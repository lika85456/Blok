package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lika85456.blokusdeskgame.Model.Piece;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Utilities.Initialization.OnOnInitializedListener;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.ZoomView;

public class SingleplayerActivity extends AppCompatActivity {

    private TextView consoleView;
    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);
        grid = findViewById(R.id.grid);
        grid.setMaxZoom(6.f);
        //grid.setMinZoom(0.7f);
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

        grid.setOnInitializedListener(new OnOnInitializedListener(){
            public void onInit()
            {
                //ON GRID INIT
            }
        });

        consoleView = findViewById(R.id.consoleView);

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        for(int i = 0;i< Piece.groups.size();i++)
        {

        }
    }

    /**
     * Sets text in consoleView - <font color='red'>red</font>
     *
     * @param text
     */
    private void setConsoleText(String text) {
        //<font color='red'>red</font>
        consoleView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
    }

    protected void onResume() {
        super.onResume();

    }
}
