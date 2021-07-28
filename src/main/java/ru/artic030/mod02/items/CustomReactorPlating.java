package ru.artic030.mod02.items;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomReactorPlating extends Item implements IReactorComponent {

	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY,
			int pulseX, int pulseY, boolean heatrun) {
		// TODO Auto-generated method stu
		return false;
	}

	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		// TODO Auto-generated method stub
		return 0;
	}

}
