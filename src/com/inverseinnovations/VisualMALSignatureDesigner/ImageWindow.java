package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import com.inverseinnovations.MALSignatureDesignerLite.Signature;

/**
 * Creates and manages the JFrame of the visible Signature
 */
public class ImageWindow {
	public final Main Main;
	private JFrame imageFrame;
	private JLabel imageLabel = new JLabel();

	/**
	 * Creates and manages the JFrame of the visible Signature
	 */
	public ImageWindow(final Main Main){
		this.Main = Main;
		create();
	}

	/**
	 * Initilizes and creates the ImageFrame
	 */
	public void create(){
		SwingUtilities.invokeLater(new Runnable(){
			  public void run(){
				  imageFrame = new JFrame("Image Demo");
				  imageFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				  imageFrame.getContentPane().add(imageLabel, BorderLayout.CENTER);
				  imageFrame.pack();
				  imageFrame.setLocationRelativeTo(null);
				  imageFrame.setVisible(true);
				  //update();
			  }
		 });
	}
	/**
	 * Updates the ImageFrame with the current Signature
	 * and resizes the ImageFrame to fit
	 */
	public void update(){
		Main.sig = new Signature(Main.getClass());
		Main.BlockWindow.blocks.rootNode.display(null);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {}
		try{
			imageLabel.setIcon(new ImageIcon(Main.sig.getSigImage()));
			imageFrame.pack();
		}
		catch(java.lang.NullPointerException e){
			System.out.println("Signature is null");
		}
	}

	public Point getLocation(){
		return imageFrame.getLocation();
	}
}
