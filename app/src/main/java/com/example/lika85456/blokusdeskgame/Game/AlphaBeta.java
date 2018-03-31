package com.example.lika85456.blokusdeskgame.Game;


import com.example.lika85456.blokusdeskgame.Model.GameHandler;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Algorithm class from which all decision making algorithms inherit.
 * Holds all generic functions that are necessary for
 * decision making.
 *
 * @author Hsing
 */

public class AlphaBeta extends Algorithm {

    /**
     * Get all the boards from a vector of moves
     *
     * @param moves to get board from
     * @return the board for each move
     **/
    private Vector<Board> getBoards(Vector<Move> moves) {
        int size = moves.size();
        Vector<Board> boards = new Vector<Board>();

        for (int i = 0; i < size; i++) {
            Move move = moves.elementAt(i);
            boards.add(move.getBoard());
        }

        return boards;
    }

    @Override
    public Move move(Game game, int player) {
        Board currState = new Board(game.getBoard());
        Vector<Player> players = simulatePlayers(game.getPlayers());
        Vector<Move> moves = getChildren(currState, player, players);
        Vector<Board> children = getBoards(moves);
        int size = children.size();

        //GameHandler Over
        if (size == 0) {
            return null;
        }

        Board maxBoard = children.elementAt(0);
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        int nextPlayer = Game.getNextPlayerId(player);
        int maxScore = alphaBeta(maxBoard, 1, min, max,
                nextPlayer, player, players);

        // Get board with maximum score value
        int select = 0;

        for (int index = 1; index < size; index++) {
            Board currBoard = children.elementAt(index);
            nextPlayer = Game.getNextPlayerId(player);
            int currScore = alphaBeta(currBoard, 1, min, max,
                    nextPlayer, player, players);

            if (currScore > maxScore) {
                maxScore = currScore;
                maxBoard = currBoard;
                select = index;
            }
        }

        //game.getBoard() = maxBoard;
        Player currPlayer = game.getPlayers()[player];
        Move nextMove = moves.elementAt(select);
        currPlayer.setUsedPiece(nextMove.getPieceUsed());

        return nextMove;
    }

    private Vector<Player> simulatePlayers(Player[] origPlayers) {
        Vector<Player> players = new Vector<Player>();

        for (int i = 0; i < 4; i++) {
            Player origPlayer = origPlayers[i];
            Player newSim = new ComputerPlayer(origPlayer);
            players.add(newSim);
        }

        return players;
    }

    /**
     * The actual algorithm for selecting the minimax value with pruning.
     *
     * @param board      current state of the game
     * @param depth      current depth
     * @param alpha
     * @param beta
     * @param currPlayer current player
     * @param origPlayer original player that ran the algorithm
     * @return
     **/
    private int alphaBeta(Board board, int depth, int alpha, int beta,
                          int currPlayer, int origPlayer, Vector<Player> players) {
        if (depth < 1)
            return evaluate(board, origPlayer);

        Vector<Move> moves = getChildren(board, currPlayer, players);
        Vector<Board> children = getBoards(moves);
        int size = children.size();

        if (children.isEmpty())
            return evaluate(board, origPlayer);

        if (currPlayer == origPlayer) {
            //TODO take out
            //TODO unit test
            for (int index = 0; index < size; index++) {
                Board nextBoard = children.elementAt(index);
                currPlayer = GameHandler.incrementTurn(currPlayer);
                int potentialMax = alphaBeta(nextBoard, depth - 1, alpha, beta,
                        currPlayer, origPlayer, players);
                alpha = Math.max(alpha, potentialMax);
                if (beta <= alpha)
                    break;
            }

            return alpha;
        } else {
            for (int index = 0; index < size; index++) {
                Board nextBoard = children.elementAt(index);
                currPlayer = GameHandler.incrementTurn(currPlayer);
                int potentialMin = alphaBeta(nextBoard, depth - 1, alpha, beta,
                        currPlayer, origPlayer, players);
                beta = Math.min(beta, potentialMin);
                if (beta <= alpha)
                    break;
            }

            return beta;
        }
    }

    /**
     * get all children of a state
     *
     * @param currState
     * @return children
     **/
    private Vector<Move> getChildren(Board original,
                                     int player, Vector<Player> players) {
        Vector<Move> children = new Vector<Move>();
        Player currPlayer = players.elementAt(player);
        ArrayList<Piece> usedPieces = currPlayer.getUsedPieces();
        int size = usedPieces.size();

        for (int i = 0; i < size; i++) {
            Move nextMove;

            if (!usedPieces[i]) {
                Piece piece = currPlayer.getSelectedPiece(i);

                Board nextBoard = null;
                for (int x = 0; x < original.getWidth(); x++) {
                    for (int y = 0; y < original.getHeight(); y++) {
                        Board currState = new Board(original);

                        if (currState.isValidMove(x, y, piece, currPlayer, currState)) {
                            nextBoard = currState.makeMove(x, y, piece, currPlayer, currState, true);
                            nextMove = new Move(i, nextBoard);
                            children.add(nextMove);
                        }
                    }
                }
            }
        }

        return children;
    }
}
*/