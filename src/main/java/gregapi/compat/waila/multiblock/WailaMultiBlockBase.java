package gregapi.compat.waila.multiblock;

import gregapi.compat.waila.GTWailaBodyBase;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;

import java.util.List;

public class WailaMultiBlockBase extends GTWailaBodyBase {
    protected boolean mStructureOkay;
    @Override
    public List<String> getWailaBody(ItemStack aItemStack, List<String> aCurrentTip, IWailaDataAccessor aAccessor, IWailaConfigHandler aConfig) {
        List<String> tTip = super.getWailaBody(aItemStack, aCurrentTip, aAccessor, aConfig);
        TileEntityBase10MultiBlockBase tBase = (TileEntityBase10MultiBlockBase) aAccessor.getTileEntity();
        mStructureOkay = tBase.mStructureOkay;
        if (!mStructureOkay) tTip.add("§cStructure not Formed");
        return tTip;
    }
}
