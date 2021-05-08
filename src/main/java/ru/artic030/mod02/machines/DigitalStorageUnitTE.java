package ru.artic030.mod02.machines;

import ic2.api.energy.prefab.BasicEnergyTe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class DigitalStorageUnitTE extends BasicEnergyTe.Sink implements ITickable {

	private int progress;
	
	public DigitalStorageUnitTE(int capacity, int tier){
		super(6000000, 2);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.progress = nbt.getInteger("progress");
	}
	  
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt = super.writeToNBT(nbt); 
		nbt.setInteger("progress", progress);
		return nbt;
	}
	  
	@Override
	public void update()  {
		if (hasWorld() && getEnergyBuffer().useEnergy(50) && ++progress >= 100) {
			progress = 0;
			progress++;
	   }
	}
}


