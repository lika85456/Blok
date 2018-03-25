package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lika85456.blokusdeskgame.Model.Piece;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Utilities.Initialization.OnOnInitializedListener;
import com.example.lika85456.blokusdeskgame.Views.CircularSelectorElement;
import com.example.lika85456.blokusdeskgame.Views.CircularSelectorView;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareGroup;
import com.example.lika85456.blokusdeskgame.Views.SquareGroupScrollView;
import com.example.lika85456.blokusdeskgame.Views.ZoomView;

public class SingleplayerActivity extends AppCompatActivity {

    private final SingleplayerActivity tis = this;
    private TextView consoleView;
    private GridView grid;
    private CircularSelectorView selectorView;

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
                SquareGroupScrollView scrollView = findViewById(R.id.scrollView);
                scrollView.activity = tis;
                for (int i = 0; i < Piece.groups.size(); i++) {
                    scrollView.add(Piece.groups.get(i));
                }
            }
        });

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

    /***
     * called after selecting is done
     * @param squareGroup
     */
    public void onSquareGroupSelected(SquareGroup squareGroup) {

    }

    /***
     * when user selects the piece he want to place down
     * @param squareGroup
     */
    public void onSquareGroupTouch(SquareGroup squareGroup) {
        selectorView = new CircularSelectorView(this);
        selectorView.addElement(new CircularSelectorElement(this, 0, 0, squareGroup.piece));
        SquareGroup temp1 = new SquareGroup(squareGroup);
        temp1.piece.rotateBy90();
        selectorView.addElement(new CircularSelectorElement(this, 0, 0, temp1.piece));
        SquareGroup temp2 = new SquareGroup(temp1);
        temp2.piece.rotateBy90();
        selectorView.addElement(new CircularSelectorElement(this, 0, 0, temp2.piece));
        SquareGroup temp3 = new SquareGroup(temp2);
        temp3.piece.rotateBy90();
        selectorView.addElement(new CircularSelectorElement(this, 0, 0, temp3.piece));

        FrameLayout rootLayout = findViewById(android.R.id.content);
        //rootLayout.removeViewAt(rootLayout.getChildCount()-1);
        rootLayout.addView(selectorView);
    }


    protected void onResume() {
        super.onResume();

    }
}
