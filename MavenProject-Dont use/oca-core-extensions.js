/**
 * Notes for using this
 *
 * Using the Selenium IDE Firefox extension
 *
 * Add oca-core-extenions.js into the "Selenium Core extensions"
 * Add oca-ide-extensions.js into the "Selenium IDE extensions"
 *
 * Close the window and reopen
 * Under the "Locator Builders" tab you should see an "oca" option. Drag this to the top
 * If it does not appear, then use about:config and search for the "extensions.selenium-ide.locatorBuildersOrder" key
 * and reset it
 *
 * When running under webdriver, in theory we can call the javacode and include the oca-core-extensions.js
 * file and it should all work (Except all the OCASelenium.debug calls will fail!)
 *
 * WORKING
 * 
 * Locators
 * - Dashboard widgets
 * - Buttons
 * - Modals
 * - Screen tab - drop buttons
 * - tree nodes
 * - top menu, except state selector and primary eme selector
 * - context menu
 *
 * Opening
 * - Modals
 *
 *
 * TODO
 *
 * Locators
 * - Popups
 * - screen tab - buttons
 * - Form designer
 * - tree - expanding
 * - treeselect
 * - dynamic widget box
 *
 * Javascript
 * - See if we can record javascript code coverage
 *
 */

/**
 * OCA Specific actions
 */

if ((typeof Selenium) == "undefined") {
	//Syntax check of these files, only needed when developing the recorder
	function Selenium() {};
	function BrowserBot() {};
	function PageBot() {};
}

Selenium.prototype.doWaitAndSelectOCAPopUp = function(url,timeout) {

	var bot = selenium.browserbot;

	if (timeout === null) {
		timeout = 30000;
	}

	return Selenium.decorateFunctionWithTimeout(
		function() {

			for (var i in bot.openedWindows) {
				var w = bot.openedWindows[i];

				//TODO - Maybe add a flag to indicate that this has already been selected
				//       this would allow multiple popups for the same URL to be opened and waited for
				//     - CSRF token will break this, need to remove the token in the comparison

				if (w.document.location.pathname == url && w.document.readyState == 'complete') {
					OCASelenium.log(i);
					return true;
				}
			}
		}
		,timeout
	);

}

/**
 * Override select frame 
 */
Selenium.prototype.doSelectFrame = function(target) {

	if (target == '_ngbasis_modalwin_') {

		//Wait for the modal to load
		var w = selenium.browserbot.getCurrentWindow();
		var b = selenium.browserbot;
		var f = w.frames[target];
		var log = LOG;

		return Selenium.decorateFunctionWithTimeout(
			function() {
				if (f.document.location.pathname && f.document.readyState == 'complete') {
					b.selectFrame(target);
					return true;
				}
			}
			,30000
		);

	} else {

		selenium.browserbot.selectFrame(target);

	}

}

Selenium.prototype.doRegisterDynamicBox = function(target) {

	var w = selenium.browserbot.getCurrentWindow();

	var box = selenium.browserbot.findElement(target);
	if (box) {

		var id = box.id.substring(4);

		var widget = w.NGBasis.widgets[id];

		if (widget) {

			//It is too late to catch the initial load
			OCASelenium.debug('OVERRIDDEN ' + widget.id);

			var old_command = widget._command;
			widget._command = function() {
				//Successully overriden
				OCASelenium.debug('COMMAND ' + widget.id);
				old_command.call(this,arguments);
			}

			return true;
		}


	}

	return false;

}

//The 'treeexp' click cannot be relied upon as tree widget memory means that nodes may
//already be expanded
/*
Selenium.prototype.doEnsureOCATreeExpanded = function(target) {

}
*/

BrowserBot.prototype._oldSelectWindow = BrowserBot.prototype.selectWindow;

BrowserBot.prototype.selectWindow = function(target) {

	if (target == '_ngbasis_modalwin_') {

		OCASelenium.log('Select iframe');

	} else {
		this._oldSelectWindow(target);
	}

}

/*
 * Locates an OCA element by parts eg button inside a dashboard widget
 */
