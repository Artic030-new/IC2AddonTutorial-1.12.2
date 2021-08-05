package ru.artic030.mod02.items;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.artic030.mod02.load.ItemLoader;

public class ElectricPotion extends Item implements IElectricItem {

	public static int maxCharge = 1000000;
	public static byte tier = 3;
	public static short TransferLimit = 128;
	public static short preUse = 300;
	
	public ElectricPotion(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(IC2.tabIC2);
		this.setMaxDamage(27);
		ItemLoader.ITEMS.add(this);
	}
	
	@Override
	public boolean canProvideEnergy(ItemStack stack) {

		return false;
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

		return 100000;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		if(ElectricItem.manager.getCharge(stack) >= ElectricPotion.preUse) {
			ElectricItem.manager.discharge(stack, --ElectricPotion.preUse, tier, true, false, false);
			if(!world.isRemote) {
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 100, 0));
			} 
			player.getCooldownTracker().setCooldown(stack.getItem(), 400);
		} else {
			player.getCooldownTracker().setCooldown(stack.getItem(), 40);
			player.sendMessage(new TextComponentString("Внимание! Не хватает энергии для осуществления операции."));
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);	
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);			
	}

}
