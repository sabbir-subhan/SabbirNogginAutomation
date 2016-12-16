/**
 * See oca-core-extensions.js for how to use this
 *
 * TODO
 * - Call doWaitAndSelectOCAPopUp
 * - multiple click handlers being registered
 * - doubleclick on listview rows
 * - add assets for listviews
 * - need to override whatever is recording the 'open' and remove the CSRF token
 *   IGNORE THE ABOVE, I think so long as the first "open" removes all history and _t_
 *   it should be fine.
 * - Configure / export buttons on listviews, probably need a locator for 'inside listview'
 *
 *   At the moment the 'global search' button is not registering a click handler
 *
 * BUGS
 *  - Person icon, click is not getting recorded correctly
 *
 *
 * - GUIDE TO BUILDING TESTS
 *   - eg collapse a tree after expanding it once done to ensure replayability
 *   - Dynamic widget boxes, add a waitForElementPresent on initial page load
 * 
 * - WebDriver conversion
 *   - Need to rewrite the 'pause' commands
 */

if ((typeof Recorder) == "undefined") {
	//Syntax check of these files, only needed when developing the recorder
	function Recorder() {};
	Recorder.removeEventHandler = function(){};
	Recorder.addEventHandler = function(){};

	function lb() {};
	lb.prototype.add = function(){};
	
	var LocatorBuilders = new lb();
}

if ((typeof CommandBuilders) == "undefined") {
	function CommandBuilders() {};
	CommandBuilders.add = function(){};
}

/*
 * TODO - add extra assertions, may not be necessary if the locators are sufficient
CommandBuilders.add('action',function(window) {

	var element = this.getRecorder(window).clickedElement;
	var locators = this.getRecorder(window).clickedElementLocators;

	var result = {
		command:'assertOCARow',
		target:locators
	}

	return result;
});
*/


/**
 * Recorder actions
 */
Recorder.prototype.ngClickHandler = function(eventType,event) {

	OCASelenium.init(this);

	OCASelenium.debug('CLICK');

	var locators = this.findLocators(event.target);

	//TODO trees, always add a
	//waitForElementPresent = the widget id > ul > li eg "//div[@id='wgt-Lists']/ul/li"

	OCASelenium.debug('CLICK LOCS FOUND : ' + locators.length);

	//The default click handler calls Recorder.findClickableElement. This does not
	//return true as it looks for "onclick" attributes which we do not use{

	if (locators && locators.length > 0 && locators[0][1] == 'oca') {
		OCASelenium.debug('CLICK LOCK OCA ' + locators[0][0],2);

		OCASelenium.recordPreClick(this,event.target);
		OCASelenium.debug('POST PRE RECORD CLICK',5);

		//TODO recordPostClick - eg add a pause after expanding / collapsing an item
		//                     - add a pause after a dynamic widget box is reloaded

		this.record(eventType,locators,'');

		OCASelenium.debug('POST record click',5);

		OCASelenium.recordPostClick(this,event.target);

		OCASelenium.debug('POST recordPostClick',5);

	} else {
		if (this.findClickableElement(event.target)) {
			OCASelenium.recordPreClick(this,event.target);
			this.record(eventType,locators,'');
			OCASelenium.recordPostClick(this,event.target);
		}
	}


}

//Override the dynamic widget box

