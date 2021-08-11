package ru.artic030.mod02.machines;

import java.util.List;


import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Redstone;
import ic2.core.block.comp.Redstone.IRedstoneChangeHandler;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.Localization;
import ic2.core.network.GuiSynced;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.artic030.mod02.gui.BackgroundlessDynamicGUI;

public class DigitalGeneratorTE extends TileEntityInventory implements IMultiEnergySource, IHasGui, INetworkClientTileEntityEventListener {
	
	@GuiSynced
	public int tier;
	protected Redstone redstone;
	private boolean addedToEnet;
	@GuiSynced
	public int generation;
	@GuiSynced
	public static int currentOutput;
	public DigitalGeneratorTE() {
		this.generation = 4;
		this.tier = 1;
		this.redstone = (Redstone)this.addComponent(new Redstone(this));
		this.redstone.subscribe(new IRedstoneChangeHandler() {
			public void onRedstoneChange(int newLevel) {
				DigitalGeneratorTE.this.setActive(newLevel <= 0);
			}
		});
	   }
	@Override
	protected void onLoaded() {
		super.onLoaded();
		if (!this.world.isRemote) {
			this.addedToEnet = !MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
		}
	}
	@Override
	protected void onUnloaded() {
		super.onUnloaded();
		if (this.addedToEnet) {
			this.addedToEnet = MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
	}
	@Override
   public void readFromNBT(NBTTagCompound nbt) {
      super.readFromNBT(nbt);
      this.generation = nbt.getInteger("production");
      this.tier = nbt.getInteger("tier");
   }
	@Override
   public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
      super.writeToNBT(nbt);
      nbt.setInteger("production", this.generation);
      nbt.setInteger("tier", this.tier);
      return nbt;
   }
	
	protected void updateEntityServer() {
	      super.updateEntityServer();
	      currentOutput++;
	}
	
	public double getCurrentOutput() {
		return DigitalGeneratorTE.currentOutput;
	}
	
	@Override
	public void onPlaced(ItemStack stack, EntityLivingBase placer, EnumFacing facing) {
      super.onPlaced(stack, placer, facing);
      if (!this.world.isRemote) {
         this.setActive(true);
      }

   }

   public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
      return true;
   }

   public int getSourceTier() {
      return this.tier;
   }

   public double getOfferedEnergy() {
	 int yPos = this.pos.getY();
	 
	 if(yPos >=8) {
		  return 0;
	  } else if ( (!this.world.isDaytime() ) && yPos == 5 ) {
		  currentOutput = this.generation;
		  return this.generation;
	  } else if ( (!this.world.isDaytime() ) && yPos >= 6 ) {
		  currentOutput = this.generation - 1;
		  return this.generation - 1;
	  } else if ( (!this.world.isDaytime() ) && yPos == 4 ) {
		  currentOutput =  this.generation + 3;
		  return this.generation + 3;
	  }
	  else return 0;	
   }
   
   public String getMaxOutput()
   {
     return String.format("%s %.0f", new Object[] { Localization.translate("mod02.gui.digital_generator.produsing"), (double)this.getMultipleEnergyPacketAmount() });
   }

   public void drawEnergy(double amount) {}

   public boolean sendMultipleEnergyPackets() {
      return (double)this.generation - EnergyNet.instance.getPowerFromTier(this.tier) > 0.0D;
   }

   public int getMultipleEnergyPacketAmount() {
      return (int)Math.round((double)this.generation);
   }
   @Override
   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
      super.addInformation(stack, tooltip, advanced);
      tooltip.add(Localization.translate("ic2.item.tooltip.PowerTier", new Object[]{"Variable"}));
   }
   @Override
   public ContainerBase<? extends TileEntityInventory> getGuiContainer(EntityPlayer player) {
      return DynamicContainer.create(this, player, GuiParser.parse(this.teBlock));
   }
   @Override
   @SideOnly(Side.CLIENT)
   public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {
      return BackgroundlessDynamicGUI.create(this, player, GuiParser.parse(this.teBlock));
   }

   public void onGuiClosed(EntityPlayer player) {}
   
   protected void changeProduction(int value) {
      this.generation += value;
      if (this.generation < 0) {
    	  this.generation = 0;
      }
   }

   public String getTier() {
      return Integer.toString(this.tier);
   }
   
    @Override
	public void onNetworkEvent(EntityPlayer player, int event) {}
}