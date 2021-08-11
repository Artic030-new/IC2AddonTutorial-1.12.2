package ru.artic030.mod02.machines;

import ic2.api.recipe.Recipes;
import ic2.core.recipe.BasicMachineRecipeManager;

public class TileEntityCentrifuge extends TileEntityTestMachine {

	public TileEntityCentrifuge() {
		super(1, Recipes.centrifuge2);
	}
	
	public String getSound() {
		return "Machines/MaceratorOp.ogg";
	}
}
