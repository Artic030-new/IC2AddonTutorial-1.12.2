package ru.artic030.mod02.armor;

import java.util.Iterator;
import java.util.LinkedList;

import ic2.api.item.ElectricItem;
import ic2.api.item.HudMode;
import ic2.api.item.IItemHudProvider;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.ItemTinCan;
import ic2.core.item.armor.ItemArmorElectric;
import ic2.core.item.armor.jetpack.IJetpack;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DigitalArmor extends ItemArmorElectric implements IJetpack, IItemHudProvider {

	public DigitalArmor(ItemName name, EntityEquipmentSlot armorType) {
		super((ItemName)null, "digital", armorType, 100000.0D, 100.0D, 2);
	}
	
	public boolean addsProtection(EntityLivingBase entity, EntityEquipmentSlot slot, ItemStack stack) {
		return ElectricItem.manager.getCharge(stack) > 0.0D;
	}
	
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
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
	public double getChargeLevel(ItemStack stack) {
		return ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
	}

	@Override
	public float getDropPercentage(ItemStack arg0) {
		return 0.25F;
	}

	@Override
	public float getHoverMultiplier(ItemStack arg0, boolean arg1) {
		return 0;
	}

	@Override
	public float getPower(ItemStack arg0) {
		return 1.0F;
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
	
	public int getItemEnchantability() {
		return 0;
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
	      boolean ret = false;
	      switch(this.armorType) {
	      case HEAD:
	         if (ElectricItem.manager.canUse(stack, 1000.0D)) {
	            player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 100, 0));
	            ElectricItem.manager.use(stack, 1000.0D, (EntityLivingBase)null);
	            ret = true;
	         } 
	         break;
	      case CHEST:
	         break;
	      case LEGS:
	         break;
	      case FEET:
	    	  break;
		default:
			break;
	      }
	      if (ret) {
	         player.inventoryContainer.detectAndSendChanges();
	      }

	   }
}
