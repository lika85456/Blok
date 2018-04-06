package com.lika85456.lika85456.blokusdeskgame.Game;

public class Move implements Comparable<Move> {

    public Piece piece;
    public int x;
    public int y;
    public byte color;
    private Board board;

    public Move(Board board, Piece piece, int x, int y) {
        this.board = board;
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    public byte getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int getBoardScore() {
        return (int) (Math.hypot(x - piece.getMass().x - board.getWidth() / 2, y - piece.getMass().y - board.getHeight() / 2));
    }

    private int getSeedScore() {
        int nBefore = board.getSeeds(piece.color).size();
        Board tempBoard = new Board(board);
        tempBoard.move(this);
        int nAfter = tempBoard.getSeeds(piece.color).size();
        return nAfter - nBefore + 1;

    }

    public int getScore() {
        return getBoardScore() + getSeedScore() * 10 + piece.list.size() * 10;
    }

    @Override
    public int compareTo(Move o) {
        return getScore() - o.getScore();
    }

    public String toString() {
        //return piece.toString()+" on:"+x+" y";
        return String.valueOf(getScore());
    }

    public Board getBoard() {
        return board;
    }
}