PageBot.prototype.locateElementByOCA = function(query,document) {

	//Break the parts down
	
	var parts = query.split('!@!');

	var target = document;

	//Note, apparently does not work in < IE9
	var win = document.defaultView;

	for (var i = 0; i < parts.length; i++) {
		var t = parts[i].split('|@|');

		var area = t[0];
		var id = t[1];

		switch (area) {
			case 'dashid':
				target = target.getElementById(id);
				break;
			case 'button':

				var buttons = target.querySelectorAll('div.button');

				target = null;

				for (var x = 0; x < buttons.length; x++) {

					var button = buttons[x];

					if (PageBot.hasClass(button,'capped-inline')) {
						button = button.getElementsByTagName('span')[0];
					}

					if (button.textContent == id) {
						target = button;
						break;
					}                          	
				}                              	
                                               	
				break;
			case 'modal':

				if (win.parent.__G && win.parent.__G.modal && win.parent.__G.modal.launcher) {
					//nothing to do, already in the modal
				} else if (win.__G && win.__G.modal && win.__G.modal.launcher) {
					//TODO - if args.target is set in the signal connection, the name of the widget will be this value
					PageBot.selectFrame('_ngbasis_modalwin_');
				}

				break;

			case 'screentabsbutton':

				var wgt_id = id.substr(0,id.indexOf('='));
				var button_label = id.substr(id.indexOf('=')+1);

				target = target.getElementById(wgt_id);

				if (target) {

					var buttons = target.querySelectorAll('div.tools > div > a');

					target = null;

					for (var x = 0; x < buttons.length; x++) {
						if (buttons[x].textContent == button_label) {
							target = buttons[x];
							break;
						}
					}

				}

				break;
			case 'xmlchooserpopup':
				var chooser = document.getElementById(id);
				if (chooser) {
					var buttons = chooser.querySelectorAll('div.choosericon');
					if (buttons.length) {
						return buttons[0];
					}
				}
				break;
			case 'topmenu':

				var divs = target.querySelectorAll('#header-nav > div > div > div.layer0 > div.layer1');

				for (var x = 0; x < divs.length; x++) {
					if (id == divs[x].textContent) {
						return divs[x];
					}
				}

				break;
			case 'submenu':
				//Note, this locator records the correct state already
				var s = id.split(':');
				var depth = s[0];
				var label = s[1];

				//OCASelenium.debug('CE Running');

				//TODO - This is broken for the first sub item

				var root_expanded = target.querySelectorAll('#header-nav > div > div > div.expanded');
				if (root_expanded.length == 0) {
					return false;
				}
				
				//ie does not support a :scope selector, querySelectorAll will get all descendants and error
				var sub_items = PageBot.subMenuChildren(root_expanded[0]);
				
				for (var y = 0; y < depth; y++) {
					for (var x = 0; x < sub_items.length; x++) {
						//Don't neet to worry about scope here, if any are expanded, it will be the
						//direct child that is first
						var expanded = sub_items[x].querySelectorAll('div.active > div.expanded');
						if (expanded.length) {
							sub_items = PageBot.subMenuChildren(expanded[0]);
						}
					}
				}


				//Cannot use querySelectorAll because of lack of :scope pseudo class in IE
				for (var y = 0; y < sub_items.length; y++) {
					for (var l0 = 0; l0 < sub_items[y].childNodes.length; l0++) {
						if (sub_items[y].childNodes[l0].tagName == 'DIV' && PageBot.hasClass(sub_items[y].childNodes[l0],'layer0')) {
							for (var l1 = 0; l1 < sub_items[y].childNodes[l0].childNodes.length; l1++) {
								if (
									sub_items[y].childNodes[l0].childNodes[l1].tagName == 'DIV'
									&& PageBot.hasClass(sub_items[y].childNodes[l0].childNodes[l1],'layer1')
									&& sub_items[y].childNodes[l0].childNodes[l1].textContent == label
								) {
									//OCASelenium.debug('CE: SUB MENU FOUND');
									return sub_items[y].childNodes[l0].childNodes[l1];
								}
							}
						}
					}
				}

				break;
			case 'globalnav':

				var header = target.querySelectorAll('.header-globalnav div[data-button="' + id + '"]');
				if (header.length == 1) {
					var div = header[0];

					var icon = div.querySelectorAll('div.dropbutton-icon');
					if (icon.length) {
						return icon[0];
					}

					return div;
				}

				break;
			case 'contextmenu':

				var parts = id.split('&@&');

				var doc = target;
				if (doc.ownerDocument) {
					doc = doc.ownerDocument;
				}
				
				var menus = doc.querySelectorAll('div.context-menu');

				//Only need to get the last one
				var menu_index = parts.length-1;
				var label = parts.pop();

				if (menus[menu_index]) {
					var items = menus[menu_index].querySelectorAll('div.context-menu-item');
					
					for (var y = 0; y < items.length; y++) {
						if (items[y].textContent == label) {
							return items[y];
						}
					}
				}

				break;
			case 'tree':
			case 'treeexp':
				var parts = id.split('&@&');

				var wgt_id = parts.shift();

				//First part is the wgt id, the rest is the heirarchy of labels, note this means that duplicate names are not supported, at least
				//not selecting any but the first

				var tree = target.querySelectorAll('#' + wgt_id);
				if (! tree.length) {
					return;
				}
				tree = tree[0];

				while (label = parts.shift()) {
					items = PageBot.scopedQuerySelectorAll(tree,'ul > li > div > div.layer0 > div.layer1 > div.layer2',6);

					tree = null;

					for (var x = 0; x < items.length; x++) {
						if (items[x].textContent == label) {
							if (parts.length == 0) {
								switch (area){
									case 'treeexp':
										//Expand is a click on the layer0 element
										return items[x].parentNode.parentNode;
										break;
									default:
										return items[x];
								}

							}
							tree = items[x].parentNode.parentNode.parentNode.parentNode; //back to the li item
						}
					}

					if (! tree) {
						return;
					}
				}

				break;
			case 'dynamicbox':

				//The id is actually the index of the dynamic widget box

				var boxes = target.querySelectorAll('div.dynamicbox');

				//OCASelenium.debug('Num boxes ' + boxes.length);

				target = boxes[id];
				
				break;

				
		}

		if (target === null) {
			//OCASelenium.debug('Returning NULL for target');
			return false;
		} 
	}

	if (target) {
		//OCASelenium.debug('Returning target with tagname ' + target.tagName);
		return target;
	}
	
	//OCASelenium.debug('no target found');

	return false;



}
PageBot.prototype.locateElementByOCA.prefix = 'oca';

