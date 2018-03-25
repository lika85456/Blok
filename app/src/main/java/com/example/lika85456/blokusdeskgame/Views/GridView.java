package com.example.lika85456.blokusdeskgame.Views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.lika85456.blokusdeskgame.Model.Board;
import com.example.lika85456.blokusdeskgame.Model.Piece;
import com.example.lika85456.blokusdeskgame.Model.SquareColor;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Utilities.Initialization.OnOnInitializedListener;

import java.util.ArrayList;

/**
 * Created by lika85456 on 16.03.2018.
 */

public class GridView extends ZoomView {
    private final Context ctx;
    private final ArrayList<SquareView> grid;
    private int width = 0;
    private int height = 0;
    private boolean initialized = false;
    private SquareGroup selected;
    private int pointSize;
    private Board board;
    private OnOnInitializedListener onInitializedListener = new OnOnInitializedListener();

    private int selectedX = 0;
    private int selectedY = 0;
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

    public void positionOn(float x, float y) {
        if (this.selected != null) {
            fromBoard(this.board);
            int tX = (int) (x / pointSize - selected.piece.mass().x);
            int tY = (int) (y / pointSize - selected.piece.mass().y);

            if (!board.isValid(selected.piece, tX, tY)) {
                Point positionInside = board.getPositionInside(selected.piece, tX, tY);
                tX = positionInside.x;
                tY = positionInside.y;
                if (!board.isValid(selected.piece, tX, tY))
                    addPieceWithColor(selected.piece, tX, tY, SquareColor.getColorFromCode(selected.piece.color) - 0x55222222);
                else
                    addPiece(selected.piece, tX, tY);
            } else
                addPiece(selected.piece, tX, tY);
            selectedX = tX;
            selectedY = tY;
        }
    }

    private void addPieceWithColor(Piece piece, int x, int y, int color) {
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            get(temp.x + x, temp.y + y).setColorCode(color);
        }
    }

    public void selected(SquareGroup selected) {
        this.selected = new SquareGroup(selected);
    }

    private void fill(){
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
        this.invalidate();
        if (!initialized)
        {
            initialized=true;
            fill();
            onInitializedListener.onInit();
        }
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
     * Sets OnOnInitializedListener
     * @param onInitializedListener
     */
    public void setOnInitializedListener(OnOnInitializedListener onInitializedListener)
    {
        this.onInitializedListener = onInitializedListener;
    }

    /***
     * Sets the grid from board array
     * @param board
     */
    public void fromBoard(Board board)
    {
        this.board = board;
        for(int x = 0;x<20;x++)
            for(int y = 0;y<20;y++)
            {
                //if(get(x,y).color!=board.board[x][y])
                setColor(x, y, board.board[x][y]);
            }
    }

    private void addPiece(Piece piece, int x, int y)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point temp = piece.list.get(i);
            get(temp.x+x,temp.y+y).setColor(piece.color);
        }
    }

    /***
     *
     * @param x
     * @param y
     * @param color (byte)
     */
    private void setColor(int x, int y, byte color)
    {
        get(x,y).setColor(color);
    }

    private SquareView get(int x, int y)
    {
        return grid.get(x*20+y);
    }

    private void add(SquareView toAdd) {
        if (gridView == null) {
            gridView = findViewById(R.id.insider_grid);
        }
        gridView.addView(toAdd);
        grid.add(toAdd);
    }


}

