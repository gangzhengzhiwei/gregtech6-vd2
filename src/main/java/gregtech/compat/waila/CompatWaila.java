package gregtech.compat.waila;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregapi.api.Abstract_Mod;
import gregapi.code.ModData;
import gregapi.compat.CompatMods;
import gregapi.data.CS;
import gregapi.tileentity.base.TileEntityBase01Root;
import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatWaila extends CompatMods {
    public CompatWaila(ModData aMod, Abstract_Mod aGTMod) {
        super(aMod, aGTMod);
    }

    @Override
    public void onPreLoad(FMLPreInitializationEvent aEvent) {
        FMLInterModComms.sendMessage(CS.ModIDs.WAILA, "register", "gregtech.compat.waila.CompatWaila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new GTWailaDataProvider(), TileEntityBase01Root.class);
        registrar.registerNBTProvider(new GTWailaDataProvider(), TileEntityBase01Root.class);
    }
}
