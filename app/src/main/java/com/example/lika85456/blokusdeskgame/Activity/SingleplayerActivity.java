package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareView;
import com.example.lika85456.blokusdeskgame.Views.ZoomView;

public class SingleplayerActivity extends AppCompatActivity {

    private TextView consoleView;

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
                //grid.zoomTo(zoom,zoomx,zoomy);
            }

            @Override
            public void onZoomEnded(float zoom, float zoomx, float zoomy) {

            }
        };

        grid.setListner(zoomViewListener);

        for (int x = 0; x < 20; x++)
            for (int y = 0; y < 20; y++) {
                grid.add(new SquareView(this, (x / 3 + y * 2) % 4, x, y));

            }

        consoleView = findViewById(R.id.consoleView);

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
