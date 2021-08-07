package ru.artic030.mod02.items;

import java.util.List;

import ic2.api.item.IBoxable;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.artic030.mod02.Mod02;
import ru.artic030.mod02.load.ItemLoader;
import ru.artic030.mod02.utils.IHasModel;

public class ItemSteelWrench extends Item implements IHasModel, IBoxable {
	
	private static final boolean logWrench = ConfigUtil.getBool(MainConfig.get(), "debug/logEmptyWrenchDrops");
	
	public ItemSteelWrench(String name) {
		setMaxStackSize(1);
		setMaxDamage(380);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(IC2.tabIC2);
		ItemLoader.ITEMS.add(this);
	}
	
	@Override
	public boolean canBeStoredInToolbox(ItemStack stack) {
		return true;
	}
	
	public boolean canTakeDamage(ItemStack stack, int amount) {
		return true;
	}
	  
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		ItemStack stack = StackUtil.get(player, hand);
	    if (!canTakeDamage(stack, 1)) {
	    	return EnumActionResult.FAIL;
	    }
	    WrenchResult result = wrenchBlock(world, pos, side, player, canTakeDamage(stack, 10));
	    if (result != WrenchResult.Nothing) {
	    	if (!world.isRemote) {
	        damage(stack, result == WrenchResult.Rotated ? 1 : 10, player);
	        } 
	    	else {
	    		IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.getDefaultVolume());
	      }
	      return world.isRemote ? EnumActionResult.PASS : EnumActionResult.SUCCESS;
	    }
	    return EnumActionResult.FAIL;
	  }
	  
	  public static WrenchResult wrenchBlock(World world, BlockPos pos, EnumFacing side, EntityPlayer player, boolean remove) {
		  IBlockState state = Util.getBlockState(world, pos);
		  Block block = state.getBlock();
		  if ((block instanceof IWrenchable)) {
			  IWrenchable wrenchable = (IWrenchable)block;
			  EnumFacing currentFacing = wrenchable.getFacing(world, pos);
			  EnumFacing newFacing = currentFacing;
			  if (IC2.keyboard.isAltKeyDown(player)) {
				  EnumFacing.Axis axis = side.getAxis();
				  if (((side.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) && (!player.isSneaking())) || ((side.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) && (player.isSneaking()))) {
					  newFacing = newFacing.rotateAround(axis);
				  } else {
			          for (int i = 0; i < 3; i++) {
			        	  newFacing = newFacing.rotateAround(axis);
			          }
				  }
	      } else if (player.isSneaking()) {
	        newFacing = side.getOpposite();
	      } else {
	        newFacing = side;
	      }
	      if ((newFacing != currentFacing) && (wrenchable.setFacing(world, pos, newFacing, player))) {
	    	  return WrenchResult.Rotated;
	      }
	      if ((remove) && (wrenchable.wrenchCanRemove(world, pos, player))) {
	        if (!world.isRemote) {
	          TileEntity te = world.getTileEntity(pos);
	          if (ConfigUtil.getBool(MainConfig.get(), "protection/wrenchLogging")) {
	            String playerName = player.getGameProfile().getName() + "/" + player.getGameProfile().getId();
	            IC2.log.info(LogCategory.PlayerActivity, "Player %s used a wrench to remove the block %s (te %s) at %s.", new Object[] { playerName, state, getTeName(te), Util.formatPosition(world, pos)});
	          }
	          block.onBlockHarvested(world, pos, state, player);
	          if (block.removedByPlayer(state, world, pos, player, true)) {
	        	  block.onBlockDestroyedByPlayer(world, pos, state);
	          } else {
	            return WrenchResult.Nothing;
	          }
	          List<ItemStack> drops = wrenchable.getWrenchDrops(world, pos, state, te, player, 0);
	          if ((drops == null) || (drops.isEmpty())) {
	            if (logWrench) {
	              IC2.log.warn(LogCategory.General, "The block %s (te %s) at %s didn't yield any wrench drops.", new Object[] { state, getTeName(te), Util.formatPosition(world, pos) });
	            }
	          } else {
	        	  for (ItemStack stack : drops) {
	        		  StackUtil.dropAsEntity(world, pos, stack);
	        	  }
	          }
	        }
	        return WrenchResult.Removed;
	      }
	    } else if (block.rotateBlock(world, pos, side)) {
	    	return WrenchResult.Rotated;
	    }
		  return WrenchResult.Nothing;
	  }
	  
	  private static String getTeName(Object te) {
	    return te != null ? te.getClass().getSimpleName().replace("TileEntity", "") : "none";
	  }
	  
	  private static enum WrenchResult {
	    Rotated,  Removed,  Nothing;
		  private WrenchResult() {}
	  }
	  
	  public void damage(ItemStack stack, int damage, EntityPlayer player) {
		  stack.damageItem(damage, player);
	  }

	  public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
	    return (repair != null) && (Util.matchesOD(repair, "ingotSteel"));
	  }
	
	public void registerModels() {	
		Mod02.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
}
