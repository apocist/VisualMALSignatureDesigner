package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Frame;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

/**
 * Basic Object that contains the 'layers' of a signature
 */
public class BuildingBlock extends DefaultMutableTreeNode{
	private static final long serialVersionUID = 1L;
	public final boolean ISPARENTABLE = true;
	protected int x = 0;
	protected int y = 0;
	private String name;

	//public Signature sig;
	public transient Main Main;

	public BuildingBlock(String name, Main Main){
		setName(name);
		//this.sig = sig;
		this.Main = Main;
		saveObject();
	}
	public void reinit(Main Main){
		this.Main = Main;
		if(getChildCount() > 0){
			for(int i = 0; i< getChildCount(); i++){
				BuildingBlock block = (BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject();
				if(block != null){
					block.reinit(Main);
				}
			}
		}
	}
 	public boolean isFilter(){
		return false;
	}
	/**
	 * @return the x coord
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return the y coord
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	public Icon getIcon() {
		return null;
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
	/**
	 * Creates a script of MSD that will copy the current signauture
	 * @return
	 */
	public String createScript(){
		StringBuilder string  = new StringBuilder(generateScript());
		if(getChildCount() > 0){
			for(int i = 0; i< getChildCount(); i++){
				BuildingBlock block = (BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject();
				if(block != null){
					if(!block.isFilter()){//if not a filter
						string = new StringBuilder("filter.composite("+string+", "+block.createScript()+", 0, 0)");
					}
					else{//if a filter
						string = new StringBuilder(block.createScript(string.toString()));
					}
				}
			}
		}
		//return final string
		return string.toString();
	}
	public String createScript(String filteronly){
		//do NOT output saveSignature from here
		StringBuilder string  = new StringBuilder(generateScript());
		if(getChildCount() > 0){
			for(int i = 0; i< getChildCount(); i++){
				BuildingBlock block = (BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject();
				if(block != null){
					if(!block.isFilter()){//if not a filter
						string = new StringBuilder("filter.composite("+string+", "+block.createScript(null)+", 0, 0)");
					}
					else{//if a filter
						string = new StringBuilder(block.createScript(string.toString()));
					}
				}
			}
		}
		//return final string
		return string.toString();
	}
	/**
	 * The Block specific for each command
	 * @return
	 */
	public String generateScript(){
		return "";
	}

}
