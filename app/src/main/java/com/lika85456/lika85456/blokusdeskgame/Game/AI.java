package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;

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
        ArrayList<Move> possibleMoves = getPossibleMoves(board, player, start, 20);
        possibleMoves = getBestMovesFromList(possibleMoves);

        if (possibleMoves.size() == 0) return null;

        if (board.moves.size() < 16) return possibleMoves.get(random.nextInt(possibleMoves.size()));

        if (level == 1)
            return possibleMoves.get(random.nextInt(possibleMoves.size()));
        if (level == 2)
            return deepThink(board, player, start, 0, 2).get(0);
        if (level == 3)
            return deepThink(board, player, start, 0, 4).get(0);

        return null;
        //TODO make better ai plz
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
    public ArrayList<Move> deepThink(Board board, Player player, boolean start, int depth, int maxDepth) {
        ArrayList<Move> moves = getPossibleMoves(board, player, start, 25);
        moves = getBestMovesFromList(moves);
        while (moves.size() > 15)
            moves.remove(moves.size() - 1);

        ArrayList<Move> bestMoves = new ArrayList<Move>();

        int maxScore = 0;
        if (depth < maxDepth) {
            for (int i = 0; i < moves.size(); i++) {
                Board tempBoard = new Board(board);
                tempBoard.move(moves.get(i));

                Player tempPlayer = new Player(player);
                tempPlayer.iDidMove(moves.get(i));

                //Theese are the best moves possible after one of the bestMoves
                ArrayList<Move> bestMovesInDeep = deepThink(tempBoard, tempPlayer, false, depth + 1, maxDepth);

                if (bestMovesInDeep.size() == 0) continue;

                if (bestMovesInDeep.get(0).score >= maxScore) {

                    if (bestMovesInDeep.get(0).score > maxScore) {
                        bestMoves.clear();
                        maxScore = bestMovesInDeep.get(0).score;
                    }
                    bestMoves.add(moves.get(i));
                }
            }
        }

        if (bestMoves.size() > 0)
            return bestMoves;
        if (moves.size() == 0)
            moves.add(null);
        return moves;
    }

    /***
     * Return all possible moves
     * @param board
     * @param player
     * @param start - is it start of the game?
     * @param limit - limit of moves
     * @return
     */
    public ArrayList<Move> getPossibleMoves(Board board, Player player, boolean start, int limit) {

        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<Move>();

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

        for (int usablePieceIndex = 0; usablePieceIndex < usablePieces.size(); usablePieceIndex++) {
            for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex++) {
                Piece usablePiece = usablePieces.get(usablePieceIndex);
                Point seed = seeds.get(seedIndex);
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    Piece tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.seedable) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, start)) {
                            Move tempMove = new Move(tempPiece, x, y);
                            tempMove.score = Move.generateScore(board, tempPiece, x, y);
                            moves.add(tempMove);
                        }
                        if ((System.currentTimeMillis() - time > 250 || moves.size() > limit) && moves.size() > 3) {
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
        if (list == null || list.size() == 0) return new ArrayList<Move>();
        Move max = Collections.max(list);
        int maxScore = max.score;
        ArrayList<Move> toRet = new ArrayList<Move>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).score == maxScore)
                toRet.add(list.get(i));
        }
        return toRet;
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

}
