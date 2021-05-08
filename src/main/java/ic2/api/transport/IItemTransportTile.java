package ic2.api.transport;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IItemTransportTile extends IPipe {
    int putItems(ItemStack itemStack, EnumFacing facing, boolean simulate);

    ItemStack getContents();

    void setContents(ItemStack stack);

    int getMaxStackSizeAllowed();

    int getTransferRate();
}
