package com.lika85456.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lika85456.lika85456.blokusdeskgame.R;

import java.util.ArrayList;
import java.util.Random;

public class BackgroundView extends View {
    private static int SQUARE_SIZE = 40;
    private static int POINTS = 20;
    private ArrayList<Point> points;
    private Random random;
    private boolean initialized = false;


    public BackgroundView(Context context) {
        this(context, null);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        initialized = true;
        random = new Random();
        points = new ArrayList<>();
        for (int i = 0; i < POINTS; i++) {
            points.add(new Point(random.nextInt(this.getWidth()), -this.getHeight() + random.nextInt(this.getHeight()), (float) (random.nextFloat() * 0.01) + 0.01f, random.nextFloat() * 2 + 1f, (byte) random.nextInt(4)));
        }
    }

    public void onDraw(Canvas canvas) {
        if (!initialized)
            init();


        for (Point point : points) {
            point.move();
            point.draw(canvas);
        }
    }

    private class Point {
        public Integer x;
        public Integer y;
        public Float rotation;
        public Float rotationSpeed;
        public Float velocity;
        public Byte color;

        public Point(Integer x, Integer y, Float rotationSpeed, Float velocity, Byte color) {
            this.x = x;
            this.y = y;
            this.rotationSpeed = rotationSpeed;
            this.velocity = velocity;
            this.rotation = 0f;
            this.color = color;
        }

        public void move() {
            this.y += velocity.intValue();
            this.rotation += rotationSpeed;
            if (y > getHeight()) {
                y = -random.nextInt(getHeight());
                x = random.nextInt(getWidth());
            }
        }


        public void draw(Canvas canvas) {
            int fx = x;
            int fy = y;
            float frotation = rotation;
            //for(int i =0;i<10;i++)
            //{
            Drawable d = getResources().getDrawable(R.drawable.block);
            d.setColorFilter(SquareColor.getColorFromCode(color), PorterDuff.Mode.MULTIPLY);
            d.mutate();
            d.setBounds(0, 0, SQUARE_SIZE, SQUARE_SIZE);
            canvas.translate(x + SQUARE_SIZE / 2, y + SQUARE_SIZE / 2);
            canvas.rotate(rotation * 360, SQUARE_SIZE / 2, SQUARE_SIZE / 2);
            d.draw(canvas);
            canvas.rotate(-rotation * 360, SQUARE_SIZE / 2, SQUARE_SIZE / 2);
            canvas.translate(-x - SQUARE_SIZE / 2, -y - SQUARE_SIZE / 2);
            //for(int iii=0;iii<20;iii++)
            //move();
            //}
            x = fx;
            y = fy;
            this.rotation = frotation;
            invalidate();
        }
    }
}
