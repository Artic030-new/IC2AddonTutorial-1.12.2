package ru.artic030.mod02.items;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.artic030.mod02.Mod02;
import ru.artic030.mod02.load.ItemLoader;
import ru.artic030.mod02.utils.IHasModel;

public class EnderCannon extends Item implements IHasModel, IElectricItem {
	
	public static int maxCharge = 128000;
	public static byte tier = 2;
	public static short TransferLimit = 512;
	public static short preUse = 1200;
	public static short enderUse = 8888;

	public EnderCannon(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setMaxDamage(27);
		ItemLoader.ITEMS.add(this);
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		return this.maxCharge;
	}

	@Override
	public int getTier(ItemStack stack) {
		return this.tier;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		return this.TransferLimit;
	}

	@Override
	public void registerModels() {
		Mod02.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	public double getDurabilityForDisplay(ItemStack stack) {
		return ((double)EnderCannon.maxCharge  - (double)ElectricItem.manager.getCharge(stack)) / (double) this.maxCharge;
	}
	
	public boolean showDurabilityBar(ItemStack stack, World world, EntityPlayer player) {
		if(player.isCreative())
			return false;
		else return true;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		if(ElectricItem.manager.getCharge(stack) >= EnderCannon.enderUse) {
			ElectricItem.manager.discharge(stack, --EnderCannon.enderUse, tier, true, false, false);
			player.spawnSweepParticles();
			if(!world.isRemote) {
				EntityEnderPearl entitysnowball = new EntityEnderPearl(world, player);
				entitysnowball.shoot(player, player.rotationPitch, player.rotationYaw, 0.2F, 1.5F, 1.0F);
				world.spawnEntity(entitysnowball);
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);			
	}
}
