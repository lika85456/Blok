package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class AI {

    //1 level = easiest
    //3 level = hardest
    private int level = 1;
    private Random random = new Random();

    public AI(int level) {
        this.level = level;
        if (level > 3) level = 3;
        if (level < 1) level = 1;
    }

    public Move think(Board board, Player player) {
        return think(board, player, board.isStartMove(player.color));
    }


    public Move think(Board board, Player player, boolean start) {

        if (board.moves.size() < 28) {
            ArrayList<Move> moves = getBestMovesFromList(getPossibleMoves(board, player, start, 30));
            return moves.get(random.nextInt(moves.size()));
        }
        return deepThink(board, player, start, 0, 3, System.currentTimeMillis());
    }


    /***
     * Returns the best moves, with deep thinking
     * @param board
     * @param player
     * @param start
     * @param depth
     * @param maxDepth
     * @return
     */
    private Move deepThink(Board board, Player player, boolean start, int depth, int maxDepth, long millis) {
        ArrayList<Move> moves = getPossibleMoves(board, player, start, 20);
        moves = getBestMovesFromList(moves);

        //if it takes too long just return what we have
        if (System.currentTimeMillis() - millis > 500)
            return moves.get(random.nextInt(moves.size()));

        ArrayList<Move> bestMoves = new ArrayList<Move>();


        int bestScore = 0;
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            Board tempBoard = board.makeSimMove(move.getX(), move.getY(), move.getPiece(), player);
            int tempScore = deep(tempBoard, player, start, depth, maxDepth, millis);
            if (tempScore > bestScore) {
                bestMoves.clear();
                bestScore = tempScore;
                bestMoves.add(move);
            }
        }
        if (bestMoves.size() == 0) return null;
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private int deep(Board board, Player player, boolean start, int depth, int maxDepth, long millis) {
        if (depth >= maxDepth) return 0;
        ArrayList<Move> moves = getPossibleMoves(board, player, start, 20);
        moves = getBestMovesFromList(moves);

        //if it takes too long just return what we have
        if (System.currentTimeMillis() - millis > 500) return 0;
        int score = 0;
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            score += move.score;
            Board tempBoard = board.makeSimMove(move.getX(), move.getY(), move.getPiece(), player);
            score += deep(tempBoard, player, start, depth + 1, maxDepth, millis);
        }
        return score;
    }

    /***
     * Return all possible moves
     * @param board
     * @param player
     * @param start - is it start of the game?
     * @param limit - limit of moves
     * @return
     */
    private ArrayList<Move> getPossibleMoves(Board board, Player player, boolean start, int limit) {

        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<>();

        if (start)
            seeds.add(board.getStartingPoint(player.color));

        if (usablePieces.get(0).list.size() < usablePieces.get(usablePieces.size() - 1).list.size())
            Collections.reverse(usablePieces);


        Comparator<Point> comparator = new Comparator<Point>() {
            @Override
            public int compare(Point left, Point right) {
                return ((left.x - 10) * (left.x - 10) + (left.y - 10) * (left.y - 10)) - ((right.x - 10) * (right.x - 10) + (right.y - 10) * (right.y - 10));
            }
        };

        //Thanks to sorted array we firstly generate better moves (its more in center)
        Collections.sort(seeds, comparator);

        long time = System.currentTimeMillis();
        Piece usablePiece;
        Point seed;
        Move tempMove;
        Piece tempPiece;
        for (int usablePieceIndex = 0; usablePieceIndex < usablePieces.size(); usablePieceIndex++) {
            for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex++) {
                usablePiece = usablePieces.get(usablePieceIndex);
                seed = seeds.get(seedIndex);
                for (int flips = 0; flips < 1; flips++, usablePiece.flip())
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.seeds) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, start)) {
                            tempMove = new Move(tempPiece, x, y);
                            tempMove.score = Move.generateScore(board, tempPiece, x, y);
                            moves.add(tempMove);
                        }
                        if ((System.currentTimeMillis() - time > 500 || moves.size() > limit) && moves.size() > 0) {
                            //moves = getBestMovesFromList(moves);
                            return moves;
                        }
                    }
                }
            }
        }
        return moves;
    }


    private ArrayList<Move> getBestMovesFromList(ArrayList<Move> list) {
        if (list == null || list.size() == 0) return new ArrayList<>();
        Move max = Collections.max(list);
        int maxScore = max.score;
        ArrayList<Move> toRet = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).score == maxScore)
                toRet.add(list.get(i));
        }
        return toRet;
    }

    public boolean hasPossibleMove(Board board, Player player) {
        if (board.moves.size() < 8) return true;
        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<>();

        if (seeds.size() == 0 || usablePieces.size() == 0) return false;

        for (Point seed : seeds) {
            for (Piece usablePiece : usablePieces) {
                for (int flips = 0; flips < 1; flips++, usablePiece.flip())
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

}
