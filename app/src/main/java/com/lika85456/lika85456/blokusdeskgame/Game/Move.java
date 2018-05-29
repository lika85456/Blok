package com.lika85456.lika85456.blokusdeskgame.Game;

import android.support.annotation.NonNull;

public class Move implements Comparable<Move> {

    public int score; //temp for AI
    private Piece piece;
    private int x;
    private int y;
    private byte color;

    public Move(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        this.color = piece.color;
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

    public static int generateScore(Board board, Piece piece, int x, int y) {
        int boardScore = (-(int) Math.hypot((board.getWidth() / 2 - piece.getMass().x - x), (board.getHeight() / 2 - piece.getMass().y - y)));

        int nBefore = board.getSeeds(piece.color).size();
        Board tempBoard = new Board(board);
        tempBoard.move(new Move(piece, x, y));
        int nAfter = tempBoard.getSeeds(piece.color).size();

        int seedScore = (nAfter - nBefore) * 5;


        int before = 0;
        int after = 0;
        for (int i = 0; i < 3; i++) {
            before += board.getSeeds((piece.color + i) % 4).size();
        }
        tempBoard = new Board(board);
        tempBoard.move(new Move(piece, x, y));
        for (int i = 0; i < 3; i++) {
            after += board.getSeeds((piece.color + i) % 4).size();
        }


        int size = piece.list.size() * 5;
        int enemySeedScore = (after - before) * 5;
        return boardScore + seedScore + size - enemySeedScore;
    }
    @Override
    public int compareTo(@NonNull Move o) {
        return score - o.score;
    }

    public String toString() {
        //return piece.toString()+" on:"+x+" y";
        return String.valueOf(score);
    }

}

