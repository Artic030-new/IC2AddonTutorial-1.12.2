package ru.artic030.mod02.items;


import java.util.EnumSet;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.init.BlocksItems;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.ItemElectricTool;
import ic2.core.item.tool.ToolClass;
import ic2.core.ref.ItemName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Dehydrator extends ItemElectricTool {
	
	public int dehydrationEnergyCost = 8888;
	
	public Dehydrator() {
		super((ItemName)null, 1200, HarvestLevel.Stone, EnumSet.of(ToolClass.Shovel, ToolClass.Sword));
		((Dehydrator)BlocksItems.registerItem(this, new ResourceLocation("mod02", "dehydrator"))).setUnlocalizedName("dehydrator");
		this.maxCharge = 120000;
		this.transferLimit = 20000;
		this.tier = 2;
		this.attackDamage = 12.0F;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels(ItemName name) {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("mod02:" + "dehydrator", (String)null));
	}
	
	public String locationReplacer() {
		return "mod02." + super.getUnlocalizedName().substring(4);
	}
	
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitx, float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);
		boolean isClay = state.getBlock() == Blocks.CLAY;
		boolean isDirt = state.getBlock() == Blocks.DIRT;
		boolean isRock = state.getBlock() == Blocks.COBBLESTONE;
		boolean isGrass= state.getBlock() == Blocks.TALLGRASS;
		boolean isSandrock = state.getBlock() == Blocks.SANDSTONE;
		boolean isDeadGrass = state.getBlock() == Blocks.DEADBUSH;
		if(ElectricItem.manager.getCharge(stack) >= this.dehydrationEnergyCost) {
			if(isClay || isDirt || isRock)
				world.setBlockState(pos, Blocks.SAND.getDefaultState());
			if(state.getBlock() == Blocks.GRASS)
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
			if(state.getBlock() == Blocks.STONE)
				world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
			if(state.getBlock() == Blocks.TALLGRASS)
				world.setBlockState(pos, Blocks.DEADBUSH.getDefaultState());
			if(isClay || isDirt || isRock || isGrass || isSandrock || isDeadGrass)
				ElectricItem.manager.discharge(stack, --this.dehydrationEnergyCost, tier, true, false, false);
			IC2.audioManager.playOnce(player, PositionSpec.Hand, "mod02:dehydratorUse.ogg", true, IC2.audioManager.getDefaultVolume() -1.0F);
			return EnumActionResult.SUCCESS;
		}
		IC2.audioManager.playOnce(player, PositionSpec.Hand, "mod02:dehydratorError.ogg", true, IC2.audioManager.getDefaultVolume() -1.0F);
		player.getCooldownTracker().setCooldown(this, 40);
		player.sendMessage(new TextComponentString("Внимание! Не хватает энергии для осуществления операции."));
			return EnumActionResult.FAIL;
	}

}
