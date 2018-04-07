package com.lika85456.lika85456.blokusdeskgame.Game;

import java.util.ArrayList;

/**
 * Created by lika85456 on 26.03.2018.
 */

public class Player {
    public byte color;

    private ArrayList<Piece> pieces;

    public boolean movesAvailable = true;
    public String name;

    public Player(Player player) {
        this.color = player.color;
        this.pieces = (ArrayList) player.pieces.clone();
        this.name = player.name;
    }

    //Just to carry color
    public Player(byte color) {
        this.color = color;
        pieces = Piece.getAllPieces(color);
    }

    public Player(int color, String name) {
        this.name = name;
        this.color = (byte) color;
        pieces = Piece.getAllPieces((byte) color);
       
    }

    public String getName() {
        return name;
    }

    public void iDidMove(Move move) {
        if (move != null)
            removePieceWithIndex(move.getPiece().index);
        return;
    }

    public void removePieceWithIndex(int index) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece toRemove = pieces.get(i);
            if (toRemove.index == index) {
                pieces.remove(toRemove);
                return;
            }
        }
    }
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

}
