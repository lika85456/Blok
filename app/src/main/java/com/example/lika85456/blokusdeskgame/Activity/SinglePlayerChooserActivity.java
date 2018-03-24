package com.example.lika85456.blokusdeskgame.Activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.lika85456.blokusdeskgame.Model.SquareColor;
import com.example.lika85456.blokusdeskgame.R;

public class SinglePlayerChooserActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private static String DEBUG_TAG = "GestureDetector";
    private GestureDetectorCompat gDetector;

    private byte color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_single_player_chooser);

        Button continueButton = findViewById(R.id.singleplayer_chooser_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinue();
            }
        });

        RelativeLayout circle = findViewById(R.id.colored_circle);
        circle.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        gDetector = new GestureDetectorCompat(this, this);
    }

    private void onContinue() {
        Intent intent = new Intent(this, SingleplayerActivity.class);
        intent.putExtra("playerColor", color);

        intent.putExtra("difficulty1", ((SeekBar) findViewById(R.id.seekBar_bot1)).getProgress());
        intent.putExtra("difficulty2", ((SeekBar) findViewById(R.id.seekBar_bot2)).getProgress());
        intent.putExtra("difficulty3", ((SeekBar) findViewById(R.id.seekBar_bot3)).getProgress());
        startActivity(intent);
    }



    private void changeColor(boolean side) {
        //false = left
        //true = right

        final int iColor = SquareColor.getColorFromCode(color);
        if (color < 0)
            color = 3;
        if (color > 3) {
            color = 0;
        }

        if (side)
            color++;
        else
            color--;

        final RelativeLayout circle = findViewById(R.id.colored_circle);
        final int newColor = SquareColor.getColorFromCode(color);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                int c = (int) (iColor + ((newColor - iColor) * progress));
                circle.getBackground().setColorFilter(c, PorterDuff.Mode.MULTIPLY);

            }
        });

        valueAnimator.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.gDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        if (event1.getRawX() - event2.getRawX() > 100 || event1.getRawX() - event2.getRawX() < -100)
            changeColor(event1.getRawX() > event2.getRawX());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        return;
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }



}
