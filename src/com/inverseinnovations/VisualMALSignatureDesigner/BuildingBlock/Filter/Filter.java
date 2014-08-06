package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter;

import java.awt.image.BufferedImage;

import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.BuildingBlock;

public class Filter extends BuildingBlock {
	private static final long serialVersionUID = 1L;

	public Filter(String name,Main Main){
		super(name, Main);
	}
	@Override
	public boolean isFilter(){
		return true;
	}

	@Override
	public BufferedImage display(BufferedImage image){
		image = generateImage(image);
		if(getChildCount() > 0){
			//send this to each child to rerender(filter)
			for(int i = 0; i< getChildCount(); i++){
				if(!((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).isFilter()){
					image = Main.sig.filter.composite(image, ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image), 0, 0);
				}
				else{//if filter
					image = ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image);
				}
			}
		}
		return image;
	}
	protected BufferedImage generateImage(BufferedImage image){
		return image;
	}
	@Override
	public String createScript(String filteronly){
		StringBuilder string  = new StringBuilder(generateScript(filteronly));
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
	public String generateScript(String filteronly){
		return null;
	}
}
