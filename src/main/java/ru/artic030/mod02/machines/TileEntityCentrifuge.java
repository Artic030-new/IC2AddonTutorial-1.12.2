package ru.artic030.mod02.machines;

import ic2.api.recipe.Recipes;

public class TileEntityCentrifuge extends TileEntityTestMachine {

	public TileEntityCentrifuge() {
		super((byte)1, Recipes.macerator);
		
	}
	
	public String getSound() {
	      return "Machines/MaceratorOp.ogg";
	   }


}