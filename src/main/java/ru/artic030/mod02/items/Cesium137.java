package ru.artic030.mod02.items;

import ic2.api.item.ICustomDamageItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.item.ItemStack;
import ru.artic030.mod02.load.ItemLoader;

public class Cesium137 extends ItemReactorUranium implements IReactorComponent, ICustomDamageItem {
	
	 public Cesium137(String name) {
		 super((ItemName)null, 1, 10000);
		 this.setUnlocalizedName(name); 
		 this.setRegistryName(name);
		 this.setCreativeTab(IC2.tabIC2);
		 ItemLoader.ITEMS.add(this);
	 }

	 protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		 return heat / 2;
     }

	 public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		 if (!heatrun) reactor.addOutput(20.0F);
		 return true;
	 }
	 
	 @Override
	 public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
		 if (!reactor.produceEnergy()) return; 
		 super.processChamber(stack, reactor, x, y, heatRun);
		 applyCustomDamage(stack, 20, null);	    
	 }

	 protected ItemStack getDepletedStack(ItemStack arg0, IReactor arg1) {
		 switch(this.numberOfCells) {
		 	case 1:
		 		return ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium); 
		 	case 2:
		 		return ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium); 
		 	case 3:
		 		default:
		 			throw new RuntimeException("Некорректное значение ТВЭЛ: " + this.numberOfCells);
		 	case 4:
		 		return ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium);
	      }
	   }
}