PageBot.subMenuChildren = function(element) {

	var children;

	var cn = element.parentNode.childNodes;

	for (var i = 0; i < cn.length; i++) {
		if (cn[i].tagName == 'DIV' && PageBot.hasClass(cn[i],'children')) {
			children = cn[i];
			break;
		}
	}

	if (! children) {
		return;
	}

	var cn = children.childNodes;

	var active = [];

	for (var i = 0; i < cn.length; i++) {
		if (cn[i].tagName == 'DIV' && PageBot.hasClass(cn[i],'active')) {
			active.push(cn[i]);
		}
	}

	return active;

}

/**
 * Returns items that match the query that are a specific depth from node eg
 *
 * div
 *  ul
 *   li
 *    ul
 *     li
 *
 * scopedQuerySelectorAll(div,'ul > li',2)
 *
 * would only return the first li item as it is a depth of 2 from div
 */
PageBot.scopedQuerySelectorAll = function(node,query,depth) {

	var items = node.querySelectorAll(query);

	if (depth === undefined) {
		depth = 1;
	}

	var scoped_items = [];

	for (var i = 0; i < items.length; i++) {

		var p = items[i].parentNode;

		for (var x = 1; x < depth; x++) {
			p = p.parentNode;
			if (! p) {
				break;
			}
		}

		if (! p) {
			continue;
		}

		if (p == node) {
			scoped_items.push(items[i]);
		} else {
		}
	}

	return scoped_items;

}

PageBot.hasClass = function(node,className){
	if (node.nodeType == 1 && node && node.className && node.className.indexOf && node.className.indexOf(className) != -1){
		return true;
	}

	return false;
}
