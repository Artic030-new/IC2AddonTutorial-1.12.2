package ru.artic030.mod02.machines;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.DynamicGui;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.gui.dynamic.IGuiValueProvider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntiyTestMachine extends TileEntityElectricMachine implements IHasGui, IGuiValueProvider {

	public TileEntiyTestMachine(int maxEnergy, int tier) {
		super(15000, 1);
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
		return 0;
	}

}
