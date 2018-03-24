package com.example.lika85456.blokusdeskgame.Model;

import android.graphics.Point;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class Board {
    byte[][] board = new byte [20][20];

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

    /***
     * Adds piece to array board !!!WITHOUT CONTROL!!!
     * @param piece
     */
    public void addPiece(Piece piece)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point tPoint = piece.list.get(i);
            board[tPoint.x][tPoint.y] = piece.color;
        }
    }

    /***
     *
     * @param piece
     * @return if piece collides with another piece on the board
     */
    public boolean collides(Piece piece)
    {
        for(int i = 0;i<piece.list.size();i++)
        {
            Point tPoint = piece.list.get(i);
            if(board[tPoint.x][tPoint.y]!=-1)
                return true;
        }
        return false;
    }




}
