package com.example.lika85456.blokusdeskgame.Game;

public class Move {

    public Piece pieceUsed;
    public Board board;

    public int x;
    public int y;
    public byte color;

    public Move(Board board, Piece piece, int x, int y) {
        this.board = new Board(board);
        board.addPiece(pieceUsed, x, y);
        this.pieceUsed = piece;
        this.x = x;
        this.y = y;
    }

    public byte getColor() {
        return color;
    }

    public Piece getPiece() {
        return pieceUsed;
    }

    /*public Piece getRealPiece() {
        return Piece.groups.get(pieceUsed);
    }
    */

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Board getBoard() {
        return board;
    }
}

