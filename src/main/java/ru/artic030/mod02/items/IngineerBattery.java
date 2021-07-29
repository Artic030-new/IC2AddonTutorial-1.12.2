package ru.artic030.mod02.items;

import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IngineerBattery extends Item implements IElectricItem{

	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTier(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		// TODO Auto-generated method stub
		return 0;
	}

}
