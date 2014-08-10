package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.jnlp.*;

import com.inverseinnovations.MALSignatureDesignerLite.*;
//TODO update when Block ediited, block added, block deleted, block moved(?)

public class Main {
	public ImageWindow ImageWindow;
	public BlockWindow BlockWindow;

	public Signature sig = new Signature(this.getClass());

    public static void main(String[] args){
        new Main();
    }

    public Main(){
    	ImageWindow = new ImageWindow(this);
    	BlockWindow = new BlockWindow(this);
    	prepareTempFolder();
    	//TODO make sure Sig is init before attempting to alter
    	//demoSig();

    	//just testing stuff here
    	try {
            Thread.sleep(400);
        } catch (InterruptedException e) {}
        //sig.addBackground("#FF33FF"); //Place a black background(honestly not needed)

    	ImageWindow.update();
    }

    /**
     * A demonstration of a signature being created and displayed
     */
    public void demoSig(){
    	sig.initDemoSignature(600,110);//Grab My Anime List and make a 600 by 110 signature
        sig.addBackground("FFFFFF"); //Place a black background(honestly not needed)
        BufferedImage animeImage = sig.loadAnimeThumbnail(1); //Gets the image of the first anime on my list
        animeImage = new Filter().perspective(animeImage, 0, 11, 52, 0, 52, 49, 0, 61); //applys a perspective filter to appear 3d slanted
        animeImage = new Filter().unsharp(animeImage,25); //then applys unsharp filter to unblur
        sig.addImage(animeImage, 52, -1); //lastly places the altered thumbnail on the sig
        sig.addImage("computergaze.png", 0, 0); //Places my main background image on the sig (it has a hole for the anime image)
        sig.addImage("compgazeapocist1.png", 0, 0); //Places another image that has my username
        sig.addTitle(1, 382, 107, sig.newFont("lightout.ttf","bold",22,"#3c5356"), "left", 0, false, 35); //Places the first anime's title
        sig.addStatus(1, 306, 67, sig.newFont("lightout.ttf","bold",15,"#3c5356"), "center", 28); //Places the first anime's watching status
        sig.addEpisodes(1, 295, 82, sig.newFont("lightout.ttf","bold",12,"#DCCBCB"), "center", 28, 2); //Places the first anime's episodes watched


        ImageWindow.update();
    }

    /**
     * Makes sure the required folders exist for the program
     */
    private void prepareTempFolder(){
    	File dir = new File(System.getProperty("user.dir") + "/images");
    	System.out.println(dir.getPath());
    	if(dir.mkdir()){
    		System.out.println("folder created");
    	}
    	if(!dir.exists()){
    		System.out.println("folder still doesn't exist tho");
    	}

    	dir = new File(System.getProperty("user.dir") + "/fonts");
    	System.out.println(dir.getPath());
    	if(dir.mkdir()){
    		System.out.println("folder created");
    	}
    	if(!dir.exists()){
    		System.out.println("folder still doesn't exist tho");
    	}
	}

    //Dunno where to put this yet
    /**
	 * Checks if a String may be translated as an int
	 * @param s String to check
	 */
	public boolean isInteger(String s){
		if(s != null && s != "")return isInteger(s,10);
		return false;
	}
	private static boolean isInteger(String s, int radix){
		if(s.isEmpty()) return false;
		for(int i = 0; i < s.length(); i++) {
			if(i == 0 && s.charAt(i) == '-') {
				if(s.length() == 1) return false;
				continue;
			}
			if(Character.digit(s.charAt(i),radix) < 0) return false;
		}
		return true;
	}
	public String toHexString(Color c) {
		  StringBuilder sb = new StringBuilder("#");

		  if (c.getRed() < 16) sb.append('0');
		  sb.append(Integer.toHexString(c.getRed()));

		  if (c.getGreen() < 16) sb.append('0');
		  sb.append(Integer.toHexString(c.getGreen()));

		  if (c.getBlue() < 16) sb.append('0');
		  sb.append(Integer.toHexString(c.getBlue()));

		  return sb.toString().toUpperCase();
	}
}
