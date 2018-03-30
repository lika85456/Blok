package com.example.lika85456.blokusdeskgame.Game;

public class Move {

    private int pieceUsed;
    private Board board;

    private int x;
    private int y;
    private byte color;

    public Move(int piece, int x, int y) {
        this.pieceUsed = piece;
        this.x = x;
        this.y = y;
    }

    public Move(int piece, Board nextBoard) {
        pieceUsed = piece;
        board = nextBoard;
    }

    public byte getColor() {
        return color;
    }

    public int getPieceUsed() {
        return pieceUsed;
    }

    public Piece getRealPiece() {
        return Piece.groups.get(pieceUsed);
    }

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

