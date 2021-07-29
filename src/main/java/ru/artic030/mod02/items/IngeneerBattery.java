package ru.artic030.mod02.items;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.artic030.mod02.load.ItemLoader;

public class IngeneerBattery extends Item implements IElectricItem {

	private int maxCharge = 60000;
	private byte tier = 2;
	private int transfer = 512;
	
	public IngeneerBattery(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(IC2.tabIC2);
		this.setMaxDamage(27);
		ItemLoader.ITEMS.add(this);
	}
	
	public double getDurabilityForDisplay(ItemStack stack) {
		return ((double)this.maxCharge  - (double)ElectricItem.manager.getCharge(stack)) / (double) this.maxCharge;
	}
	
	public boolean showDurabilityBar(ItemStack stack, World world, EntityPlayer player) {
		if(player.isCreative())
			return false;
		else return true;
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return true;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		
		return maxCharge;
	}

	@Override
	public int getTier(ItemStack stack) {
		return tier;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		return transfer;
	}

}
