package com.example.lika85456.blokusdeskgame;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by lika85456 on 08.03.2018.
 */

public class Utility {
    public static int getColorFromInt(int color) {
        switch (color) {
            case 0:
                return 0xFFFF0000;
            case 1:
                return 0xFF00FF00;
            case 2:
                return 0xFF0000FF;
            case 3:
                return 0xFFFFFF00;
            default:
                return 0xFFFF0000;
        }

    }

    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }


}
