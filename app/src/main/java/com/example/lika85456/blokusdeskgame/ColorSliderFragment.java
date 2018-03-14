package com.example.lika85456.blokusdeskgame;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lika85456 on 09.03.2018.
 */

public class ColorSliderFragment extends Fragment {

    private int position = 0;

    public void position(int position) {
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.singleplayer_chooser_color_fragment, container, false);
        Drawable background = rootView.findViewById(R.id.colorFragment).getBackground();

        switch (position) {
            /**
             * 0 - RED
             * 1 - GREEN
             * 2 - BLUE
             * 3 - YELLOw
             */
            case 0:
                background.setColorFilter(0xffff0000, PorterDuff.Mode.MULTIPLY);
                break;
            case 1:
                background.setColorFilter(0xff00FF00, PorterDuff.Mode.MULTIPLY);
                break;
            case 2:
                background.setColorFilter(0xff0000FF, PorterDuff.Mode.MULTIPLY);
                break;
            case 3:
                background.setColorFilter(0xffffFF00, PorterDuff.Mode.MULTIPLY);
                break;
        }

        return rootView;
    }
}