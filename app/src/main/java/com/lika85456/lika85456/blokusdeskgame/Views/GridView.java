package com.lika85456.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lika85456.lika85456.blokusdeskgame.Game.Board;
import com.lika85456.lika85456.blokusdeskgame.Game.Piece;
import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Utilities.SquareColor;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends ZoomView {
    private final Context ctx;
    private final ArrayList<SquareView> grid;
    public Board board;
    public int selectedX = 0;
    public int selectedY = 0;
    public boolean positionChanged = false;
    private int width = 0;
    private int height = 0;
    private Piece selectedPiece;
    private int pointSize;
    private GridViewMoveListener gridViewMoveListener;
    private RelativeLayout gridView;

    public GridView(Context ctx) {
        super(ctx);
        this.ctx = ctx;
        grid = new ArrayList<>();
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        grid = new ArrayList<>();
    }

    public void setOnMoveListener(GridViewMoveListener l) {
        this.gridViewMoveListener = l;
    }

    public void onDoubleClick() {
        if (selectedPiece == null) return;
        selectedPiece.rotateBy90();
        selectedX = -10;
        selectedY = -10;
        //TODO animation
        //zoomTo(1.f,0,0);
    }

    public void positionOn(float x, float y) {
        if (this.selectedPiece != null) {

            int tX = (int) (x / pointSize - selectedPiece.getMass().x);
            int tY = (int) (y / pointSize - selectedPiece.getMass().y);
            Point positionInside = board.getPositionInside(selectedPiece, tX, tY);
            tX = positionInside.x;
            tY = positionInside.y;
            if (tX != selectedX || tY != selectedY) {
                selectedX = tX;
                selectedY = tY;
                gridViewMoveListener.onSelectedSquareGroupMove(tX, tY);
                fromBoard(board);
            }
        }
    }

    private void addPieceWithColor(Piece piece, int x, int y, int color) {
        ArrayList<Point> squareList = piece.getSquares();
        for (int i = 0; i < squareList.size(); i++) {
            Point temp = squareList.get(i);
            get(temp.x + x, temp.y + y).setColorCode(color);
            get(temp.x + x, temp.y + y).color = -2;
        }
    }

    public void selected(Piece selected) {
        this.selectedPiece = selected;
        selectedX = -10;
        selectedY = -10;
    }


    private void fill() {
        for (int x = 0; x < 20; x++)
            for (int y = 0; y < 20; y++) {
                add(new SquareView(ctx, SquareColor.BLANK, x, y));

            }
    }

    protected void onMeasure(int w, int h) {
        this.width = MeasureSpec.getSize(w);
        this.height = MeasureSpec.getSize(h);
        this.pointSize = (int) ((float) Math.min(width, height) / 20.f);
        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).measure(pointSize, pointSize);
        }
        this.setMeasuredDimension(width, Math.min(width, height));

    }

    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            //int pointSize = (int) ((float) Math.min(width, height) / 20.f);
            if (pointSize == 0) pointSize = 36;


            for (int x = 0; x < grid.size(); x++) {
                int left = (pointSize * grid.get(x).x);
                int top = (pointSize * grid.get(x).y);
                grid.get(x).layout(left, top, left + pointSize, top + pointSize);
            }
        }
    }

    /***
     * Sets the grid from board array
     * @param board
     */
    public void fromBoard(Board board) {
        this.board = board;
        for (int x = 0; x < 20; x++)
            for (int y = 0; y < 20; y++) {
                if (get(x, y).color != board.board[x][y])
                    setColor(x, y, board.board[x][y]);
            }
        if (selectedPiece != null) {
            if (board.isValid(selectedPiece, selectedX, selectedY, board.isStartMove(selectedPiece.color)))
                addPiece(selectedPiece, selectedX, selectedY);
            else
                addPieceWithColor(selectedPiece, selectedX, selectedY, 0xCCA9A9A9);
        }

    }

    private void addPiece(Piece piece, int x, int y) {
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            get(temp.x + x, temp.y + y).setColor(piece.color);
            get(temp.x + x, temp.y + y).color = SquareColor.UNKNOWN;
        }
    }

    /***
     *
     * @param x
     * @param y
     * @param color (byte)
     */
    private void setColor(int x, int y, byte color) {
        get(x, y).setColor(color);
        get(x, y).color = color;
    }

    private SquareView get(int x, int y) {
        return grid.get(x * 20 + y);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        fill();
    }

    private void add(SquareView toAdd) {
        if (gridView == null) {
            gridView = findViewById(R.id.insider_grid);
        }
        gridView.addView(toAdd);
        grid.add(toAdd);
    }


}

