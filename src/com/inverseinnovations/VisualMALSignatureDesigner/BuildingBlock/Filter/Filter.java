package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter;

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
}
