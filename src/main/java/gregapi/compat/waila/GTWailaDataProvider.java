package gregapi.compat.waila;

import gregapi.compat.waila.multiblock.WailaMultiBlockCrucible;
import gregapi.compat.waila.multiblock.WailaMultiBlockPart;
import gregapi.data.CS;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregtech.tileentity.multiblocks.MultiTileEntityCrucible;
import gregtech.tileentity.tanks.MultiTileEntityBarrelMetal;
import gregtech.tileentity.tanks.MultiTileEntityBarrelPlastic;
import gregtech.tileentity.tanks.MultiTileEntityBarrelWood;
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

import static gregapi.compat.waila.GTWailaUtils.NBT_WAILA_ENABLED;
import static gregapi.data.CS.T;

public class GTWailaDataProvider implements IWailaDataProvider {
    public static HashMap<Class<? extends TileEntity>, GTWailaBodyBase> mTileEntityMap = new HashMap<>();
    public static HashSet<Class<? extends TileEntity>> mTileEntityList = new HashSet<>();
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
        if (!accessor.getNBTData().getBoolean(NBT_WAILA_ENABLED)) return currenttip; //Waila compat is not enabled on server
        GTWailaBodyBase tBase = getWailaBodyBase(accessor.getTileEntity());
        if (tBase == null) return currenttip;
        tBase.getWailaBody(itemStack, currenttip, accessor, config);
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        GTWailaBodyBase tBase = getWailaBodyBase(te);
        if (tBase == null) return tag;
        tBase.getNBTData(player, te, tag, world, x, y, z);
        tag.setBoolean(NBT_WAILA_ENABLED, T); //Server enabled waila compat
        return tag;
    }
    @SafeVarargs
    public static void registerBody(Class<? extends GTWailaBodyBase> aBase, Class<? extends TileEntity>... aTileEntities) {
        try{
            GTWailaBodyBase tBaseInstance = aBase.newInstance();
            for (Class<? extends TileEntity> tTileEntity : aTileEntities) {
                mTileEntityList.add(tTileEntity);
                mTileEntityMap.put(tTileEntity, tBaseInstance);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            CS.ERR.println(e);
        }
    }
    public static GTWailaBodyBase getWailaBodyBase(TileEntity aTileEntity) {
        if (!mTileEntityList.contains(aTileEntity.getClass())) return null;
        return mTileEntityMap.get(aTileEntity.getClass());
    }
    static {
        try{
            registerBody(WailaSmeltery.class, MultiTileEntitySmeltery.class);
            registerBody(WailaBarrel.class, MultiTileEntityBarrelPlastic.class, MultiTileEntityBarrelMetal.class, MultiTileEntityBarrelWood.class);

            registerBody(WailaMultiBlockPart.class, MultiTileEntityMultiBlockPart.class);
            registerBody(WailaMultiBlockCrucible.class, MultiTileEntityCrucible.class);
        } catch (Exception e) {
            e.printStackTrace();
            CS.ERR.println(e);
            throw e;
        }
    }
}
