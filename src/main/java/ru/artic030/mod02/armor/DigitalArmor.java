package ru.artic030.mod02.armor;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IItemHudProvider;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.ItemName;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class DigitalArmor extends ItemArmorElectric implements IJetpack, IItemHudProvider {

	public DigitalArmor(ItemName name, EntityEquipmentSlot armorType) {
		super((ItemName)null, "digital", armorType, 100000.0D, 100.0D, 2);
	}
	
	public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
	      return ElectricItem.manager.getCharge(stack) > 0.0D;
	   }

	@Override
	public boolean doesProvideHUD(ItemStack stack) {
		return false;
	}

	@Override
	public HudMode getHudMode(ItemStack stack) {
		return null;
	}

	@Override
	public boolean drainEnergy(ItemStack arg0, int arg1) {
		return false;
	}

	@Override
	public double getChargeLevel(ItemStack arg0) {
		return 0;
	}

	@Override
	public float getDropPercentage(ItemStack arg0) {
		return 0;
	}

	@Override
	public float getHoverMultiplier(ItemStack arg0, boolean arg1) {
		return 0;
	}

	@Override
	public float getPower(ItemStack arg0) {
		return 0;
	}

	@Override
	public float getWorldHeightDivisor(ItemStack arg0) {
		return 0;
	}

	@Override
	public boolean isJetpackActive(ItemStack arg0) {
		return false;
	}

	@Override
	public double getDamageAbsorptionRatio() {
		return 0;
	}

	@Override
	public int getEnergyPerDamage() {
		return 0;
	}

}
