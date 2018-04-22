package com.lika85456.lika85456.blokusdeskgame.Game;

public class Move implements Comparable<Move> {

    public Piece piece;
    public int x;
    public int y;
    public byte color;
    public int score;

    public Move(Move move) {
        this.piece = move.piece;
        this.x = move.x;
        this.y = move.y;
    }

    public Move(Piece piece, int x, int y) {
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
/*
    private int getBoardScore() {
        return (int)Math.hypot((board.getWidth()/2 - this.piece.getMass().x - x),(board.getHeight()/2 - this.piece.getMass().y - y));
    }

    private int getEnemySeedScore()
    {
        int before = 0;
        int after = 0;
        for(int i = 0;i<3;i++)
        {
            before+=board.getSeeds((piece.color+i)%4).size();
        }
        Board tempBoard = new Board(board);
        tempBoard.move(this);
        for(int i = 0;i<3;i++)
        {
            after+=board.getSeeds((piece.color+i)%4).size();
        }
        return after-before;
    }

    private int getSeedScore() {
        int nBefore = board.getSeeds(piece.color).size();
        Board tempBoard = new Board(board);
        tempBoard.move(this);
        int nAfter = tempBoard.getSeeds(piece.color).size();
        return nAfter - nBefore;

    }

    public int getScore() {
        int boardScore = (-getBoardScore());
        int seedScore = getSeedScore()*5;
        int size = piece.list.size()*5;
        int enemySeedScore = getEnemySeedScore()*5;
        return boardScore + seedScore + piece.list.size()*5 - enemySeedScore;
    }
*/

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
    public int compareTo(Move o) {
        return score - o.score;
    }

    public String toString() {
        //return piece.toString()+" on:"+x+" y";
        return String.valueOf(score);
    }

}

