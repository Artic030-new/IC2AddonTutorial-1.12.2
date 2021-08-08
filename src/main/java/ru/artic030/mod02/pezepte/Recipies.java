package ru.artic030.mod02.pezepte;

import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.IRecipeInputFactory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Recipies {
	
	public static final ItemStack plcar   =  	IC2Items.getItem("crafting", "carbon_plate");
	public static final ItemStack coins   =   	IC2Items.getItem("crafting", "coin");
	public static final ItemStack u235 	  =  	IC2Items.getItem("nuclear", "uranium_235");
	public static final ItemStack irpart  =		IC2Items.getItem("misc_resource", "iridium_shard");
	public static final ItemStack rawcarbon = 	IC2Items.getItem("crafting", "carbon_mesh");
	public static final ItemStack rotorblade =  IC2Items.getItem("crafting", "carbon_rotor_blade");
	
	public static final Block ladder = Block.getBlockById(65);
	public static final Block bars   = Block.getBlockById(101);
	public static final Block slime  = Block.getBlockById(165);
	
	public static final ItemStack glass =  new ItemStack(Blocks.GLASS);
	public static final ItemStack sand =  new ItemStack(Blocks.SAND);
	public static final ItemStack dirt = new ItemStack(Blocks.DIRT);
	public static final ItemStack sandx2 = new ItemStack(Blocks.SAND, 2);
	public static final ItemStack claydust = IC2Items.getItem("dust", "clay");
	public static final ItemStack claydustx2 = IC2Items.getItem("dust", "clay");
	public static final ItemStack gravel = new ItemStack(Blocks.GRAVEL);
	public static final ItemStack flintx2 = new ItemStack(Items.FLINT, 2);
    public static final ItemStack stonedust = IC2Items.getItem("dust", "stone");

	public static final ItemStack mossycobble = new ItemStack(Blocks.MOSSY_COBBLESTONE);
	public static final ItemStack cobble = new ItemStack(Blocks.COBBLESTONE);
	public static final ItemStack netherrack = new ItemStack(Blocks.NETHERRACK);
	public static final ItemStack soulsand = new ItemStack(Blocks.SOUL_SAND);
	
    public static final ItemStack smallgold = IC2Items.getItem("dust", "small_gold");
    
    public static final ItemStack apple = new ItemStack(Items.APPLE);
    public static final ItemStack goldapple = new ItemStack(Items.GOLDEN_APPLE);
    public static final ItemStack diamond = new ItemStack(Items.DIAMOND);
    public static final ItemStack plutonium = IC2Items.getItem("nuclear","plutonium");
    public static final ItemStack iridium = IC2Items.getItem("misc_resource","iridium_ore");
    public static final ItemStack star = new ItemStack(Items.NETHER_STAR);
    
    public static final int [] amps = {16000, 75000, 120000, 450000, 700000, 2500000};

	public static void addCraftingRecipes() {
		addShapedRecipes((plcar), "III","IPI","III", 'I', new ItemStack(Items.BOOK), 'P', coins);
	}
	
	private static void addShapedRecipes(ItemStack output, Object... input) {
		ic2.api.recipe.Recipes.advRecipes.addRecipe(output, input);
	}
	
	 public static void addMachineRecipe() {
		 IRecipeInputFactory input = ic2.api.recipe.Recipes.inputFactory;
		 NBTTagCompound nbt = new NBTTagCompound();
		 nbt.setInteger("minHeat", 50 );
		 NBTTagCompound nbt2 = new NBTTagCompound();
		 
		 nbt2.setInteger("amount", 500);
		 NBTTagCompound nbt3 = new NBTTagCompound();
		 nbt3.setInteger("amount", 1500);
		 NBTTagCompound nbt4 = new NBTTagCompound();
		 nbt4.setInteger("amount", 2500);
		 
		 NBTTagCompound nbt12 = new NBTTagCompound();
		 nbt12.setInteger("amplification", 60000);
		 for (int i = 0; i < amps.length; i++) 
			 addMatterAmplification(input.forStack(apple), amps[i], nbt12);
		 claydustx2.setCount(2);
		 stonedust.setCount(3);
		 addCompressorRecipe(input.forStack(new ItemStack(Items.SLIME_BALL, 9)), new ItemStack(slime));
		  addMaceratorRecipe(input.forStack(plcar), IC2Items.getItem("dust", "coal"));
		   addExtractorRecipe(input.forStack(u235), irpart);
		   
		    addRollingRecipe(input.forStack(rawcarbon, 9), rotorblade);
		     addExtrudingRecipe(input.forOreDict("plankWood", 4), new ItemStack(ladder, 8));
		      addCuttingRecipe(input.forStack(new ItemStack(Items.IRON_INGOT)), new ItemStack(bars, 3));
		      addMinCentrifugeRecipe(input.forStack(glass), sand, nbt);
		      addMedCentrifugeRecipe(input.forStack(dirt), claydustx2, sandx2,nbt);
		      addAdvCentrifugeRecipe(input.forStack(gravel), flintx2, sand, stonedust, nbt );
		      addMinOreWashingRecipe(input.forStack(mossycobble), cobble, nbt2);
		      addMedOreWashingRecipe(input.forStack(dirt), sand, claydust, nbt3);
		      addAdvOreWashingRecipe(input.forStack(netherrack), soulsand, stonedust, smallgold,nbt4 );
	 }
	 private static void addCompressorRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.compressor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addMaceratorRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.macerator.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addExtractorRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.extractor.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	
	 private static void addRollingRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.metalformerRolling.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addCuttingRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.metalformerCutting.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	 
	 private static void addExtrudingRecipe(IRecipeInput input, ItemStack output) {
		 ic2.api.recipe.Recipes.metalformerExtruding.addRecipe(input, (NBTTagCompound)null, false, new ItemStack[] {output});
	 }
	
	private static void addMinCentrifugeRecipe(IRecipeInput input, ItemStack output, NBTTagCompound nbt) {
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output});
	}
	 
	private static void addMedCentrifugeRecipe(IRecipeInput input, ItemStack output, ItemStack output2, NBTTagCompound nbt) {
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output, output2});
	} 
	 
	private static void addAdvCentrifugeRecipe(IRecipeInput input, ItemStack output, ItemStack output2, ItemStack output3, NBTTagCompound nbt) {
		ic2.api.recipe.Recipes.centrifuge.addRecipe(input, (NBTTagCompound)nbt, false, new ItemStack[] {output, output2, output3});
	}
	
	public static void addMinOreWashingRecipe(IRecipeInput input, ItemStack output, NBTTagCompound nbt2) {
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, false, new ItemStack[] {output});
	}
	 
	public static void addMedOreWashingRecipe(IRecipeInput input, ItemStack output, ItemStack output2, NBTTagCompound nbt2) {
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, false, new ItemStack[] {output, output2});
	}
	
	public static void addAdvOreWashingRecipe(IRecipeInput input, ItemStack output, ItemStack output2, ItemStack output3, NBTTagCompound nbt2) {
		ic2.api.recipe.Recipes.oreWashing.addRecipe(input, (NBTTagCompound)nbt2, false, new ItemStack[] {output, output2, output3});
	}
	 
	private static void addMatterAmplification(IRecipeInput input, int amps, NBTTagCompound tag12) {
		ic2.api.recipe.Recipes.matterAmplifier.addRecipe(input, amps, (NBTTagCompound)tag12, false);
	}	 
}


