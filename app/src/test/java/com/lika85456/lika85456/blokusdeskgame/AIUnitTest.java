
package com.lika85456.lika85456.blokusdeskgame;

import org.junit.Test;

public class AIUnitTest {
    @Test
    public void testGame() {
        Game game = new Game();
        AI ai = new AI(game);
        boolean[] players = new boolean[]{true, true, true, true};
        int turn = 0;
        while (allTrue(players)) {
            if (ai.hasMove(turn)) {
                Move move = ai.think(turn, 3);
                System.out.println(game.toString());
                game.play(move);
            } else {
                players[turn] = false;
            }
            turn++;
            if (turn > 3)
                turn = 0;
        }
    }

    public boolean allTrue(boolean[] array) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == false) return false;
        return true;
    }
}
