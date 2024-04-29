package com.lika85456.blokus.game.Model;

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

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import com.lika85456.blokus.R;
import com.lika85456.blokus.game.Activity.SingleplayerActivity;
import com.lika85456.blokus.game.Game.Game;
import com.lika85456.blokus.game.Game.Move;
import com.lika85456.blokus.game.Game.Piece;
import com.lika85456.blokus.game.Game.Player;
import com.lika85456.blokus.game.Listeners.UIListener;
import com.lika85456.blokus.game.Views.GridView;
import com.lika85456.blokus.game.Views.GridViewEventListener;
import com.lika85456.blokus.game.Views.SquareColor;
import com.lika85456.blokus.game.Views.SquareGroup;
import com.lika85456.blokus.game.Views.SquareGroupScrollView;
import com.lika85456.blokus.game.Views.SquareView;
import com.lika85456.blokus.game.Views.ZoomView;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class UI implements UIListener {

    private static final byte CONSOLE_STATE = 0;
    private static final byte CONFIRM_STATE = 1;
    private final GridView gridView;
    protected byte colorTurn;
    private boolean gameState = true; //if false  game is over
    private Activity activity;
    private byte state = CONSOLE_STATE;
    private SquareGroupScrollView scrollView;
    private TextView consoleView;
    private LinearLayout consoleLayout;
    private Button confirmButton;
    private Button flipButton;
    private Button rotateButton;
    private LinearLayout confirmContainer;
    private Game game;
    private Piece selectedPiece;

    private boolean canConfirm = false;
    private LinearLayout consoleContainer;
    private TextView[] scores;
    private boolean[] playerC;

    public UI(final Activity activity, boolean[] player, Game game) {
        consoleLayout = activity.findViewById(R.id.consoleLayout);
        gridView = activity.findViewById(R.id.grid);
        scrollView = activity.findViewById(R.id.scrollView);
        scrollView.color = colorTurn;
        consoleContainer = activity.findViewById(R.id.console_container);
        confirmContainer = activity.findViewById(R.id.confirmLayout);
        consoleView = consoleContainer.findViewById(R.id.consoleView);
        confirmButton = consoleContainer.findViewById(R.id.turn_confirm_button);
        flipButton = activity.findViewById(R.id.flipButton);
        rotateButton = activity.findViewById(R.id.rotateButton);
        this.game = game;
        this.activity = activity;
        playerC = player;

        initializeScrollView(game.getCurrentPlayer().getPieces());
        initializeScores();
        gridView.setMaxZoom(6.f);

        flipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPiece != null) {
                    selectedPiece.flip();
                    setSelectedPiece(selectedPiece);
                    scrollView.updatePiece(selectedPiece);
                }

            }
        });

        rotateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPiece != null) {
                    selectedPiece.rotateBy90();
                    setSelectedPiece(selectedPiece);
                    scrollView.updatePiece(selectedPiece);

                }
            }
        });


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

        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirm();
                Point startPoint = gridView.board.getStartingPoint(colorTurn);
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
                    if (gridView.board.isStartMove(colorTurn)) {
                        Point startPoint = gridView.board.getStartingPoint(colorTurn);
                        SquareView squareView = gridView.get(startPoint.x, startPoint.y);
                        squareView.setBackgroundResource(R.drawable.block);
                        squareView.getBackground().mutate();
                    }
                } else {
                    setConfirmButtonColor(Color.RED);
                    canConfirm = false;
                    if (gridView.board.isStartMove(colorTurn)) {
                        Point startPoint = gridView.board.getStartingPoint(colorTurn);
                        SquareView squareView = gridView.get(startPoint.x, startPoint.y);
                        squareView.color = -1;
                        squareView.setBackgroundResource(R.drawable.block_seed);
                        squareView.getBackground().setColorFilter(SquareColor.getColorFromCode(colorTurn), PorterDuff.Mode.MULTIPLY);
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
                if (!playerC[colorTurn])
                    setConfirmState();


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

        activity.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SingleplayerActivity) activity).backState();
            }
        });

    }

    public void refresh(Game game) {
        gridView.board = game.getBoard();
        gridView.fromBoard(gridView.board);
        this.game = game;
        initializeScrollView(game.getCurrentPlayer().getPieces());
    }

    private void initializeScores() {
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
    }

    private void initializeScrollView(ArrayList<Piece> pieces) {
        scrollView.color = pieces.get(0).color;
        scrollView.removeAllViews();
        scrollView.list = new ArrayList<>();
        for (Piece piece : pieces) {
            scrollView.add(piece);
        }
        scrollView.invalidate();

    }


    private void updateScores() {
        for (int i = 0; i < 4; i++) {
            scores[i].setText("" + gridView.board.getColorScore((byte) i));
        }
    }


    public void onMoving(Player player) {
        colorTurn = player.color;


        setConsoleState();
        changeHrColor(SquareColor.getColorFromCode(player.color));
        if (!playerC[player.color]) /*USER*/ {

            initializeScrollView(player.getPieces());

            if (!gameState) return;
            if (selectedPiece == null)
                setConsoleText(player.name + "'s turn");
            else
                setConfirmState();

        } else {
            if (selectedPiece == null)
                gridView.zoomOnCenter();
            setConsoleText(player.getName() + "'s turn");


        }

    }

    public void onMove(Player player, Move move) {
        //TODO add some animation method with fromBoard
        if (move != null) {
            //gridView.board.move(move);
            gridView.fromBoard(gridView.board);
            updateScores();
        }
        gridView.zoomOnCenter();
        gridView.redraw();
    }

    private void changeHrColor(int color) {
        activity.findViewById(R.id.hr).setBackgroundColor(color);
    }

    /***
     * Makes CONFIRM button apper
     */
    private void setConfirmState() {
        if (state == CONFIRM_STATE)
            return;
        consoleLayout.setVisibility(GONE);
        confirmContainer.setVisibility(VISIBLE);
        state = CONFIRM_STATE;
    }

    /***
     * Makes ConsoleView appear
     */
    private void setConsoleState() {
        if (state == CONSOLE_STATE)
            return;
        consoleLayout.setVisibility(VISIBLE);
        confirmContainer.setVisibility(GONE);
        state = CONSOLE_STATE;
    }

    /***
     * Called when user confirms placing piece
     */
    private void onConfirm() {
        if (canConfirm) {
            onMoveConfirm(gridView.selectedX, gridView.selectedY);
            this.setConsoleState();
        }
    }

    protected void removeSquareGroupFromList(Piece piece) {
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

    protected Piece getSelectedPiece() {
        return selectedPiece;
    }

    protected void setSelectedPiece(Piece piece) {
        this.selectedPiece = piece;
        gridView.selected(piece);
    }


    private void setConfirmButtonColor(int color) {
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
        setConsoleText("Game Over");
        gameState = false;
    }

}
