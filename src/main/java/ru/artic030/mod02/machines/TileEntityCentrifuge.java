package ru.artic030.mod02.machines;

import java.util.Collection;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class TileEntityCentrifuge extends TileEntityTestMachine {

	public TileEntityCentrifuge(byte tier, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeSet) {
		super((byte)1, Recipes.macerator);
		
	}
	
	public String getSound() {
	      return "Machines/MaceratorOp.ogg";
	   }


}
