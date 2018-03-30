package com.example.lika85456.blokusdeskgame.Game;

import android.graphics.Point;

import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Logic board containing all pieces + some matrix logic etc.
 * Created by lika85456 on 24.03.2018.
 */

public class Board {
    public byte[][] board = new byte[20][20];

    public Board()
    {
        //Initialize array
        for(int i = 0;i<20;i++)
        {
            for(int ii=0;ii<20;ii++)
            {
                board[i][ii] = -1;
            }
        }
    }

    /**
     * Copy the original board into a new one.
     *
     * @param original board
     */
    public Board(Board original) {
        this.board = original.board.clone();
    }

    public void Move(Move move) {
        addPiece(move.getRealPiece(), move.getX(), move.getY());
    }

    public boolean isValid(Piece piece,int x, int y)
    {
        return isInside(piece, x, y) && !collides(piece, x, y) && isOnCorner(piece, x, y);
    }

    /***
     * Adds piece to array board !!!WITHOUT CONTROL!!!
     * @param piece
     */
    public void addPiece(Piece piece, int x, int y)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point tPoint = piece.list.get(i);
            board[tPoint.x + x][tPoint.y + y] = piece.color;
        }
    }

    public Point getPositionInside(Piece piece, int x, int y) {
        while (!isInside(piece, x, y))
            for (int i = 0; i < piece.list.size(); i++) {
                Point tPoint = piece.list.get(i);
                if (tPoint.x + x > 19) {
                    x--;
                    break;
                }

                if (x + tPoint.x < 0) {
                    x++;
                    break;
                }
                if (tPoint.y + y > 19) {
                    y--;
                    break;
                }
                if (y + tPoint.y < 0) {
                    y++;
                    break;
                }
            }
        return new Point(x, y);
    }

    private boolean isInside(Piece piece, int x, int y) {
        for (int i = 0; i < piece.list.size(); i++) {
            Point tPoint = piece.list.get(i);
            if (tPoint.x + x > 19 || x + tPoint.x < 0 || tPoint.y + y > 19 || y + tPoint.y < 0)
                return false;
        }
        return true;
    }

    /***
     *
     * @param piece
     * @return if piece collides with another piece on the board
     */
    private boolean collides(Piece piece, int x, int y)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point tPoint = piece.list.get(i);
            if(board[tPoint.x+x][tPoint.y+y]!=-1)
                return true;

        }
        return false;
    }

    /***
     *
     * @param piece with valid color
     * @return
     */
    private boolean isOnCorner(Piece piece, int x, int y) {
        if (piece.color == -1) throw new InvalidParameterException();
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            try {
                if (getColor(temp.x - 1 + x, temp.y + y) == piece.color) return false;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + x, temp.y - 1 + y) == piece.color) return false;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + x, temp.y + 1 + y) == piece.color) return false;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + 1 + x, temp.y + y) == piece.color) return false;
            } catch (Exception e) {
            }


        }
        return true;
    }

    /**
     * Make a move on the board with position and piece
     *
     * @param x     position of move
     * @param y     position of move
     * @param piece to make the move with
     */
    public Board makeSimMove(int x, int y, Piece piece, Player player) {
        ArrayList<Point> points = piece.list;
        int color = player.color;
        Board nextBoard = new Board(this);
        nextBoard.addPiece(piece, x, y);


        return nextBoard;
    }

    /***
     * returns color in byte
     * @param x
     * @param y
     * @return
     */
    public byte getColor(int x, int y) {
        return board[x][y];
    }

}