Recorder.prototype.reattachWindowMethods = function() {

	OCASelenium.init(this); //This is here so that I can test the Locator function in oca-core-extensions.js, without it, log messages are missed

	//This is a complete override of the function definition from recorder.js
	//except the window.open records a different event

    var window = this.getWrappedWindow();
	//this.log.debug("reattach");
	if (!this.windowMethods) {
		this.originalOpen = window.open;
	}
	this.windowMethods = {};
	['alert', 'confirm', 'prompt', 'open'].forEach(function(method) {
			this.windowMethods[method] = window[method];
		}, this);
	var self = this;
	window.alert = function(alert) {
		self.windowMethods['alert'].call(self.window, alert);
        self.record('assertAlert', alert);
	}
	window.confirm = function(message) {
		var result = self.windowMethods['confirm'].call(self.window, message);
		if (!result) {
			self.record('chooseCancelOnNextConfirmation', null, null, true);
		}
        self.record('assertConfirmation', message);
		return result;

	}
	window.prompt = function(message) {
		var result = self.windowMethods['prompt'].call(self.window, message);
		self.record('answerOnNextPrompt', result, null, true);
        self.record('assertPrompt', message);
		return result;
	}
	window.open = function(url, windowName, windowFeatures, replaceFlag) {
		if (self.openCalled) {
			// stop the recursion called by modifyWindowToRecordPopUpDialogs
			return self.originalOpen.call(window, url, windowName, windowFeatures, replaceFlag);
		} else {
			self.openCalled = true;
			var result = self.windowMethods['open'].call(window, url, windowName, windowFeatures, replaceFlag);
			self.openCalled = false;
            if (result.wrappedJSObject) {
                result = result.wrappedJSObject;
            }
			//TODO - do we even need this, can't we detect we are in a popup window and set accordingly?
			//       maybe keep an internal state in Recorder to know which is the 'current' window
			//       and if the event occurred not in the current window add a select window 
			//       this event should also wait until the page has been loaded
			// New line
			//setTimeout(Recorder.record, 0, self, 'waitAndSelectOCAPopUp', url, "30000");

			//Original line
			setTimeout(Recorder.record, 0, self, 'waitForPopUp', windowName, "30000");

            for (var i = 0; i < self.observers.length; i++) {
                if (self.observers[i].isSidebar) {
                    self.observers[i].getUserLog().warn("Actions in popup window cannot be recorded with Selenium IDE in sidebar. Please open Selenium IDE as standalone window instead to record them.");
                }
            }
			return result;
		}
	}

}

Recorder.removeEventHandler('clickLocator');
Recorder.addEventHandler(
	'clickLocator',
	'click',
	function(event) {
		this.ngClickHandler('click',event);
	}
	,{alwaysRecord:true,capture:true}

);

Recorder.addEventHandler(
	'mousedownLocator',
	'mousedown',
	function(event) {
		this.ngClickHandler('mouseDown',event);
	}
	,{alwaysRecord:true,capture:true}
);

//Change this, remove the addEventHandler('type','change' and set
//it to use sendKeys and record ${KEY_ENTER}
Recorder.removeEventHandler('type');
Recorder.addEventHandler(
	'keydownLocator',
	'keydown',
	function(event) {
		if (event.target.tagName == 'INPUT') {
			OCASelenium.init(this);

			var key;
			switch (event.keyCode) {
				case 13: key = '${KEY_ENTER}'; break;
				case 9:  key = '${KEY_TAB}';   break;
				case 8:  key = '${KEY_BACKSPACE}'; break;
				case 37: key = '${KEY_LEFT}'; break
				case 40: key = '${KEY_DOWN}'; break
				case 38: key = '${KEY_UP}'; break
				case 39: key = '${KEY_RIGHT}'; break
				case 17: //control
				case 16: //shift
					return;
				default: key = event.key;
			}

			this.record('sendKeys',this.findLocators(event.target),key);
		}
	}
	,{alwaysRecord:true,capture:true}
);

Recorder.addEventHandler(
	'dblclickLocator',
	'dblclick',
	function(event) {
		OCASelenium.log('double click 2');
	}
	,{alwaysRecord:true,capture:true}
);


/**
 * Locator for OCA specific items, buttons, workflownodes etc
 */

LocatorBuilders.add('oca',function(element) {

	var parts = [];

	var sep = '|@|';

	var button_selector = OCASelenium.getButtonLocator(element,sep);

	if (button_selector) {
		parts.unshift(button_selector);
	}

	var screentabsbutton = OCASelenium.screenTabsButton(element);

	if (screentabsbutton) {
		parts.unshift('screentabsbutton' + sep + screentabsbutton);
	}

	var dashid = OCASelenium.insideDashboardWidget(element);

	//TODO - if inside dashid, do we add a waitForVisible event?

	if (dashid) {
		parts.unshift('dashid' + sep + dashid);
	}

	if (OCASelenium.insideModal(element)) {
		parts.unshift('modal' + sep + 'm');
	}

	var tree_parts = OCASelenium.getTree(element);
	if (tree_parts) {
		parts.unshift(tree_parts[0] + sep + tree_parts[1].join('&@&'));
	} else {
		var top_menu_label = OCASelenium.isInTopMenu(element);

		if (top_menu_label) {
			parts.unshift('topmenu' + sep + top_menu_label);
		} else {
			var sub_menu = OCASelenium.isInSubMenu(element);
			if (sub_menu) {
				parts.unshift('submenu' + sep + sub_menu);
			} else {
				var contextmenu = OCASelenium.getContextMenus(element);
				if (contextmenu !== false) {
					var cmsep = '&@&';
					parts.unshift('contextmenu' + sep + contextmenu.join(cmsep));
				}
			}
		}
	}

	//TODO - DynamicWidgetBox - add a id based locator that removes the id of the dynamic widgetbox itself eg
	//       wgt-300-MessageHTML goes to wgt-dyn-MessageHTML
	//       DynamicWidgetBoxes with numberic ids, maybe change it to be based on the index of dynamic widget boxes eg
	//
	//       wgt-30 -> dyn-1
	//       wgt-45 -> dyn-2
	//

	if (parts.length) {
		//using !@! as it is extremely unlikely this will appear in the content
		OCASelenium.debug('Attempting locator ' + "oca=" + parts.join('!@!') + ' for element ' + element.tagName);
		return "oca=" + parts.join('!@!');
	}

	return null;

});

