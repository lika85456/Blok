package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AI {

    //1 level = easiest
    //3 level = hardest
    public int level = 1;

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
        if (level == 1) return possibleMoves.get(possibleMoves.size() / 2);
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

    public ArrayList<Move> lightWeightMoves(Board board, Player player, boolean start) {
        ArrayList<Point> seeds = board.getSeeds(player.color);
        List<Piece> usablePieces = player.getPieces();

        while (usablePieces.size() > 5)
            usablePieces.remove(0);

        ArrayList<Move> moves = new ArrayList<Move>();

        if (start)
            seeds.add(board.getStartingPoint(player.color));

        for (Point seed : seeds) {
            for (Piece usablePiece : usablePieces) {
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    Piece tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.list) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, start)) {
                            moves.add(new Move(board, tempPiece, x, y));
                        }
                    }
                }
            }
        }

        return moves;
    }

    public ArrayList<Move> getPossibleMoves(Board board, Player player, boolean start) {
        ArrayList<Point> seeds = board.getSeeds(player.color);
        ArrayList<Piece> usablePieces = player.getPieces();
        ArrayList<Move> moves = new ArrayList<Move>();

        if (start)
            seeds.add(board.getStartingPoint(player.color));

        for (Point seed : seeds) {
            for (Piece usablePiece : usablePieces) {
                for (int rotation = 0; rotation < 3; rotation++, usablePiece.rotateBy90()) {
                    Piece tempPiece = new Piece(usablePiece);

                    for (Point square : usablePiece.list) {
                        int x = seed.x - square.x;
                        int y = seed.y - square.y;
                        if (board.isValid(tempPiece, x, y, start)) {
                            moves.add(new Move(board, tempPiece, x, y));
                        }
                    }
                }
            }
        }

        return moves;
    }
}
