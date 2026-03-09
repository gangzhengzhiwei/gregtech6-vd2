package gregapi.compat.waila;

import gregapi.data.LH;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;
import gregtech.tileentity.tools.MultiTileEntitySmeltery;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.List;

import static gregapi.data.CS.*;


public class WailaSmeltery extends GTWailaBodyBase{

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP aPlayer, TileEntity aTileEntity, NBTTagCompound aNBT, World aWorld, int aX, int aY, int aZ) {
        super.getNBTData(aPlayer, aTileEntity, aNBT, aWorld, aX, aY, aZ);
        MultiTileEntitySmeltery tSmeltery = (MultiTileEntitySmeltery) aTileEntity;
        UT.NBT.setNumber(aNBT, NBT_TEMPERATURE, tSmeltery.getTemperatureValue(SIDE_INSIDE));
        OreDictMaterialStack.saveList(NBT_MATERIALS, aNBT, tSmeltery.getContent());

        if (ST.valid(tSmeltery.slot(0))) aNBT.setTag("s", ST.save(tSmeltery.slot(0))); //hidden itemstack
        return aNBT;
    }
    @Override
    public List<String> getWailaBody(ItemStack aItemStack, List<String> aCurrentTip, IWailaDataAccessor aAccessor, IWailaConfigHandler aConfig) {
        super.getWailaBody(aItemStack, aCurrentTip, aAccessor, aConfig);
        NBTTagCompound tNBT = aAccessor.getNBTData();
        long tTemperature = tNBT.getLong(NBT_TEMPERATURE);
        List<OreDictMaterialStack> tStackList = OreDictMaterialStack.loadList(NBT_MATERIALS, tNBT);
        MultiTileEntitySmeltery tSmeltery = (MultiTileEntitySmeltery) aAccessor.getTileEntity();

        aCurrentTip.add(LH.format("gt.waila.smeltery.0", tTemperature, tSmeltery.getTemperatureMax(SIDE_INSIDE)));
        StringBuilder tBuilder = new StringBuilder(LH.get("gt.waila.smeltery.1"));
        long tAmount = 0;
        for (OreDictMaterialStack tStack : tStackList) {
            tBuilder.append(LH.format("gt.waila.smeltery.2",GTWailaUtils.DF1.format((double)tStack.mAmount/U) ,tStack.mMaterial.getLocal()));
            tAmount += tStack.mAmount;
        }
        if (!tStackList.isEmpty()) aCurrentTip.add(tBuilder.toString());
        aCurrentTip.add(LH.format("gt.waila.smeltery.3", GTWailaUtils.DF1.format((double)tAmount/U), 16));

        if (tNBT.hasKey("s")) {
            ItemStack tHiddenStack = ST.load(tNBT.getCompoundTag("s"));
            aCurrentTip.add(LH.format("gt.waila.smeltery.4", tHiddenStack.stackSize, tHiddenStack.getDisplayName()));
        }

        MovingObjectPosition tPos = aAccessor.getPosition();
        long tEnvTemperature = WD.envTemp(aAccessor.getWorld(), tPos.blockX, tPos.blockY, tPos.blockZ), tHeatLoss = Math.round((double) (tTemperature - tEnvTemperature) * MultiTileEntitySmeltery.HEAT_LOSS_FACTOR / 175);
        if (tHeatLoss == 0 && tTemperature != tEnvTemperature) {
            if (tTemperature > tEnvTemperature) tHeatLoss = 1;
            else tHeatLoss = -1;
        }
        if (tHeatLoss != 0) aCurrentTip.add(LH.format("gt.waila.smeltery.5", tHeatLoss));
        return aCurrentTip;
    }
}
