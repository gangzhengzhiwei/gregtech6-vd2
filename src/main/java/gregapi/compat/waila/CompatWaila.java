package gregapi.compat.waila;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregapi.compat.CompatBase;
import gregapi.data.CS;
import gregapi.tileentity.base.TileEntityBase01Root;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatWaila extends CompatBase {
    @Override
    public void onLoad(FMLInitializationEvent aEvent) {
        FMLInterModComms.sendMessage(CS.ModIDs.WAILA, "register", "gregapi.compat.waila.CompatWaila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        IWailaDataProvider provider = new GTWailaDataProvider();
        registrar.registerBodyProvider(provider, TileEntityBase01Root.class);
        registrar.registerNBTProvider(provider, TileEntityBase01Root.class);
    }
}
