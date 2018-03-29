package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lika85456.blokusdeskgame.Model.UIHandler;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareGroupScrollView;
import com.example.lika85456.blokusdeskgame.Views.ZoomView;

public class SingleplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);
        final GridView grid = findViewById(R.id.grid);
        grid.setMaxZoom(6.f);

        ZoomView.ZoomViewListener zoomViewListener = new ZoomView.ZoomViewListener() {
            @Override
            public void onZoomStarted(float zoom, float zoomx, float zoomy) {
            }

            @Override
            public void onZooming(float zoom, float zoomx, float zoomy) {
            }

            @Override
            public void onZoomEnded(float zoom, float zoomx, float zoomy) {
            }
        };


        grid.setListner(zoomViewListener);
        final SquareGroupScrollView scrollView = findViewById(R.id.scrollView);
        TextView consoleView = findViewById(R.id.consoleView);

        UIHandler uiHandler = new UIHandler(grid, scrollView, consoleView);

    }






    protected void onResume() {
        super.onResume();

    }
}
