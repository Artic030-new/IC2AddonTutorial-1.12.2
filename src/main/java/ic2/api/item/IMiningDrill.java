package ic2.api.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;

/**
 * Mark an {@link Item} as usable within a Miner
 *
 * <p>Note that the ability of a drill to start mining a block is determined via
 * {@link ForgeHooks#canToolHarvestBlock(IBlockAccess, BlockPos, ItemStack)} or
 * {@link Item#canHarvestBlock(IBlockState, ItemStack)} returning <code>true</code>
 * for the drill stack on the block trying to be mined.
 *
 * @author Chocohead
 */
public interface IMiningDrill {
	/**
	 * The power use per tick whilst mining the block with the given {@link IBlockState}
	 *
	 * @param stack The drill stack being "used" to mine
	 * @param world The world the block being broken is in
	 * @param pos The position the block being broken is at
	 * @param state The block state of the block being broken
	 *
	 * @return The power use whilst breaking the block with the given state
	 */
	int energyUse(ItemStack stack, World world, BlockPos pos, IBlockState state);

	/**
	 * The time needed to mine the block with the given {@link IBlockState}
	 *
	 * @param stack The drill stack being "used" to mine
	 * @param world The world the block being broken is in
	 * @param pos The position the block being broken is at
	 * @param state The block state of the block being broken
	 *
	 * @return The time needed to break the block with the given state
	 */
	int breakTime(ItemStack stack, World world, BlockPos pos, IBlockState state);

	/**
	 * Whether the block at the given position with the given state can be broken
	 *
	 * @param stack The drill stack being "used" to mine
	 * @param world The world the block about to be broken is in
	 * @param pos The position the block about to be broken is at
	 * @param state The block state of the block about to be broken
	 *
	 * @return Whether the drill is able to break the block with the given state
	 */
	boolean breakBlock(ItemStack stack, World world, BlockPos pos, IBlockState state);

	/**
	 * Utility method for use in {@link #breakBlock(ItemStack, World, BlockPos, IBlockState)}
	 * to return true if the given amount of power can be used from the given drill stack
	 *
	 * @param drill The drill stack to use the power
	 * @param amount The amount of power to try consume
	 *
	 * @return Whether the drill had enough energy stored
	 */
	default boolean tryUsePower(ItemStack drill, double amount) {
		return ElectricItem.manager.use(drill, amount, null);
	}
}