package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.lika85456.blokusdeskgame.GridView;
import com.example.lika85456.blokusdeskgame.OnPinchListener;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.SquareView;

public class SingleplayerActivity extends AppCompatActivity {

    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);
        GridView grid = findViewById(R.id.grid);

        scaleGestureDetector = new ScaleGestureDetector(this, new OnPinchListener(grid));

        grid.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });


        grid.add(new SquareView(this, 1, 5, 19));
    }

    protected void onResume() {
        super.onResume();

    }
}
