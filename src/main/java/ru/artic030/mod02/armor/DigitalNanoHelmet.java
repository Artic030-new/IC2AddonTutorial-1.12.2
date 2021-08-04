package ru.artic030.mod02.armor;

import ic2.api.item.HudMode;
import ic2.api.item.IItemHudProvider;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.ItemName;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class DigitalNanoHelmet extends ItemArmorElectric implements IJetpack, IItemHudProvider {

	public DigitalNanoHelmet(ItemName name, String armorName, EntityEquipmentSlot armorType, double maxCharge,
			double transferLimit, int tier) {
		super(name, armorName, armorType, maxCharge, transferLimit, tier);
	}

	@Override
	public boolean doesProvideHUD(ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HudMode getHudMode(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean drainEnergy(ItemStack arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getChargeLevel(ItemStack arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getDropPercentage(ItemStack arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHoverMultiplier(ItemStack arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getPower(ItemStack arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWorldHeightDivisor(ItemStack arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isJetpackActive(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getDamageAbsorptionRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnergyPerDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
