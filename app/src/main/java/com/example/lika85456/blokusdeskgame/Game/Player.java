package com.example.lika85456.blokusdeskgame.Game;

import java.util.ArrayList;

/**
 * Created by lika85456 on 26.03.2018.
 */

public class Player {
    public byte color;

    private ArrayList<Piece> pieces;
    private ArrayList<Piece> usedPieces;

    public Player(Player player) {
        this.color = player.color;
        this.pieces = player.pieces;
        this.usedPieces = player.usedPieces;
    }

    public Player(byte color) {
        usedPieces = new ArrayList<Piece>();
        this.color = color;
        pieces = (ArrayList) Piece.groups.clone();
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).color = color;
        }
    }

    public void moveAdd(Move move) {
        pieces.remove(move.getPiece());
        usedPieces.add(move.getPiece());
    }

    public ArrayList<Piece> getUsedPieces() {
        return usedPieces;
    }
}
