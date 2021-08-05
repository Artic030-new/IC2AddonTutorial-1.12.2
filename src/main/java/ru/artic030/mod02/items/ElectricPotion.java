package ru.artic030.mod02.items;

import ic2.api.item.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ElectricPotion extends Item implements IElectricItem {

	@Override
	public boolean canProvideEnergy(ItemStack stack) {

		return false;
	}

	@Override
	public double getMaxCharge(ItemStack stack) {

		return 0;
	}

	@Override
	public int getTier(ItemStack stack) {

		return 0;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {

		return 0;
	}

}
