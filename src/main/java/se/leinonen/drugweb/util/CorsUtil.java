package se.leinonen.drugweb.util;

/**
 * Created by leinonen on 2014-07-02.
 */
public class CorsUtil {
    private static final String ALLOW_ORIGIN = "cors.allowOrigin";

    public static String getAllowOrigin(){
        return Settings.getInstance().getString(ALLOW_ORIGIN);
    }
}
