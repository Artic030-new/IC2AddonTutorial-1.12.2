package ru.artic030.mod02.machines;

import ic2.api.recipe.IMachineRecipeManager;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntiyTestMachine extends TileEntityElectricMachine implements IHasGui, IGuiValueProvider {
	
	private final int idleEU;
	private final int activeEU;
	private final int maxProgress;
	
	public final InvSlotProcessable inputSlot;
	public final InvSlotOutput outputSlot;
	   public final InvSlotUpgrade upgradeSlot;
	   protected final Redstone redstone;
	
	@GuiSynced public int progress;
	

	public TileEntiyTestMachine(byte tier, byte numberOfOutputs, IMachineRecipeManager recipeSet, int idleEU, int activeEU) {
	      super(15000, 1);
	      this.maxProgress = 120000;
	      this.progress = 0;

	      this.idleEU = idleEU;
	      this.activeEU = activeEU;
	      this.inputSlot = new InvSlotProcessableGeneric(this, "input", 1, recipeSet);
	      this.outputSlot = new InvSlotOutput(this, "output", numberOfOutputs);
	      this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 2);
	      this.redstone = (Redstone)this.addComponent(new Redstone(this));
	   /*   this.comparator.setUpdate(() -> {
	         return this.heat * 15 / 10000;
	      });*/
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
	public void onGuiClosed(EntityPlayer arg0) {
		
	}

	@Override
	public double getGuiValue(String arg0) {
		return (double)(1000 * this.progress / this.maxProgress) / 1000.0D;
	}

}
