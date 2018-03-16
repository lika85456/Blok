package com.example.lika85456.blokusdeskgame;

import android.view.ScaleGestureDetector;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

    float startingSpan;
    float endSpan;
    float startFocusX;
    float startFocusY;
    private Resizable view;

    public OnPinchListener(Resizable view) {
        this.view = view;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        startingSpan = detector.getCurrentSpan();
        startFocusX = detector.getFocusX();
        startFocusY = detector.getFocusY();
        return true;
    }


    public boolean onScale(ScaleGestureDetector detector) {
        view.scale(detector.getCurrentSpan() / startingSpan, startFocusX, startFocusY);
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
    }
}