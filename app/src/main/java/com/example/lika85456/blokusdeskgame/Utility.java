package com.example.lika85456.blokusdeskgame;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lika85456 on 08.03.2018.
 */

public class Utility {
    public static void hideTopBar(AppCompatActivity activity) {
        //Remove title bar
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


}
