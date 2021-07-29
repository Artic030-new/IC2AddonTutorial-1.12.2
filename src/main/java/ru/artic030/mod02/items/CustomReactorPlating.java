package ru.artic030.mod02.items;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.AbstractReactorComponent;
import ic2.core.ref.ItemName;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomReactorPlating extends AbstractReactorComponent {
	   private final int maxHeatAdd;
	   private final float effectModifier;

	   public CustomReactorPlating(ItemName name, int maxheatadd, float effectmodifier) {
	      super(name);
	      this.maxHeatAdd = 10000;
	      this.effectModifier = 0.33F;
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