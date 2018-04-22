package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;

import java.security.InvalidParameterException;
import java.util.ArrayList;
/**
 * Logic board containing all pieces + some matrix logic etc.
 * Created by lika85456 on 24.03.2018.
 */

public class Board {
    public static final byte NOTHING = -1;
    private int size = 20;
    public byte[][] board = new byte[size][size];
    public ArrayList<Move> moves;

    public Board()
    {
        moves = new ArrayList<>();
        //Initialize array
        for (int i = 0; i < getWidth(); i++)
        {
            for (int ii = 0; ii < getHeight(); ii++)
            {
                board[i][ii] = NOTHING;
            }
        }
    }

    /**
     * Copy the original board into a new one.
     *
     * @param original board
     */

    public Board(Board original) {
        moves = (ArrayList<Move>) original.moves.clone();
        this.board = new byte[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = original.getColor(x, y);
            }
        }
    }

    public int getColorScore(byte color) {
        int t = 0;
        for (int i = 0; i < getWidth(); i++) {
            for (int ii = 0; ii < getHeight(); ii++) {
                if (getColor(i, ii) == color) t++;
            }
        }
        return t;
    }

    public boolean isOver(Player[] players) {
        AI ai = new AI();
        if (moves.size() < players.length) return false;
        for (int i = 0; i < players.length; i++) {
            if (ai.hasPossibleMove(this, players[i])) return false;
        }
        return true;
    }

    public boolean isStartMove(byte color) {
        for (Move move : moves)
            if (move.piece.color == color) return false;
        return true;
    }

    public ArrayList<Point> getSeeds(int color) {
        ArrayList<Point> toRet = new ArrayList<Point>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (isSeed((byte) color, x, y)) toRet.add(new Point(x, y));
            }
        }
        return toRet;
    }

    private boolean isSeed(byte color, int x, int y) {
        return (getSafe(x - 1, y - 1) == color ||
                getSafe(x + 1, y + 1) == color ||
                getSafe(x + 1, y - 1) == color ||
                getSafe(x - 1, y + 1) == color) &&
                (getSafe(x - 1, y) != color &&
                        getSafe(x + 1, y) != color &&
                        getSafe(x, y - 1) != color &&
                        getSafe(x, y + 1) != color);
    }

    //Gets value of array but with try catch block
    private byte getSafe(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size)
            return NOTHING;
        else
            return board[x][y];
    }



    public void move(Move move) {
        if (move != null) {
            moves.add(move);
            addPiece(move.getPiece(), move.getX(), move.getY());
        }
    }

    public boolean containsColor(byte color) {
        for (int i = 0; i < moves.size(); i++) {
            if (moves.get(i).color == color) return true;
        }
        return false;
    }

    public boolean isValid(Piece piece, int x, int y)
    {
        return isValid(piece, x, y, false);
    }

    public boolean isValid(Piece piece, int x, int y, boolean start) {
        //If start = true -> only position to control is the start position
        if (start == false) {
            if (isInside(piece, x, y)) {
                if (!collides(piece, x, y)) {
                    if (!isNextToAnotherPieceOfSameColor(piece, x, y)) {
                        return isOnCornerOfAnotherPiece(piece, x, y);
                    }
                }
            }
        }
        else {
            Point startingPoint = getStartingPoint(piece.color);
            if (isInside(piece, x, y)) {
                return isOnPos(piece, x, y, startingPoint.x, startingPoint.y);
            }
        }
        return false;
    }

    public Point getStartingPoint(byte color) {
        if (color == 0) return new Point(0, 0);
        if (color == 1) return new Point(getWidth() - 1, 0);
        if (color == 2) return new Point(0, getHeight() - 1);
        if (color == 3) return new Point(getWidth() - 1, getHeight() - 1);
        return null;

    }

    public boolean isOnPos(Piece piece, int x1, int y1, int x2, int y2) {
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            if (temp.x + x1 == x2 && temp.y + y1 == y2) return true;
        }
        return false;
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
                if (tPoint.x + x > getWidth() - 1) {
                    x--;
                    break;
                }

                if (x + tPoint.x < 0) {
                    x++;
                    break;
                }
                if (tPoint.y + y > getHeight() - 1) {
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
        if (x > 5 && x < 14 && y > 5 && y < 14) return true;
        for (int i = 0; i < piece.list.size(); i++) {
            Point tPoint = piece.list.get(i);
            if (tPoint.x + x > getWidth() - 1 || x + tPoint.x < 0 || tPoint.y + y > getHeight() - 1 || y + tPoint.y < 0)
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
            if (board[tPoint.x + x][tPoint.y + y] > -1)
                return true;

        }
        return false;
    }

    /**
     * Returns true if its at some corner of another piece with same color
     *
     * @param piece
     * @param x
     * @param y
     * @return
     */
    public boolean isOnCornerOfAnotherPiece(Piece piece, int x, int y) {
        //TODO refactor with getSafe
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            try {
                if (getColor(temp.x + x + 1, temp.y + y + 1) == piece.color) return true;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + x - 1, temp.y + 1 + y) == piece.color) return true;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + x - 1, temp.y - 1 + y) == piece.color) return true;
            } catch (Exception e) {
            }
            try {
                if (getColor(temp.x + x + 1, temp.y - 1 + y) == piece.color) return true;
            } catch (Exception e) {
            }
        }
        return false;
    }



    /***
     *
     * @param piece with valid color
     * @return
     */
    private boolean isNextToAnotherPieceOfSameColor(Piece piece, int x, int y) {
        if (piece.color == -1) throw new InvalidParameterException();
        for (int i = 0; i < piece.list.size(); i++) {
            Point temp = piece.list.get(i);
            if (getSafe(temp.x - 1 + x, temp.y + y) == piece.color) return true;
            if (getSafe(temp.x + x, temp.y - 1 + y) == piece.color) return true;
            if (getSafe(temp.x + x, temp.y + 1 + y) == piece.color) return true;
            if (getSafe(temp.x + 1 + x, temp.y + y) == piece.color) return true;
        }
        return false;
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

    public String toString() {
        String toRet = "";
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (getColor(x, y) == -1) toRet += " ";
                else toRet += getColor(x, y);
            }
            toRet += "\n";
        }
        return toRet;
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

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }
}
