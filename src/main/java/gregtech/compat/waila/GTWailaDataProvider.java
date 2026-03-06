package gregtech.compat.waila;

import gregapi.data.CS;
import gregtech.tileentity.multiblocks.MultiTileEntityCrucible;
import gregtech.tileentity.tools.MultiTileEntitySmeltery;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GTWailaDataProvider implements IWailaDataProvider {
    public static HashMap<Class, GTWailaBodyBase> mTileEntityMap = new HashMap<>();
    public static HashSet<Class> mTileEntityList = new HashSet<>();
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity tileEntity = accessor.getTileEntity();
        if (!mTileEntityList.contains(tileEntity.getClass())) return currenttip;
        return mTileEntityMap.get(tileEntity.getClass()).getWailaBody(itemStack, currenttip, accessor, config);
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        if (!mTileEntityList.contains(te.getClass())) return tag;
        return mTileEntityMap.get(te.getClass()).getNBTData(player, te, tag, world, x, y, z);
    }
    public static void registerBody(Class aTileEntity, GTWailaBodyBase aBase) {
        mTileEntityMap.put(aTileEntity, aBase);
        mTileEntityList.add(aTileEntity);
    }
    static {
        try{
            registerBody(MultiTileEntitySmeltery.class, new WailaSmeltery());
            registerBody(MultiTileEntityCrucible.class, new WailaMultiBlockCrucible());
        } catch (Exception e) {
            e.printStackTrace();
            CS.ERR.println(e);
            throw e;
        }
    }
}