/**
 * Helper class
 */
OCASelenium = {
	ot:null
	,debugLevel:10
	,origModal:null
	,hover:[] //List of nodes that a mouseover will be registered on
	,inited:false
	,maxMenuLevel:10 //The maximum number of nested menu items - stop infinite loops due to bugs
	,init:function(oldthis){
		if (this.inited){
			return;
		}

		var record = oldthis.record;

		var win = oldthis.getWrappedWindow();

		var old_clear = win.NGWidget.DynamicWidgetBox.prototype.clear;
		win.NGWidget.DynamicWidgetBox.prototype.clear = function() {

			//TODO - doesn't work, if the dynamicwidgetbox is too fast then it stalls
			oldthis.record('waitForNotText',oldthis.findLocators(this.node));

			old_clear.call(this,arguments);
		}
		//Don't need to override success as it causes security errors, so long as the clear override adds a waitfornocontent

		win.console.log('OCA IDE Extensions initialised');

		this.inited = true;
		this.ot = oldthis;

	}
	,locatorParts: function(query) {
		var t = query.splot('!@!');

		var parts = [];

		for (var i = 0; i < t.length; i++ ){
			parts.push(t[i].split('|@|'));
		}

		return parts;
		
	}
	,hasClass:function(node,className){
		if (node.nodeType == 1 && node && node.className && node.className.indexOf && node.className.indexOf(className) != -1){
			return true;
		}

		return false;
	}
	,hasAttribute:function(node,attributeName){
		if (node.nodeType == 1 && node && node.hasAttribute(attributeName)){
			return true;
		}
		return false;
    }
	,log:function(msg){
		if (! this.ot) {
			return;
		}

		if (typeof msg == 'string') {
			this.ot.getWrappedWindow().console.log("OCASELENIUM : " + msg);
		} else {
			this.ot.getWrappedWindow().console.log("OCASELENIUM");
			this.ot.getWrappedWindow().console.log(msg);
		}

		return true;
	}
	,dir:function(obj){
		if (! this.ot) {
			return;
		}
		this.ot.getWrappedWindow().console.dir(obj);
	}
	,debug:function(msg,level) {

		if (level === undefined) {
			level = 1;
		}

		if (level <= this.debugLevel) {
			this.log('(' + level + ') ' + msg);
		}

		return true;
		
	}
	,getButtonLocator:function(element,sep) {
		
		var funcs = {
			'button'          : OCASelenium.isButton,
			'xmlchooserpopup' : OCASelenium.isChooserPopup,
			'globalnav'       : OCASelenium.isGlobalButton
		};

		for (var i in funcs) {

			var result = funcs[i](element);

			if (result) {
				var locator = i + sep + result;
				OCASelenium.debug('Found button locator : ' + locator,5);
				return locator;
			}
			
		}

		return false;

	}
	,isButton:function(element) {
		if (element.tagName == 'SPAN' && OCASelenium.hasClass(element.parentElement,'button')) {
			return element.textContent;
		}
	}
	,isChooserPopup:function(element) {

		if (
			element.tagName == 'DIV'
			&& OCASelenium.hasClass(element,'choosericon')
			&& OCASelenium.hasClass(element.parentElement,'xmlchooser')
		) {
			return element.parentElement.id;
		}
	}
	,isGlobalButton:function(element) {

		if (OCASelenium.hasClass(element.parentNode,'header-globalnav-icons')){
			if (element.hasAttribute('data-button')) {
				return element.getAttribute('data-button');
			}
		} else if(OCASelenium.hasClass(element.parentNode,'header-globalnav-tool')) {
			OCASelenium.debug('isGlobalButton - found drop button',5);
			//Drop button
			if (element.parentNode.hasAttribute('data-button')) {
				return element.parentNode.getAttribute('data-button');
			}
		} 

		return false;

	}
	,insideDashboardWidget(element) {
		return OCASelenium.insideParentWithClass(element,'widget');
	}
	,insideModal(element) {

		var win = element.ownerDocument.defaultView;

		if (win.parent !== win && win.parent.__G && win.parent.__G.modal && win.parent.__G.modal.launcher) {
			return true;
		}

		return false;
	}
	,insideParentWithClass(element,className) {

		var p = element;

		while (p) {

			if (OCASelenium.hasClass(p,className)) {
				if (p.id) {
					return p.id;
				} else {
					return true;
				}
			}

			p = p.parentElement;

			if (p.tagName  == 'BODY') {
				break;
			}
		}

		return false;
		
	}
	,screenTabsButton(element) {

		if (
			element.tagName == 'A'
			&& element.parentNode
			&& element.parentNode.parentNode
			&& OCASelenium.hasClass(element.parentNode.parentNode,'tools')
			&& element.parentNode.parentNode.parentNode
			&& OCASelenium.hasClass(element.parentNode.parentNode.parentNode,'oca-screentabs')
		) {
			return element.parentNode.parentNode.parentNode.id + '=' + element.textContent;
		}

		return false;

	}

	,isInTopMenu:function(tnode){

		var node = tnode;
		if (
			this.hasClass(node,'layer1')
			&& this.hasClass(node.parentNode,'layer0')
			&& node.parentNode.parentNode.parentNode.parentNode.id == 'header-nav'
		){
			return node.textContent;
		}
		return false;
	}

	,isInSubMenu:function(tnode){

		var node = tnode;
		if (
			this.hasClass(node,'layer1')
			&& this.hasClass(node.parentNode,'layer0')
			&& this.hasClass(node.parentNode.parentNode.parentNode,'children')
		){
			node = node.parentNode.parentNode.parentNode.parentNode;

			var i = 0;
			for (var i; i < OCASelenium.maxMenuLevel; i++) {
				if (node.parentNode.parentNode.id == 'header-nav') {
					break;
				}

				node = node.parentNode.parentNode;
			}

			return i + ':' + tnode.textContent;

		}
		return false;
	}
	,getTopMenuLocators:function(recorder,target) {

		var node = target;
		var hover_locators = [];

		OCASelenium.debug('getTopMenuLocators start',10);

		for (var i=0; i < OCASelenium.maxMenuLevel; i++) {

			OCASelenium.debug('getTopMenuLocators loop # ' + i,10);

			if (
				this.hasClass(node,'layer1')
				&& this.hasClass(node.parentNode,'layer0')
			) {

				OCASelenium.debug('getTopMenuLocators found layer1',10);

				try {

					if (node.parentNode.parentNode.parentNode.parentNode.id == 'header-nav') {
						OCASelenium.debug('getTopMenuLocators back to root',10);
						return hover_locators;
					} else if(this.hasClass(node.parentNode.parentNode.parentNode,'children')) {

						OCASelenium.debug('getTopMenuLocators finding siblings',10);

						var siblings = node.parentNode.parentNode.parentNode.parentNode.childNodes;
						OCASelenium.debug('getTopMenuLocators # siblings' + siblings.length,10);

						for (var x = 0; x < siblings.length; x++) {
							if (this.hasClass(siblings[x],'layer0')) {
								var l0 = siblings[x].querySelectorAll('div.layer1');
								OCASelenium.debug('getTopMenuLocators found label ' + l0.length);
								node = l0[0];
								break;
							}
						}

						hover_locators.unshift(recorder.findLocators(node));
					}
				} catch(err) {
					OCASelenium.debug('getTopMenuLocators ERROR : ' + err.message);
				}
			}
		}

		OCASelenium.debug('getTopMenuLocators end - no hovers',10);

		return false;

	}
	,recordHover:function(recorder,target) {

		OCASelenium.debug('recordHover pre getTopMenu',10);

		var hovers = this.getTopMenuLocators(recorder,target);

		OCASelenium.debug('recordHover post getTopMenu',10);

		if (! hovers) {
			OCASelenium.debug('recordHover pre context menu',10);
			hovers = this.getContextMenuHovers(recorder,target);
			OCASelenium.debug('recordHover post context menu',10);
		}

		if (! hovers) {
			OCASelenium.debug('recordHover - no hovers',10);
			return;
		}

		OCASelenium.debug('recordHover # hovers ' + hovers.length,10);

		for (var i = 0; i < hovers.length; i++) {
			recorder.record('waitForElementPresent',hovers[i],'');
			recorder.record('mouseOver',hovers[i],'');
		}

	}
	,getContextMenuHovers: function(recorder,target) {

		if (! this.hasClass(target,'context-menu-item')) {
			return false;
		}

		var doc = target.ownerDocument;

		var focused = doc.querySelectorAll('div.context-menu div.context-menu-item-focused');

		var hover_locators = [];

		//Don't need the last one
		for (var i = 0; i < focused.length-1; i++) {
			var locs = recorder.findLocators(focused[i]);
			hover_locators.push(locs);
		}

		return hover_locators;

	}
	,getContextMenus:function(target) {
		
		if (! this.hasClass(target,'context-menu-item')) {
			return false;
		}

		//Get any preceding context-menus in the document and add the focused item in that list

		var labels = [];
		
		var menu = target.parentNode;

		var menus = target.ownerDocument.querySelectorAll('div.context-menu');

		for (var i = 0; i < menus.length; i++) {

			var focused = menus[i].querySelectorAll('div.context-menu-item-focused');

			if (focused.length) {
				labels.push(focused[0].textContent);
			}

			if (menus[i] == menu) {
				break;
			}

		}
		return labels;
	}
	,getTree:function(target) {
		
		var rule;

		if (target.tagName == 'DIV') {
			if (this.hasClass(target,'layer2')) {
				rule = 'tree';
			} else if(this.hasClass(target,'layer0')) {
				rule = 'treeexp';
			}
		}

		if (! rule) {
			return false;
		}

		var parts = [target.textContent];

		var p = target.parentNode;

		while(p) {
			
			switch (p.tagName) {
				case 'DIV':
					if (this.hasClass(p,'oca-tree')) {
						parts.unshift(p.id);
						return [rule,parts];
					}
					break;
				case 'UL':
					if (p.parentNode.tagName == 'LI') {
						//Sub item, get the label of that div
						var parent_tree_node = p.parentNode.querySelector('div.expanded > div.layer0 > div.layer1 > div.layer2');
						parts.unshift(parent_tree_node.textContent);
					}
					break;

			}

			p = p.parentNode;

		}

		return false;


	}
	,recordPreClick:function(recorder,target) {

		OCASelenium.debug('recordPreClick A',10);

		OCASelenium.recordHover(recorder,target);
		OCASelenium.debug('recordPreClick B',10);
		
		//Trees can load via ajax
		var tree_parts = this.getTree(target);
		if (tree_parts) {
			OCASelenium.debug('recordPreClick C',10);

			/*
			Ignoring the for moment, however it means that a test part cannot be replayed without logging
			out
			if (tree_parts[0] == 'tree') {
				//TODO add , ensure ensureOCATreeCollapsed
				recorder.record('ensureOCATreeExpanded',recorder.findLocators(target));
			}
			*/

			var tree = target.ownerDocument.getElementById(tree_parts[1][0]);
			var first_item = tree.querySelector('ul > li');
			OCASelenium.debug('recordPreClick D',10);

			var locators = recorder.findLocators(first_item);

			//The recorder can pick a bad one, eg 'css:li'
			for (var i = 0; i < locators.length; i++) {
				if (locators[i][1] != 'xpath:idRelative') {
					locators.splice(i,1);
					i--;
				}
			}

			if (locators.length) {
				recorder.record('waitForElementPresent',locators,'');
			}
		}
		OCASelenium.debug('recordPreClick E',10);

		//Dynamicwidgetbox, if inside a dynamic widget box, then add a waitForElementPresent
		//Nope, won't work for multiple clicks, eg a test to ensure that the 'dirty' is NOT triggered if no changes made

	}
	,recordPostClick:function(recorder,target) {

		var tree_parts = this.getTree(target);
		if (tree_parts && tree_parts[0] == 'treeexp') {
			//Wait for the treememory to be sent
			recorder.record('pause','1000','');
		}

	}
	
	//OLD
	,isDropButton:function(tnode){
		var node = tnode;
		if (
			this.hasClass(node,'dropbutton-icon')
			&& this.hasAttribute(node.parentNode,'data-button')
		){
			return true;
		} else if (
			(node.tagName == 'A')
			&& (node.parentNode.id.indexOf('wgt-') != -1)
			&& !this.hasClass(node.parentNode,'button-link')
			&& (
				this.hasClass(node.parentNode.parentNode,'oca-groupbox-nav')
				|| this.hasClass(node.parentNode.parentNode,'tools')
			)
		){
			return true;
		}
		return false;
	}
};
