package ru.artic030.mod02.load;

import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.ref.TeBlock.DefaultDrop;
import ic2.core.ref.TeBlock.HarvestTool;
import ic2.core.util.Util;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.artic030.mod02.Mod02;
import ru.artic030.mod02.machines.DigitalGeneratorTE;

public enum MachinesTE implements ITeBlock {
	
	digital_generator(ru.artic030.mod02.machines.DigitalGeneratorTE.class, 1, EnumRarity.RARE);
	
	private final Class<? extends TileEntityBlock> teClass;
	private final int itemMeta;
	private final EnumRarity rarity;
	private TileEntityBlock dummyTe;
	private static final MachinesTE[] VALUES = values();
	public static final ResourceLocation LOCATION = new ResourceLocation("mod02", "machines");
	
	private MachinesTE(Class<DigitalGeneratorTE> teClass, int itemMeta, EnumRarity rarity) {
		this.teClass = teClass;
		this.itemMeta = itemMeta;
		this.rarity = rarity;
		GameRegistry.registerTileEntity(teClass, "mod02:" + this.getName());
	}
	   
   public TileEntityBlock getDummyTe() {
	      return this.dummyTe;
	   }

   public boolean hasItem() {
      return true;
   }

   public String getName() {
      return this.name();
   }

   public int getId() {
      return this.itemMeta;
   }

   public ResourceLocation getIdentifier() {
      return LOCATION;
   }

   public Class<? extends TileEntityBlock> getTeClass() {
      return this.teClass;
   }

   public boolean hasActive() {
      return this == digital_generator;
   }

   public float getHardness() {
      return 3.0F;
   }

   public float getExplosionResistance() {
      return 15.0F;
   }

   public HarvestTool getHarvestTool() {
      return HarvestTool.Pickaxe;
   }

   public DefaultDrop getDefaultDrop() {
      return DefaultDrop.Self;
   }

   public boolean allowWrenchRotating() {
      return false;
   }

   public Set<EnumFacing> getSupportedFacings() {
      return Util.horizontalFacings;
   }

   public EnumRarity getRarity() {
      return this.rarity;
   }

   public static void buildDummies() {
      ModContainer mc = Loader.instance().activeModContainer();
      if (mc != null && Mod02.MODID.equals(mc.getModId())) {
    	  MachinesTE[] var1 = VALUES;
         int var2 = var1.length;
         for(int var3 = 1; var3 < var2; ++var3) {
        	 MachinesTE block = var1[var3];
             if (block.teClass != null) {
            	 try {
            		 block.dummyTe = (TileEntityBlock)block.teClass.newInstance();
            	 } catch (Exception var6) {
                  if (Util.inDev()) {
                     var6.printStackTrace();
                  }
               }
            }
         }
      } else {
         throw new IllegalAccessError("Don't mess with this please.");
      }
   }

 
}
