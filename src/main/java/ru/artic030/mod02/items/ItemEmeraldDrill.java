package ru.artic030.mod02.items;

import java.util.IdentityHashMap;
import java.util.Map;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.init.BlocksItems;
import ic2.core.item.tool.HarvestLevel;
import ic2.core.item.tool.ItemDrill;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEmeraldDrill extends ItemDrill {
	
	public static int maxCharg = 96000;
	public static byte tir = 2;
	public static short perUse = 196;
	public static float eff = 16.0F;
	
	public ItemEmeraldDrill() {
		super((ItemName)null, perUse, HarvestLevel.Diamond, maxCharg, 160, tir, eff);
		((ItemEmeraldDrill)BlocksItems.registerItem(this, new ResourceLocation("mod02", "emerald_drill"))).setUnlocalizedName("emerald_drill");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerModels(ItemName name) {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("mod02:" + "emerald_drill", (String)null));
	}
	
	public String locationReplacer() {
		return "mod02." + super.getUnlocalizedName().substring(4);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if((!world.isRemote) && (IC2.keyboard.isModeSwitchKeyDown(player))) {	
			Map<Enchantment, Integer> ench = new IdentityHashMap<Enchantment, Integer>();
			ench.put(Enchantments.FORTUNE, Integer.valueOf(2));
			ItemStack stack = StackUtil.get(player, hand);
			if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack) == 0 && ElectricItem.manager.getCharge(stack) >= ItemEmeraldDrill.perUse) {
				ench.put(Enchantments.FORTUNE, Integer.valueOf(2));
				IC2.platform.messagePlayer(player, "Режим удачи включён");
			} else {
				ench.clear();
				signalErrorFor(player);
			}
				EnchantmentHelper.setEnchantments(ench, stack);
		}
			return super.onItemRightClick(world, player, hand);
}
	void signalErrorFor(EntityPlayer player) {
		IC2.audioManager.playOnce(player, PositionSpec.Hand, "mod02:dehydratorError.ogg", true, IC2.audioManager.getDefaultVolume() -1.0F);
		player.getCooldownTracker().setCooldown(this, 40);
		player.sendMessage(new TextComponentString("Внимание! Не хватает энергии для осуществления операции."));
	}
	}
