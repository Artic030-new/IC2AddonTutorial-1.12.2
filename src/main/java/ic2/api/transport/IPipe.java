package ic2.api.transport;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IPipe {
    TileEntity getTile();

    /**
     * Check if the pipe is connected in the provided facing
     * @param facing the provided facing
     * @return true if there is a connection or false otherwise
     */
    boolean isConnected(EnumFacing facing);

    /**
     * Flip the connection status in the provided facing
     * @param facing the provided facing
     */
    void flipConnection(EnumFacing facing);
}
