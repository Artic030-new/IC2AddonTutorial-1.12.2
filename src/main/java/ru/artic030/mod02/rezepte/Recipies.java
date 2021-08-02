package ru.artic030.mod02.rezepte;

import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.core.recipe.BasicMachineRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public final class Recipies {
	
	public static final ItemStack plcar   =  	IC2Items.getItem("crafting", "carbon_plate");
	public static final ItemStack coins   =   	IC2Items.getItem("crafting", "coin");
	public static final ItemStack u235 	  =  	IC2Items.getItem("nuclear", "uranium_235");
	public static final ItemStack irpart  =		IC2Items.getItem("misc_resource", "iridium_shard");
	public static final ItemStack rawcarbon = 	IC2Items.getItem("crafting", "carbon_mesh");
	public static final ItemStack rotorblade =  IC2Items.getItem("crafting", "carbon_rotor_blade");
	public static final ItemStack diamond_small_dust =  IC2Items.getItem("dust", "small_diamond");
	public static final ItemStack gold_small_dust =  IC2Items.getItem("dust", "small_gold");
	
	public static final Block ladder = Block.getBlockById(65);
	public static final Block bars   = Block.getBlockById(101);
	public static final Block slime  = Block.getBlockById(165);
	
	public static final ItemStack glass =  new ItemStack(Blocks.GLASS);
	public static 	    ItemStack sand =  new ItemStack(Blocks.SAND);
	public static final ItemStack dirt = new ItemStack(Blocks.DIRT);
	public static final ItemStack sandx2 = new ItemStack(Blocks.SAND, 2);
	public static final ItemStack claydustx2 = IC2Items.getItem("dust", "clay");
	public static 		ItemStack gravel = new ItemStack(Blocks.GRAVEL);
	public static final ItemStack flintx2 = new ItemStack(Items.FLINT, 2);
    public static final ItemStack stonedustx3 = IC2Items.getItem("dust", "stone");
    public static final ItemStack coaldust = IC2Items.getItem("dust", "coal");
    public static final ItemStack glowdust = new ItemStack(Items.GLOWSTONE_DUST);
/*
	public static final ItemStack sandstone = new ItemStack(Blocks.SANDSTONE);
	public static final ItemStack stone = new ItemStack(Blocks.STONE);
																				*/
	
	public static void addCraftingRecipes() 
	{
		addShapedRecipes((plcar), "III","IPI","III", 'I', new ItemStack(Items.BOOK), 'P', coins);
	}
	
	private static void addShapedRecipes(ItemStack output, Object... input) 
	{
		ic2.api.recipe.Recipes.advRecipes.addRecipe(output, input);
	}
	
	
	 public static void addMachineRecipe() 
	 {
		 IRecipeInputFactory input = ic2.api.recipe.Recipes.inputFactory;
		 NBTTagCompound nbt = new NBTTagCompound();
		 nbt.setInteger("minHeat", 50 );
		
	/*	 NBTTagCompound nbt2 = new NBTTagCompound();
		 nbt2.setInteger("amount", 1000);              */
		 claydustx2.setCount(2);
		 stonedustx3.setCount(3);
		 addCompressorRecipe(input.forStack(new ItemStack(Items.SLIME_BALL, 9)), new ItemStack(slime));
		 
		  addMaceratorRecipe(input.forStack(plcar), IC2Items.getItem("dust", "coal"));
		 
		   addExtractorRecipe(input.forStack(u235), irpart);
		
		    addRollingRecipe(input.forStack(rawcarbon, 9), rotorblade);
		     addExtrudingRecipe(input.forOreDict("plankWood", 4), new ItemStack(ladder, 8));
		      addCuttingRecipe(input.forStack(new ItemStack(Items.IRON_INGOT)), new ItemStack(bars, 3));
		      
		       addMinCentrifugeRecipe(input.forStack(glass), sand, nbt);
		        addMedCentrifugeRecipe(input.forStack(dirt), claydustx2, sandx2,nbt);
		         addAdvCentrifugeRecipe(input.forStack(gravel), flintx2, sand, stonedustx3, nbt );
		         
		         addCentrifuge2Recipe(input.forStack(coaldust), diamond_small_dust);
		         addCentrifuge2Recipe(input.forStack(glowdust), diamond_small_dust);
		   /*   
		      addMinOreWashingRecipe(input.forStack(coins), sand, nbt2);
		      addMedOreWashingRecipe(input.forStack(sandstone), sand.splitStack(4), stonedustx3, nbt2);
		      addAdvOreWashingRecipe(input.forStack(stone), gravel.splitStack(2), stonedustx3, claydustx2, nbt2);
		      */
		      
		     
	 }
	 
	 
	 private static void addCompressorRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.compressor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addMaceratorRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.macerator.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addExtractorRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.extractor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	  
	 private static void addCentrifuge2Recipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.centrifuge2 = new BasicMachineRecipeManager();
		 ic2.api.recipe.Recipes.centrifuge2.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	
	 private static void addRollingRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.metalformerRolling.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addCuttingRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.metalformerCutting.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addExtrudingRecipe(IRecipeInput input, ItemStack output) 
	 {
		 ic2.api.recipe.Recipes.metalformerExtruding.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	
	private static void addMinCentrifugeRecipe(IRecipeInput input, ItemStack output, NBTTagCompound nbt)
	{
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output});
	}
	 
	private static void addMedCentrifugeRecipe(IRecipeInput input, ItemStack output, ItemStack output2, NBTTagCompound nbt)
	{
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output, output2});
	} 
	 
	private static void addAdvCentrifugeRecipe(IRecipeInput input, ItemStack output, ItemStack output2, ItemStack output3, NBTTagCompound nbt)
	{
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output, output2, output3});
	}
	

	
	
	
/*	
	private static void addMinOreWashingRecipe(IRecipeInput input, ItemStack output, NBTTagCompound nbt2)
	{
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, false, new ItemStack[] {output});
	} 
	 
	private static void addMedOreWashingRecipe(IRecipeInput input, ItemStack output, ItemStack output2, NBTTagCompound nbt2)
	{
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, false, new ItemStack[] {output,output2});
	} 
	 
	private static void addAdvOreWashingRecipe(IRecipeInput input, ItemStack output, ItemStack output2, ItemStack output3, NBTTagCompound nbt2)
	{
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, true, new ItemStack[] {output,output2,output3});
	} */
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}


