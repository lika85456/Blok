package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class AI {

    //1 level = easiest
    //3 level = hardest
    public int level = 1;
    public Random random = new Random();

    public AI(int level) {
        this.level = level;
        if (level > 3) level = 3;
        if (level < 1) level = 1;
    }

    public AI() {

    }

    public Move think(Board board, Player player) {
        return think(board, player, board.isStartMove(player.color));
    }

    public Move think(Board board, Player player, boolean start) {
        ArrayList<Move> possibleMoves = getPossibleMoves(board, player, start);
        if (possibleMoves.size() == 0) return null;
        Collections.sort(possibleMoves);
        Collections.reverse(possibleMoves);
        Move max = Collections.max(possibleMoves);
        if (possibleMoves.size() == 1) return possibleMoves.get(0);
        if (level == 1)
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        if (level == 2) return max;
        if (level == 3) {
            Board tempBoard = new Board(board);
            ArrayList<Move> bestMoves = new ArrayList<Move>();
            for (Move tMove : possibleMoves)
                if (tMove.getScore() == max.getScore()) bestMoves.add(tMove);

            int maxScore = 0;
            Move bestMove = max;
            for (Move move : bestMoves) {
                int score = getChainScore(board, move);
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = move;
                }
            }
            return bestMove;

        }
        return null;
        //TODO make better ai plz
    }

    //Returns number of moves possible after @param move
    public int getChainScore(Board board, Move move) {
        Board tempBoard = new Board(board);
        tempBoard.move(move);
        return getPossibleMoves(tempBoard, new Player(move.piece.color), false).size();
    }

    public boolean hasPossibleMove(Board board, Player player) {
        if (board.moves.size() < 5) return true;
        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<Move>();

        if (seeds.size() == 0 || usablePieces.size() == 0) return false;

        for (Point seed : seeds) {
            for (Piece usablePiece : usablePieces) {
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    Piece tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.list) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, false)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public ArrayList<Move> getPossibleMoves(Board board, Player player, boolean start) {
        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<Move>();

        //To have bigger pieces at the start
        Collections.reverse(usablePieces);

        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point left, Point right) {
                return ((left.x - 10) * (left.x - 10) + (left.y - 10) * (left.y - 10)) - ((right.x - 10) * (right.x - 10) + (right.y - 10) * (right.y - 10));
            }
        };

        //Thanks to sorted array we firstly generate better moves (its more in center)
        Collections.sort(seeds, comparator);

        try {
            Log.d("DEBUG1", seeds.get(0).x + " " + seeds.get(0).y);

        } catch (Exception e) {
        }
        if (start)
            seeds.add(board.getStartingPoint(player.color));
        int maxScore = 0;
        int badMoves = 0;
        for (Point seed : seeds) {
            for (Piece usablePiece : usablePieces) {
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    Piece tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.list) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, start)) {
                            Move tempMove = new Move(board, tempPiece, x, y);
                            int tempMoveScore = tempMove.getScore();
                            if (tempMoveScore >= maxScore) {
                                if (tempMoveScore > maxScore)
                                    moves.clear();
                                moves.add(tempMove);
                                maxScore = tempMoveScore;
                                badMoves = 0;
                            } else
                                badMoves++;

                            //To reduce time
                            if (moves.size() > 20 || badMoves > 10) return moves;
                        }
                    }
                }
            }
        }

        return moves;
    }
}
