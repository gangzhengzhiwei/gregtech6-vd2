package gregapi.compat.waila;

import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.tank.TileEntityBase08Barrel;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

import static gregapi.data.CS.*;

public class WailaBarrel extends GTWailaBodyBase{
    private static final FluidTankGT tTank = new FluidTankGT();
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP aPlayer, TileEntity aTileEntity, NBTTagCompound aNBT, World aWorld, int aX, int aY, int aZ) {
        super.getNBTData(aPlayer, aTileEntity, aNBT, aWorld, aX, aY, aZ);
        TileEntityBase08Barrel tBarrel = (TileEntityBase08Barrel) aTileEntity;
        if (tBarrel.mMode != 0) aNBT.setByte(NBT_MODE, tBarrel.mMode);
        tBarrel.mTank.writeToNBT(aNBT, NBT_TANK);
        return aNBT;
    }

    @Override
    public List<String> getWailaBody(ItemStack aItemStack, List<String> aCurrentTip, IWailaDataAccessor aAccessor, IWailaConfigHandler aConfig) {
        super.getWailaBody(aItemStack, aCurrentTip, aAccessor, aConfig);
        NBTTagCompound tNBT = aAccessor.getNBTData();
        byte mMode = tNBT.getByte(NBT_MODE);
        tTank.readFromNBT(aAccessor.getNBTData(), NBT_TANK);
        if (!tTank.isEmpty()) {
            aCurrentTip.add(LH.format("gt.waila.barrel.0", tTank.amount(), FL.name(tTank, T)));
        }
        aCurrentTip.add(mMode == 0 ? LH.get("gt.waila.barrel.1"):LH.get("gt.waila.barrel.2"));
        return aCurrentTip;
    }
}
