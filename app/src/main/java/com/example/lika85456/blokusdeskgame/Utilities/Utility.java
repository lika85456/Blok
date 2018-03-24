package com.example.lika85456.blokusdeskgame.Utilities;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by lika85456 on 08.03.2018.
 */

public class Utility {


    public static int convertDpToPixels(float dp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }


}
