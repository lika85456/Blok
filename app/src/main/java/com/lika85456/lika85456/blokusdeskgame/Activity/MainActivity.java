package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Utilities.SquareColor;

public class MainActivity extends AppCompatActivity {

    private int index = 0;
    private ImageView block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_main);

        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);
        Button multiPlayerButton = findViewById(R.id.multiPlayerButton);
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSinglePlayer();
            }
        });
        multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMultiPlayer();
            }
        });

        block = findViewById(R.id.blockView);
        animation(block, SquareColor.getColorFromCode((byte) index), SquareColor.getColorFromCode(index + 1 >= 3 ? (byte) 0 : (byte) (index + 1)));
    }

    public void onAnimationEnd() {
        index = (index + 1) % 4;
        animation(block, SquareColor.getColorFromCode((byte) index), SquareColor.getColorFromCode(index + 1 >= 3 ? (byte) 0 : (byte) (index + 1)));
    }

    public void animation(final ImageView block, int firstColor, final int lastColor) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), firstColor, lastColor);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                block.getDrawable().clearColorFilter();
                block.getDrawable().setColorFilter((int) animation.getAnimatedValue() + 0x11000000, PorterDuff.Mode.MULTIPLY);
                if ((int) (animation.getAnimatedValue()) == lastColor)
                    onAnimationEnd();
            }
        });

        valueAnimator.start();
    }

    private void onSinglePlayer() {
        Intent intent = new Intent(this, SinglePlayerChooserActivity.class);
        startActivity(intent);
    }

    private void onMultiPlayer() {
        //Intent intent = new Intent(this,M.class);
        //startActivity(intent);
    }


}
