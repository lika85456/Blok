package com.example.lika85456.blokusdeskgame.Game;

/**
 * Extension of the Player class to take turns by calculation.
 *
 * @author Hsing
 */
public class ComputerPlayer extends Player {

    final AlphaBeta AI = new AlphaBeta();

    public ComputerPlayer(byte setColor) {
        super(setColor, generateBotName(setColor));
    }

    public static String generateBotName(byte color) {
        return "Bot #" + color;
    }

    public ComputerPlayer(Player origPlayer) {
        super(origPlayer);
    }

    /**
     * Take turn by calculation of optimal move and making it.
     */
    public Move getMove(Game game) {
        return AI.move(game, color);
    }

}

