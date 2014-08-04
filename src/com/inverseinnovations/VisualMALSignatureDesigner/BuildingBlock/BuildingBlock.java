package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Frame;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

/**
 * Basic Object that contains the 'layers' of a signature
 */
public class BuildingBlock extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 1L;
	public final boolean ISPARENTABLE = true;
	private BufferedImage image;
	private String name;

	//public Signature sig;
	public final Main Main;

	public BuildingBlock(String name, Main Main){
		setName(name);
		//this.sig = sig;
		this.Main = Main;
		saveObject();
	}
	public boolean isFilter(){
		return false;
	}
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public void saveObject(){
		setUserObject(this);
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return getName();
	}
	public JDialog settingsDialog(Frame owner){
		return new JDialog(owner, "Null", true);
	}
	public BufferedImage display(BufferedImage image){
		//render TODO

		if(getChildCount() > 0){
			//send this to each child to rerender(filter)
			for(int i = 0; i< getChildCount(); i++){
				image = ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image);//TODO
			}
		}
		//return final image
		return image;
	}
}
