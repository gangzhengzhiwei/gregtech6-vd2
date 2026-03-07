package gregapi.compat.waila;

import java.text.DecimalFormat;

public class GTWailaUtils {
    public static DecimalFormat DF1 = new DecimalFormat("#.###"); // 123.4567 -> 123.456, 123.0000 -> 123  I don't know how to give it a name.
    public static String NBT_WAILA_ENABLED = "gt.waila.enabled";
}
