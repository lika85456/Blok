package com.lika85456.lika85456.blokusdeskgame.Model;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Piece;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;
import com.lika85456.lika85456.blokusdeskgame.Listeners.UIListener;
import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Utilities.SquareColor;
import com.lika85456.lika85456.blokusdeskgame.Utilities.Utility;
import com.lika85456.lika85456.blokusdeskgame.Views.GridView;
import com.lika85456.lika85456.blokusdeskgame.Views.GridViewMoveListener;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareGroup;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareGroupScrollView;
import com.lika85456.lika85456.blokusdeskgame.Views.ZoomView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class UI implements UIListener {

    public static final byte CONSOLE_STATE = 0;
    public static final byte CONFIRM_STATE = 1;
    public final GridView gridView;
    public byte state = CONSOLE_STATE;
    public SquareGroupScrollView scrollView;

    public TextView consoleView;
    public Button confirmButton;
    public LinearLayout consoleContainer;

    private Piece selectedPiece;

    public Player user;
    private boolean canConfirm = false;
    private boolean userTurn;


    public UI(GridView gridViewv, final SquareGroupScrollView scrollView, LinearLayout consoleContainer, Player user) {
        this.scrollView = scrollView;
        scrollView.requestLayout();
        this.user = user;
        this.gridView = gridViewv;

        this.consoleContainer = consoleContainer;

        this.consoleView = consoleContainer.findViewById(R.id.consoleView);
        this.confirmButton = consoleContainer.findViewById(R.id.turn_confirm_button);

        gridView.setMaxZoom(6.f);

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
        gridView.setListner(zoomViewListener);


        ArrayList<Piece> pieces = Piece.getAllPieces(user.color);
        for (int i = 0; i < pieces.size(); i++) {
            scrollView.add(pieces.get(i));
        }




        /** Confirm button onClickListener **/
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirm();
            }
        });

        this.gridView.setOnMoveListener(new GridViewMoveListener() {
            @Override
            public void onSelectedSquareGroupMove(int x, int y) {
                if (isValid(selectedPiece, x, y)) {
                    setConfirmButtonColor(0xFF3FF931);
                    canConfirm = true;
                }
                else {
                    setConfirmButtonColor(Color.RED);
                    canConfirm = false;
                }
            }
        });



        /***
         * called after selecting is done
         * @param squareGroup
         */
        scrollView.setSquareGroupOnClickListener(new View.OnClickListener() {
            private long lastTimeClick;


            public void onClick(final View view) {
                if (userTurn)
                    setConfirmState();



                final int width = view.getWidth();
                final int height = view.getHeight();

                final boolean doubleClick;
                doubleClick = System.currentTimeMillis() - lastTimeClick < 400;
                ValueAnimator singleClickAnimator = ValueAnimator.ofFloat(0f, (float) (Math.PI / 2));
                singleClickAnimator.setInterpolator(new LinearInterpolator());

                singleClickAnimator.setDuration(150);
                singleClickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        float multiplier = ((float) Math.sin(progress)) * 0.35f + 1;

                        ((SquareGroup) view).setSize(multiplier);

                        if (progress >= Math.PI / 2) {
                            lastTimeClick = 0;
                            selectedPiece = ((SquareGroup) view).getPiece();
                            if (doubleClick) {
                                //If its double click - roatte the piece (and call listener?)

                                ((SquareGroup) view).rotate();
                                //Call the event
                                onPieceSelected(selectedPiece);
                                //Render it
                                gridView.selected(selectedPiece);
                                //Rerender squareGroupView
                                ((SquareGroup) view).fromPiece(selectedPiece);
                                //Invalidate to be sure its rendered
                                scrollView.invalidate();
                                view.invalidate();
                                /*int index = scrollView.getIndexOfElement(selectedPiece);
                                scrollView.removeElementAtIndex(index);
                                selectedPiece.rotateBy90();
                                scrollView.addAtIndex(selectedPiece,index);
                                */


                            }
                        }
                        //view.requestLayout();
                    }
                });

                singleClickAnimator.start();

                lastTimeClick = System.currentTimeMillis();
                selectedPiece = ((SquareGroup) view).getPiece();
                onPieceSelected(selectedPiece);
                gridView.selected(selectedPiece);
                //TODO add moer events?
            }
        });


    }

    public void onMoving(Player player) {

        setConsoleState();
        changeHrColor(SquareColor.getColorFromCode(player.color));
        if (player == user) {
            userTurn = true;
            if (selectedPiece == null)
                setConsoleText("Your turn");
            else
                setConfirmState();
        }
        else {
            gridView.zoomOnCenter();
            setConsoleText(player.getName() + " turn");
            userTurn = false;
        }

    }

    public void onMove(Player player, Move move) {
        //TODO add some animation method with fromBoard
        gridView.fromBoard(move.getBoard());


    }

    public void changeHrColor(int color) {
        Drawable background = gridView.findViewById(R.id.hr).getBackground();
        background.setColorFilter(0, PorterDuff.Mode.CLEAR);
        background.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    /***
     * Makes CONFIRM button apper
     */
    private void setConfirmState() {
        if (state == CONFIRM_STATE)
            return;
        consoleView.setVisibility(GONE);
        confirmButton.setVisibility(VISIBLE);
        state = CONFIRM_STATE;
    }

    /***
     * Makes ConsoleView appear
     */
    private void setConsoleState() {
        if (state == CONSOLE_STATE)
            return;
        consoleView.setVisibility(VISIBLE);
        confirmButton.setVisibility(GONE);
        state = CONSOLE_STATE;
    }

    public byte getState() {
        return state;
    }

    /***
     * Called when user confirms placing piece
     */
    public void onConfirm() {
        if (canConfirm) {
            onMoveConfirm(gridView.selectedX, gridView.selectedY);
            this.setConsoleState();
        }
    }

    public void removeSquareGroupFromList(Piece piece) {
        scrollView.removeElementAtIndex(scrollView.getIndexOfElement(piece));
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

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
        gridView.selected(null);
    }


    public void setConfirmButtonColor(int color) {
        GradientDrawable drawable = (GradientDrawable) confirmButton.getBackground();
        drawable.setStroke(Utility.convertDpToPixels(2.f, confirmButton.getContext()), color);
        confirmButton.setTextColor(color);
        drawable.mutate();
    }

    @Override
    public void onPieceSelected(Piece piece) {

    }

    @Override
    public boolean isValid(Piece piece, int x, int y) {
        return false;
    }

    @Override
    public void onMoveConfirm(int x, int y) {

    }

    public void onEnd() {
        setConsoleState();
        setConsoleText("Game end TODO:Show ending stuff");
    }
}