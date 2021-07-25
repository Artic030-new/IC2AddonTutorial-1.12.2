package ru.artic030.mod02.items;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.artic030.mod02.load.ItemLoader;

public class Cesium137 extends Item implements IReactorComponent {
	
    private int numberOfCells;
	private int maxDamage;

	public Cesium137(String name) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.REDSTONE);
		ItemLoader.ITEMS.add(this);
	}

	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		return true;
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		if (!reactor.produceEnergy()) {
		      return;
		}
		int basePulses = 1 + this.numberOfCells / 2;
		for (int iteration = 0; iteration < this.numberOfCells; iteration++) {
			int pulses = basePulses;
			if (!heatrun) {
				for (int i = 0; i < pulses; i++) {
		          acceptUraniumPulse(stack, reactor, stack, x, y, x, y, heatrun);
		        }
		        pulses = pulses + (checkPulseable(reactor, x - 1, y, stack, x, y, heatrun) + checkPulseable(reactor, x + 1, y, stack, x, y, heatrun) + checkPulseable(reactor, x, y - 1, stack, x, y, heatrun) + checkPulseable(reactor, x, y + 1, stack, x, y, heatrun));
		    } else {
		    	pulses = pulses + (checkPulseable(reactor, x - 1, y, stack, x, y, heatrun) + checkPulseable(reactor, x + 1, y, stack, x, y, heatrun) + checkPulseable(reactor, x, y - 1, stack, x, y, heatrun) + checkPulseable(reactor, x, y + 1, stack, x, y, heatrun));
		        int heat = triangularNumber(pulses) * 4;
		        heat = getFinalHeat(stack, reactor, x, y, heat);
		        Queue<ItemStackCoord> heatAcceptors = new ArrayDeque<>();
		        checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
		        checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
		        checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
		        checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
		        while ((!heatAcceptors.isEmpty()) && (heat > 0)) {
		        	int dheat = heat / heatAcceptors.size();
		        	heat -= dheat;
		        	ItemStackCoord acceptor = (ItemStackCoord)heatAcceptors.remove();
		        	IReactorComponent acceptorComp = (IReactorComponent)acceptor.stack.getItem();
		        	dheat = acceptorComp.alterHeat(acceptor.stack, reactor, acceptor.x, acceptor.y, dheat);
		        	heat += dheat;
		        }
		        if (heat > 0) {
		          reactor.addHeat(heat);
		        }
		      }
		    }
		    if ((!heatrun) && (getCustomDamage(stack) >= getMaxCustomDamage(stack) - 1)) {
		    	reactor.setItemAt(x, y, getDepletedStack(stack, reactor));
		    } else if (!heatrun) {
		    	applyCustomDamage(stack, 1, null);
		    }
	}
	
	public int getCustomDamage(ItemStack stack) {
		if (!stack.isEmpty()) {
	      return 0;
	    }
	    return stack.getTagCompound().getInteger("advDmg");
	  }
	
	protected static int triangularNumber(int x) {
		return (x * x + x) / 2;
	}
	
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
	    setCustomDamage(stack, getCustomDamage(stack) + damage);
	    return true;
	}
	
	public void setCustomDamage(ItemStack stack, int damage) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
	    nbt.setInteger("advDmg", damage);
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		return true;
	}

	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return true;
	}

	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return 1000;
	}

	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return 10;
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		return 10;
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		return 10000;
	}
	
	protected void checkHeatAcceptor(IReactor reactor, int x, int y, Collection<ItemStackCoord> heatAcceptors) {
		ItemStack stack = reactor.getItemAt(x, y);
	    if ((stack != null) && ((stack.getItem() instanceof IReactorComponent)) && 
	      (((IReactorComponent)stack.getItem()).canStoreHeat(stack, reactor, x, y))) {
	      heatAcceptors.add(new ItemStackCoord(stack, x, y));
	    }
	  }
	
	 protected int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
	    return heat;
	  }
	
	protected ItemStack getDepletedStack(ItemStack stack, IReactor reactor) {
	    ItemStack ret;
	   
	    switch (this.numberOfCells)
	    {
	    case 1: 
	      ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_uranium); break;
	    case 2: 
	      ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_dual_uranium); break;
	    case 4: 
	      ret = ItemName.nuclear.getItemStack(NuclearResourceType.depleted_quad_uranium); break;
	    case 3: 
	    default: 
	      throw new RuntimeException("invalid cell count: " + this.numberOfCells);
	    }
	 
	    return ret.copy();
	  }
	protected static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
	    ItemStack other = reactor.getItemAt(x, y);
	    if ((other != null) && ((other.getItem() instanceof IReactorComponent)) && 
	      (((IReactorComponent)other.getItem()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun))) {
	      return 1;
	    }
	    return 0;
	}
	public int getMaxCustomDamage(ItemStack stack)
	  {
	    return this.maxDamage;
	  }
}

class ItemStackCoord {
  public final ItemStack stack;
  public final int x;
  public final int y;
  
  public ItemStackCoord(ItemStack stack, int x, int y) {
	  this.stack = stack;
	  this.x = x;
	  this.y = y;
  }
}
