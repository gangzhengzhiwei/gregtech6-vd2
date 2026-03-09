package gregapi.compat.waila;

import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.connectors.MultiTileEntityPipeFluid;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

import static gregapi.data.CS.NBT_TANK;
import static gregapi.data.CS.NBT_TANK_COUNT;

public class WailaPipeFluid extends GTWailaBodyBase{
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP aPlayer, TileEntity aTileEntity, NBTTagCompound aNBT, World aWorld, int aX, int aY, int aZ) {
        super.getNBTData(aPlayer, aTileEntity, aNBT, aWorld, aX, aY, aZ);
        MultiTileEntityPipeFluid tPipe = (MultiTileEntityPipeFluid) aTileEntity;
        if (tPipe.mTanks[0].isEmpty()) return aNBT;
        aNBT.setInteger(NBT_TANK_COUNT, tPipe.mTanks.length);
        for (int i=0;i<tPipe.mTanks.length;i++) {
            tPipe.mTanks[i].writeToNBT(aNBT, NBT_TANK + i);
        }
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(ItemStack aItemStack, List<String> aCurrentTip, IWailaDataAccessor aAccessor, IWailaConfigHandler aConfig) {
        super.getWailaBody(aItemStack, aCurrentTip, aAccessor, aConfig);
        NBTTagCompound tNBT = aAccessor.getNBTData();
        if (!tNBT.hasKey(NBT_TANK_COUNT)) return aCurrentTip;

        int tTankCount = Math.max(1, tNBT.getInteger(NBT_TANK_COUNT));
        StringBuilder tStr = new StringBuilder();
        for (int i=0;i<tTankCount;i++) {
            FluidTankGT tTank = new FluidTankGT(tNBT, NBT_TANK+i, 0);
            if (tTank.isEmpty()) continue;
            if (i != 0) tStr.append(" | ");
            tStr.append(GTWailaUtils.getFluidTankGTTip(tTank));
        }
        aCurrentTip.add(LH.format("gt.waila.pipefluid.0", tStr));
        if (tTankCount != 1) aCurrentTip.add(LH.format("gt.waila.pipefluid.1", tTankCount));
        return aCurrentTip;
    }
}
