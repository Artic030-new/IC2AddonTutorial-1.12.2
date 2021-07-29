package ru.artic030.mod02.items;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2;
import ic2.core.item.reactor.AbstractReactorComponent;
import ic2.core.ref.ItemName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.artic030.mod02.load.ItemLoader;

public class CustomReactorPlating extends AbstractReactorComponent {
	
	   private final int maxHeatAdd;
	   private final float effectModifier;
	   
	   public CustomReactorPlating(String name) {
		   super((ItemName)null);
		   this.setUnlocalizedName(name);
		   this.setRegistryName(name);
		   this.maxHeatAdd = 10000;
		   this.effectModifier = 0.33F;
		   this.maxStackSize = 1;
		   
		   this.setCreativeTab(IC2.tabIC2);
		   ItemLoader.ITEMS.add(this);
	   }

	   public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
	      if (heatrun) {
	         reactor.setMaxHeat(reactor.getMaxHeat() + this.maxHeatAdd);
	         reactor.setHeatEffectModifier(reactor.getHeatEffectModifier() * this.effectModifier);
	      }

	   }

	   public float influenceExplosion(ItemStack stack, IReactor reactor) {
		   return this.effectModifier >= 1.0F ? 0.0F : this.effectModifier;
	   }
	}