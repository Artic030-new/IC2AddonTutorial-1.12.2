package ru.artic030.mod02.items;

import java.util.ArrayDeque;
import java.util.Queue;

import ic2.api.item.ICustomDamageItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.ItemName;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.artic030.mod02.load.ItemLoader;

public class Cesium137 extends ItemReactorUranium implements IReactorComponent, ICustomDamageItem {
	
	 public Cesium137(String name) {
	      super((ItemName)null, 1, 10000);
	      this.setRegistryName("reacttorcomponent030", name);
	    
	    //  this.setTranslationKey(this.getRegistryName().toString());
	      this.setCreativeTab(CreativeTabs.REDSTONE);
	      ItemLoader.ITEMS.add(this);
	   }

	   @SideOnly(Side.CLIENT)
	   public void initModel() {
	      final ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
	      ModelBakery.registerItemVariants(this, new ResourceLocation[]{model});
	      ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
	    	  @Override
	         public ModelResourceLocation getModelLocation(ItemStack stack) {
	            return model;
	         }
	      });
	   }

	   public String func_77658_a() {
	      return "item." + this.getRegistryName().toString() + ".name";
	   }

	   protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
	      return heat / 2;
	   }

	   public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
	      if (!heatrun) reactor.addOutput(.1F);
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
	         throw new RuntimeException("invalid cell count: " + this.numberOfCells);
	      case 4:
	         return ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium);
	      }
	   }
}
