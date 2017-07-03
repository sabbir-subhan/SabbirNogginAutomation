package com.noggin.OCA1AutomationSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Rought idea
/*
 * Read selenium testcase.html file
 * Read <td> element of html
 * Apply Business rules to convert each selenium command Noggin specific Java function call
 */

public class JSoupToolToReadSelHtlmlFile {

	public static void main(String[] args)  {
		
		//Jsoup to read html files and find elements of that HTML file (i.e Selenium Test Cases)
		//Reference: https://jsoup.org/cookbook/extracting-data/dom-navigation
		Elements contents=null;
		try{
				File input = new File("C://SeleniumIDETestCase//login.html");
				Document doc = Jsoup.parse(input, "UTF-8");
				contents = doc.getElementsByTag("td");
				
		}
		catch(NullPointerException e){
			System.out.println("No file found!!");
		} 
		catch (IOException e) {
		
			System.err.println("Problem writing to the file");
			e.printStackTrace();
		}
		//initiliaze variables
		int x=1;
		String part1="";
		String part2="";
		String part3="";
		int j=1;
		//creating an array list
		int arrayIndex=0;
		ArrayList<String> OutPutArray = new ArrayList<String>();
	
//		String[] textArray = null;
		for(Element content: contents){
			String Text=content.text();
	
			if(x>4){
					if(j==1){
						part1=Text;
						part1=removeOCABbtton(part1);
						
							}
					if(j==2){
						part2=Text;
						part2=removeOCABbtton(part2);
							}
					if(j==3){
						part3=Text;
						part3=removeOCABbtton(part3);
						/*After combing text from tree subsequent td elements, we should pass this element to a Java Function
						 * Java function will read the string.
						 * Based on subs string (exact text) rule, it will write selenium webdriver function call
						 * and save in java or text file.
						 */
						String finalString=part1+part2+part3;
						//add to string array list
						//arryindex
						
						OutPutArray.add(finalString);
						
						System.out.println(x+".Combined String:"+OutPutArray.get(arrayIndex));
						arrayIndex++;
						part1="";
						part2="";
						part3="";
						j=0;
							}
					
				 	}
			if(x>4&&j<3){
				j++;
				}
			x=x+1;
			}
		//Write array list file
		try {
			savetoFile(OutPutArray);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Problem writing to the file");
			e.printStackTrace();
		}
		}

	private static String removeOCABbtton(String part1) {
		String[] parts=part1.split(Pattern.quote("oca="));
		String ComStr="";
		for (int i=0;i<parts.length;i++){
			ComStr=ComStr+parts[i];
		}
		return ComStr;
	}

	private static void savetoFile(ArrayList<String> OutPutArray) throws FileNotFoundException {
		try{
		File JavaFile = new File("C://SeleniumIDETestCase//Test.java");
		FileOutputStream FlieOutPut=new FileOutputStream(JavaFile);
		
		PrintWriter pw = new PrintWriter(FlieOutPut);
	    for (int i=0;i<OutPutArray.size();i++)
	        pw.println(OutPutArray.get(i));
	    pw.close();
		}
		catch (IOException e) {
        	System.err.println("Problem writing to the file");
        	
    }
		
	}
}


