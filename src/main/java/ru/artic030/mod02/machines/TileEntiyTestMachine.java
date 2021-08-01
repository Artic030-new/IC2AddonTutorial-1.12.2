package ru.artic030.mod02.machines;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GuiSynced;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntiyTestMachine extends TileEntityElectricMachine implements IHasGui, IGuiValueProvider, IUpgradableBlock {
	
	public final int idleEU;
	public final int activeEU;
	public final int maxProgress;
	
	public final InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> inputSlot;
	public final InvSlotOutput outputSlot;
	public final InvSlotUpgrade upgradeSlot;
	
	public final Redstone redstone;
	
	@GuiSynced public int progress;
	

	public TileEntiyTestMachine(byte tier, byte numberOfOutputs, IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> recipeSet, int idleEU, int activeEU) {
		super(15000, 1);
		this.maxProgress = 100000;
		this.progress = 0;
		
		this.idleEU = idleEU;
		this.activeEU = activeEU;
		
		this.inputSlot = new InvSlotProcessableGeneric(this, "input", 1, recipeSet);
		this.outputSlot = new InvSlotOutput(this, "output", numberOfOutputs);
		this.upgradeSlot  = new InvSlotUpgrade(this, "upgrade", 2);
		
		this.redstone = (Redstone)this.addComponent(new Redstone(this));

	   }
	
	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getInteger("progress");
    }
	
	@Override
    public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("progress", this.progress);
        return nbt;
    }
    
    protected void onUnloaded() {
        super.onUnloaded();
    }

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getGui(EntityPlayer p, boolean arg1) {
		return DynamicGui.create(this, p, GuiParser.parse(this.teBlock));
	}

	@Override
	public ContainerBase<? extends TileEntityElectricMachine> getGuiContainer(EntityPlayer p) {
		return DynamicContainer.create(this, p, GuiParser.parse(this.teBlock));
	}

	@Override
	public void onGuiClosed(EntityPlayer arg0) {}

	@Override
	public double getGuiValue(String arg0) {
		return (double)(1000 * this.progress / this.maxProgress) / 1000.0D;
	}

	@Override
	public double getEnergy() {
		return 0;
	}

	@Override
	public boolean useEnergy(double amount) {
		return this.energy.useEnergy(amount);
	}

	@Override
	public Set<UpgradableProperty> getUpgradableProperties() {
	      return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
	}

}
