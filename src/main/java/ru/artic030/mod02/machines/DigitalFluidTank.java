package ru.artic030.mod02.machines;

import java.util.Set;

import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.comp.Fluids;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.network.GuiSynced;
import ic2.core.util.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidTank;

public class DigitalFluidTank extends TileEntityInventory implements IHasGui, IUpgradableBlock {
	
	public final InvSlotUpgrade upgradeSlots = new InvSlotUpgrade(this, "upgrade", 4);
	@GuiSynced protected final FluidTank fluidTank;
	
	protected final Fluids fluids = (Fluids)this.addComponent(new Fluids(this));
	public DigitalFluidTank() {
	      this.fluidTank = this.fluids.addTank("fluid", 1000000);
	      this.comparator.setUpdate(() -> {
	         return this.fluidTank.getFluidAmount() == 0 ? 0 : (int)Util.lerp(1.0F, 15.0F, (float)this.fluidTank.getFluidAmount() / (float)this.fluidTank.getCapacity());
	      });
	   }
	
	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public void markDirty() {
		
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public double getEnergy() {
		return 0;
	}

	@Override
	public boolean useEnergy(double amount) {
		return false;
	}

	@Override
	public Set<UpgradableProperty> getUpgradableProperties() {
		return null;
	}

	@Override
	public GuiScreen getGui(EntityPlayer arg0, boolean arg1) {
		return null;
	}

	@Override
	public ContainerBase<?> getGuiContainer(EntityPlayer arg0) {
		return null;
	}

	@Override
	public void onGuiClosed(EntityPlayer arg0) {
		
	}

}
