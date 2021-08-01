package ru.artic030.mod02.load;


import ic2.core.block.ITeBlock;
import ic2.core.block.TileEntityBlock;
import ic2.core.ref.TeBlock.DefaultDrop;
import ic2.core.ref.TeBlock.HarvestTool;
import ic2.core.util.Util;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum AdvancedMachineTEs implements ITeBlock {
   rotary_macerator(ru.artic030.mod02.machines.TileEntityCentrifuge.class, 0)
  ;

   private final Class<? extends TileEntityBlock> teClass;
   private final int itemMeta;
   private TileEntityBlock dummyTe;
   public static final ResourceLocation IDENTITY = new ResourceLocation("advanced_machines", "machines");

   private AdvancedMachineTEs(Class<? extends TileEntityBlock> teClass, int itemMeta) {
      this.teClass = teClass;
      this.itemMeta = itemMeta;
      GameRegistry.registerTileEntity(teClass, "advanced_machines:" + this.getName());
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
      return IDENTITY;
   }

   public Class<? extends TileEntityBlock> getTeClass() {
      return this.teClass;
   }

   public boolean hasActive() {
      return true;
   }

   public float getHardness() {
      return 5.0F;
   }

   public float getExplosionResistance() {
      return 10.0F;
   }

   public HarvestTool getHarvestTool() {
      return HarvestTool.Pickaxe;
   }

   public DefaultDrop getDefaultDrop() {
      return DefaultDrop.AdvMachine;
   }

   public boolean allowWrenchRotating() {
      return false;
   }

   public Set getSupportedFacings() {
      return Util.horizontalFacings;
   }

   public EnumRarity getRarity() {
      return EnumRarity.UNCOMMON;
   }

   public Material getMaterial() {
      return Material.BARRIER;
   }

   
   public String[] getRecipeCategories() {
      return new String[]{this.getName()};
   }

  
  
   public static void buildDummies() {
      ModContainer mc = Loader.instance().activeModContainer();
      if(mc != null && "advanced_machines".equals(mc.getModId())) {
         AdvancedMachineTEs[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            AdvancedMachineTEs block = var1[var3];
            if(block.teClass != null) {
               try {
                  block.dummyTe = (TileEntityBlock)block.teClass.newInstance();
               } catch (Exception var6) {
                  if(Util.inDev()) {
                     var6.printStackTrace();
                  }
               }
            }
         }

      } else {
         throw new IllegalAccessError("Don\'t mess with this please.");
      }
   }

   public TileEntityBlock getDummyTe() {
      return this.dummyTe;
   }
}
