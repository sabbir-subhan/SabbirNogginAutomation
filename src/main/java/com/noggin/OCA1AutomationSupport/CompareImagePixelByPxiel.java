package com.noggin.OCA1AutomationSupport;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class CompareImagePixelByPxiel {
	
	
	//this method will compare between two images and return difference as buffered image
	public static BufferedImage getDifferenceImage(BufferedImage actualImage, BufferedImage baseLineImage) {
	    // convert images to pixel arrays...
	    final int w = actualImage.getWidth(),
	            h = actualImage.getHeight(), 
	            highlight = Color.MAGENTA.getRGB();
	    final int[] p1 = actualImage.getRGB(0, 0, w, h, null, 0, w);
	    final int[] p2 = baseLineImage.getRGB(0, 0, w, h, null, 0, w);
	    boolean diffFoundbetweenImages=false;
	    // compare img1 to img2, pixel by pixel. If different, highlight img1's pixel...
	    for (int i = 0; i < p1.length; i++) {
	        if (p1[i] != p2[i]) {
	            p1[i] = highlight;
	            diffFoundbetweenImages=true;
	        }
	    }
	    
	    if(diffFoundbetweenImages){
	    	System.out.print("!----Difference is found between Actual image and Baselind image. Please check the difference--!");
	    }
	    else{
	    	System.out.print("!----No Difference is found between Actual image and Baselind image. All good :)--!");
	    }
	    // save img1's pixels to a new BufferedImage, and return it...
	    // (May require TYPE_INT_ARGB)
	    final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    out.setRGB(0, 0, w, h, p1, 0, w);
	    return out;
	}
}