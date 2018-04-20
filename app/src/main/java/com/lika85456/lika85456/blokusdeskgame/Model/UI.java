package com.lika85456.lika85456.blokusdeskgame.Model;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.text.Html;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Piece;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;
import com.lika85456.lika85456.blokusdeskgame.Listeners.UIListener;
import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Utilities.SquareColor;
import com.lika85456.lika85456.blokusdeskgame.Views.GridView;
import com.lika85456.lika85456.blokusdeskgame.Views.GridViewEventListener;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareGroup;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareGroupScrollView;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareView;
import com.lika85456.lika85456.blokusdeskgame.Views.ZoomView;

import java.util.ArrayList;
import java.util.Collections;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class UI implements UIListener {

    public static final byte CONSOLE_STATE = 0;
    public static final byte CONFIRM_STATE = 1;
    public boolean gameState = true; //if false  game is over
    public final GridView gridView;
    public Activity activity;
    public byte state = CONSOLE_STATE;
    public SquareGroupScrollView scrollView;

    public TextView consoleView;
    public Button confirmButton;
    public LinearLayout consoleContainer;

    private Piece selectedPiece;

    public Player user;
    private boolean canConfirm = false;
    private boolean userTurn;

    private TextView[] scores;

    public UI(Activity activity, final Player user) {
        gridView = activity.findViewById(R.id.grid);
        scrollView = activity.findViewById(R.id.scrollView);
        consoleContainer = activity.findViewById(R.id.console_container);
        this.activity = activity;
        scrollView.requestLayout();
        this.user = user;

        this.consoleView = consoleContainer.findViewById(R.id.consoleView);
        this.confirmButton = consoleContainer.findViewById(R.id.turn_confirm_button);

        gridView.setMaxZoom(6.f);

        scores = new TextView[4];
        scores[0] = activity.findViewById(R.id.score1);
        scores[1] = activity.findViewById(R.id.score2);
        scores[2] = activity.findViewById(R.id.score3);
        scores[3] = activity.findViewById(R.id.score4);

        for (int i = 0; i < 4; i++) {
            scores[i].setTextColor(SquareColor.getColorFromCode((byte) i));
            ((RelativeLayout) scores[i].getParent()).getBackground().setColorFilter(0xFF333333, PorterDuff.Mode.MULTIPLY);
            ((RelativeLayout) scores[i].getParent()).getBackground().mutate();
        }

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
        Collections.reverse(pieces);
        for (int i = 0; i < pieces.size(); i++) {
            scrollView.add(pieces.get(i));
        }


        /** Confirm button onClickListener **/
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirm();
                Point startPoint = gridView.board.getStartingPoint(user.color);
                SquareView squareView = gridView.get(startPoint.x, startPoint.y);
                squareView.setBackgroundResource(R.drawable.block);
                //squareView.getBackground().setColorFilter();
            }
        });

        this.gridView.setOnMoveListener(new GridViewEventListener() {
            @Override
            public void onSelectedPieceMove(int x, int y) {

                if (isValid(selectedPiece, x, y)) {
                    setConfirmButtonColor(0xFF3FF931);
                    canConfirm = true;
                    if (gridView.board.isStartMove(user.color)) {
                        Point startPoint = gridView.board.getStartingPoint(user.color);
                        SquareView squareView = gridView.get(startPoint.x, startPoint.y);
                        squareView.setBackgroundResource(R.drawable.block);
                        squareView.getBackground().mutate();
                    }
                } else {
                    setConfirmButtonColor(Color.RED);
                    canConfirm = false;
                    if (gridView.board.isStartMove(user.color)) {
                        Point startPoint = gridView.board.getStartingPoint(user.color);
                        SquareView squareView = gridView.get(startPoint.x, startPoint.y);
                        squareView.color = -1;
                        squareView.setBackgroundResource(R.drawable.block_seed);
                        squareView.getBackground().setColorFilter(SquareColor.getColorFromCode(user.color), PorterDuff.Mode.MULTIPLY);
                        squareView.getBackground().mutate();
                    }
                }
            }

            @Override
            public void onSelectedPieceRotate(Piece piece) {
                for (int i = 0; i < scrollView.getChildCount(); i++) {
                    if (((SquareGroup) scrollView.getChildAt(i)).getPiece().index == piece.index) {
                        ((SquareGroup) scrollView.getChildAt(i)).fromPiece(piece);
                    }
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
                        }
                        //view.requestLayout();
                    }
                });

                singleClickAnimator.start();

                lastTimeClick = System.currentTimeMillis();
                selectedPiece = ((SquareGroup) view).getPiece();
                if (doubleClick) {
                    ((SquareGroup) view).rotate();
                    gridView.selected(selectedPiece);
                    ((SquareGroup) view).fromPiece(selectedPiece);
                    scrollView.invalidate();


                }
                onPieceSelected(selectedPiece);
                gridView.selected(selectedPiece);
                //TODO add moer events?
            }
        });

        Point startPoint = gridView.board.getStartingPoint(user.color);
        SquareView squareView = gridView.get(startPoint.x, startPoint.y);
        squareView.setBackgroundResource(R.drawable.block_seed);
        squareView.getBackground().setColorFilter(SquareColor.getColorFromCode(user.color), PorterDuff.Mode.MULTIPLY);
        squareView.getBackground().mutate();
    }

    public void updateScores() {
        for (int i = 0; i < 4; i++) {
            scores[i].setText("" + gridView.board.getColorScore((byte) i));
        }
    }


    public void onMoving(Player player) {

        setConsoleState();
        changeHrColor(SquareColor.getColorFromCode(player.color));
        if (player == user) {
            if (!gameState) return;
            userTurn = true;
            if (selectedPiece == null)
                setConsoleText("Your turn");
            else
                setConfirmState();
        }
        else {
            if (selectedPiece == null)
                gridView.zoomOnCenter();
            if (!gameState) return;
            setConsoleText(player.getName() + " turn");
            userTurn = false;
        }

    }

    public void onMove(Player player, Move move) {
        //TODO add some animation method with fromBoard
        if (move != null) {
            gridView.fromBoard(move.getBoard());
            updateScores();
        }



    }

    public void changeHrColor(int color) {
        activity.findViewById(R.id.hr).setBackgroundColor(color);
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
        scrollView.removeElementWithIndex(piece.index);
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
        confirmButton.setTextColor(color);
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
        gameState = false;
    }
}
