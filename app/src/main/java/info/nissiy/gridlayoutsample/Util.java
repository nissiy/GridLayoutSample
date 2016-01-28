package info.nissiy.gridlayoutsample;

import android.content.Context;
import android.content.res.Configuration;

public class Util {

    private Util() {
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
