package ru.artic030.mod02.machines;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
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
	
	protected final int idleEU;
	protected final int activeEU;
	protected final int maxProgress;
	
	public final InvSlotProcessable<IRecipeInput, Collection<ItemStack>, ItemStack> inputSlot;
	public final InvSlotOutput outputSlot;
	public final InvSlotUpgrade upgradeSlot;
	
	protected final Redstone redstone;
	
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
	
	protected boolean canRun() {
	        return true;
	    }
	
	protected void updateEntityServer() {
	      super.updateEntityServer();
	      boolean needsInvUpdate = false;
	      boolean canOperate = this.canOperate();
	      if (this.progress >= this.maxProgress) {
	         while(true) {
	            if (this.progress < this.maxProgress || !canOperate) {
	               needsInvUpdate = true;
	               break;
	            }

	            this.operate();
	            this.progress -= this.maxProgress;
	            canOperate = this.canOperate();
	         }
	      }

	      boolean spinUp;
	      if (this.canRun()) {
	         if (canOperate && this.energy.useEnergy((double)this.activeEU)) {
	            spinUp = true;
	            this.progress += this.heat;
	            this.updateSound(0);
	         } else {
	            spinUp = this.redstone.hasRedstoneInput();
	            this.progress = 0;
	            if (spinUp && !this.energy.useEnergy((double)this.idleEU)) {
	               spinUp = false;
	            } else {
	               this.updateSound(2);
	            }
	         }
	      } else {
	         spinUp = false;
	      }

	      if (spinUp) {
	         this.heat = (short)Math.min(10000, this.heat + 1);
	      } else {
	         this.heat = (short)Math.max(0, this.heat - 2);
	         if (this.heat <= 0) {
	            if (this.getActive()) {
	               this.updateSound(1);
	            }
	         } else {
	            this.updateSound(2);
	         }
	      }

	      Iterator var4 = this.upgradeSlot.iterator();

	      while(var4.hasNext()) {
	         ItemStack stack = (ItemStack)var4.next();
	         if (!StackUtil.isEmpty(stack) && stack.func_77973_b() instanceof IUpgradeItem) {
	            needsInvUpdate |= ((IUpgradeItem)stack.func_77973_b()).onTick(stack, this);
	         }
	      }

	      this.setActive(this.heat > 0);
	      if (needsInvUpdate) {
	         this.func_70296_d();
	      }

	   }
	
	public boolean canOperate() {
        if (this.inputSlot.isEmpty()) {
            return false;
        }
        final MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = (MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack>)this.inputSlot.process();
        return output != null && this.outputSlot.canAdd((Collection)output.getOutput());
    }
    
    public void operate() {
        assert this.canOperate();
        final MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack> output = (MachineRecipeResult<IRecipeInput, Collection<ItemStack>, ItemStack>)this.inputSlot.process();
        this.processUpgrades((Collection<ItemStack>)output.getOutput());
        this.outputSlot.add((Collection)output.getOutput());
        this.inputSlot.consume((MachineRecipeResult)output);
    }
    
    protected void processUpgrades(final Collection<ItemStack> output) {
        for (final ItemStack stack : this.upgradeSlot) {
            if (stack != null && stack.func_77973_b() instanceof IUpgradeItem) {
                ((IUpgradeItem)stack.func_77973_b()).onProcessEnd(stack, (IUpgradableBlock)this, (Collection)output);
            }
        }
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
