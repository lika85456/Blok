
package com.lika85456.lika85456.blokusdeskgame;

import com.lika85456.lika85456.blokusdeskgame.Game.AI;
import com.lika85456.lika85456.blokusdeskgame.Game.Board;
import com.lika85456.lika85456.blokusdeskgame.Game.Game;
import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;

import org.junit.Test;

public class AIUnitTest {
    @Test
    public void testGame() {
        Board board = new Board();
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++)
            players[i] = new Player(i, "");
        Game game = new Game(players);
        game.setBoard(board);

        AI ai = new AI(3);


        int playerIndex = 0;
        while (game.board.isOver(players) == false) {
            Move move = ai.deepThink(board, players[playerIndex % 4], game.board.isStartMove((byte) (playerIndex % 4)), 0, 3);
            game.play(players[playerIndex % 4], move);
            System.out.println("" + move.getScore());
        }

    }


}
