package ic2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnhancedOverlayProvider {
	/**
	 * Determine whether an item provides an enhanced overlay or not.
	 *
	 * @param world The world the item is being used in
	 * @param pos The position the item is being used at
	 * @param side The side of the block the item is being used on
	 * @param player The player using the item, might be <code>null</code>
	 * @param stack The {@link ItemStack} of the item being used
	 *
	 * @return Whether the item provides an enhanced overlay or not
	 */
	boolean providesEnhancedOverlay(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack);
}
