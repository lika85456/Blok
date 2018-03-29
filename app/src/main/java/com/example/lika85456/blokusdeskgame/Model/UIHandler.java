package com.example.lika85456.blokusdeskgame.Model;

import android.animation.ValueAnimator;
import android.text.Html;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.example.lika85456.blokusdeskgame.Game.Piece;
import com.example.lika85456.blokusdeskgame.Listeners.UIListener;
import com.example.lika85456.blokusdeskgame.Utilities.Initialization.OnOnInitializedListener;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareGroup;
import com.example.lika85456.blokusdeskgame.Views.SquareGroupScrollView;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class UIHandler {

    public GridView gridView;
    public SquareGroupScrollView scrollView;
    public TextView consoleView;

    private Piece selectedPiece;
    private UIListener uiListener;

    public UIHandler(GridView gridView, SquareGroupScrollView scrollView, TextView consoleView) {
        this.scrollView = scrollView;
        this.gridView = gridView;
        this.consoleView = consoleView;

        final SquareGroupScrollView temp = scrollView;
        //SquareGroups initialization
        gridView.setOnInitializedListener(new OnOnInitializedListener() {
            public void onInit() {

                for (int i = 0; i < Piece.groups.size(); i++) {
                    temp.add(Piece.groups.get(i));
                }
            }
        });

        /***
         * called after selecting is done
         * @param squareGroup
         */
        scrollView.onClickListener = new View.OnClickListener() {
            private long lastTimeClick;

            @Override
            public void onClick(final View view) {

                final boolean doubleClick;
                doubleClick = System.currentTimeMillis() - lastTimeClick < 400;
                ValueAnimator singleClickAnimator = ValueAnimator.ofFloat(0f, (float) (Math.PI / 2));
                singleClickAnimator.setInterpolator(new LinearInterpolator());

                singleClickAnimator.setDuration(150);
                singleClickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        ((SquareGroup) (view)).setSize(((float) Math.sin(progress)) * 0.35f + 1);

                        if (progress == Math.PI / 2) {
                            ((SquareGroup) (view)).setSize(1);
                            selectedPiece = new Piece(((SquareGroup) view).piece);
                            if (doubleClick) {
                                //If its double click - roatte the piece (and call listener?)
                                selectedPiece.rotateBy90();
                            }
                            uiListener.onPieceSelected(selectedPiece);
                        }
                    }
                });

                singleClickAnimator.start();

                lastTimeClick = System.currentTimeMillis();
                selectedPiece = new Piece(((SquareGroup) view).piece);
                uiListener.onPieceSelected(selectedPiece);
                //TODO add moer events?
            }
        };


    }

    public void setUiListener(UIListener l) {
        this.uiListener = l;
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


}
