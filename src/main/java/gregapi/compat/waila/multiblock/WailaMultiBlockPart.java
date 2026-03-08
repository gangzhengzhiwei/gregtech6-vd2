package gregapi.compat.waila.multiblock;

import gregapi.compat.waila.GTWailaBodyBase;
import gregapi.compat.waila.GTWailaDataProvider;
import gregapi.data.LH;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class WailaMultiBlockPart extends GTWailaBodyBase {
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP aPlayer, TileEntity aTileEntity, NBTTagCompound aNBT, World aWorld, int aX, int aY, int aZ) {
        super.getNBTData(aPlayer, aTileEntity, aNBT, aWorld, aX, aY, aZ);
        MultiTileEntityMultiBlockPart tPart = (MultiTileEntityMultiBlockPart) aTileEntity;
        TileEntityBase10MultiBlockBase tController = (TileEntityBase10MultiBlockBase) tPart.mTarget; //freeze game when use getTarget method, so we need to check valid in another way
        if (tController == null || !tController.mStructureOkay) return aNBT; //another way to check valid
        GTWailaBodyBase tBase = GTWailaDataProvider.getWailaBodyBase(tController);
        if (tBase == null) return aNBT;
        aNBT.setTag("gt.controller", tBase.getNBTData(aPlayer, tController, new NBTTagCompound(), aWorld, aX, aY, aZ));
        int tX = tController.xCoord, tY = tController.yCoord, tZ = tController.zCoord;
        aNBT.setInteger("x", tX);
        aNBT.setInteger("y", tY);
        aNBT.setInteger("z", tZ);
        return aNBT;
    }
    @Override
    public List<String> getWailaBody(ItemStack aItemStack, List<String> aCurrentTip, IWailaDataAccessor aAccessor, IWailaConfigHandler aConfig) {
        super.getWailaBody(aItemStack, aCurrentTip, aAccessor, aConfig);
        NBTTagCompound tNBT = aAccessor.getNBTData();
        if (!tNBT.hasKey("gt.controller")) return aCurrentTip;
        NBTTagCompound tControllerNBT = tNBT.getCompoundTag("gt.controller");
        if (tControllerNBT == null) return aCurrentTip;

        //The mTarget field and getTarget() method of MultiTileEntityMultiBlockPart is not believable on Client side.
        int tX = tNBT.getInteger("x"), tY = tNBT.getInteger("y"), tZ = tNBT.getInteger("z");
        TileEntity tController = aAccessor.getWorld().getTileEntity(tX, tY, tZ);
        if (!(tController instanceof TileEntityBase10MultiBlockBase)) return aCurrentTip;

        aCurrentTip.add(String.format("Belongs to §f%s", LH.get("gt.multitileentity." + ((TileEntityBase10MultiBlockBase) tController).getMultiTileEntityID())));
        GTWailaBodyBase tBase = GTWailaDataProvider.getWailaBodyBase(tController);
        if (tBase == null) return aCurrentTip;
        tBase.getWailaBody(aItemStack, aCurrentTip, new MultiBlockDataAccessor(aAccessor, tController, tControllerNBT), aConfig);//gen a "fake" accessor
        return aCurrentTip;
    }
}
