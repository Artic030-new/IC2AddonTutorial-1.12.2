package ru.artic030.mod02.items;

import java.util.HashMap;
import java.util.Map;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.player.EntityPlayer;
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
	public static short TransferLimit = 512;
	public static int preUse = 100000;
	public static int mode = 0;
	
	private Map<Integer, String> supportedEffects = new HashMap<Integer, String>();
	
	public ElectricPotion(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(IC2.tabIC2);
		this.setMaxDamage(27);
		supportedEffects.put(0, "Скорость");
		supportedEffects.put(1, "Сила");
		supportedEffects.put(2, "Восстановление");
		supportedEffects.put(3, "Прыгучесть");
		supportedEffects.put(4, "Невидимость");
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

		return TransferLimit;
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(world.isRemote)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		if(ElectricItem.manager.getCharge(stack) >= ElectricPotion.preUse) {
			if((!world.isRemote) && (IC2.keyboard.isModeSwitchKeyDown(player))) {
				if(mode <= supportedEffects.size() - 2) {
					mode++;
					ElectricItem.manager.discharge(stack, --ElectricPotion.preUse / 100, tier, true, false, false);	
					player.sendMessage(new TextComponentString("Режим зелья: " + supportedEffects.get(mode) + " активирован."));
				} else {
					mode = 0;
					ElectricItem.manager.discharge(stack, --ElectricPotion.preUse / 100, tier, true, false, false);
					player.sendMessage(new TextComponentString("Режим зелья: " + supportedEffects.get(mode) + " активирован."));
				}
		} else {
			if(!world.isRemote) {
				switch(mode) {
					case 0: player.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1000, 0)); break;
					case 1: player.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 1000, 0)); break;
					case 2: player.addPotionEffect(new PotionEffect(Potion.getPotionById(6), 1000, 0)); break;
					case 3: player.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 1000, 0)); break;
					case 4: player.addPotionEffect(new PotionEffect(Potion.getPotionById(14), 1000, 0)); break;
					default: break;
				}
				ElectricItem.manager.discharge(stack, --ElectricPotion.preUse, tier, true, false, false);	
			} 
			player.getCooldownTracker().setCooldown(stack.getItem(), 400);
		}
		} else {
			player.getCooldownTracker().setCooldown(stack.getItem(), 40);
			player.sendMessage(new TextComponentString("Внимание! Не хватает энергии для осуществления операции."));
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);	
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);			
	}

}
