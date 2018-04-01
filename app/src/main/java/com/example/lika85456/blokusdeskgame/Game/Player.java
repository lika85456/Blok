package com.example.lika85456.blokusdeskgame.Game;

import java.util.ArrayList;

/**
 * Created by lika85456 on 26.03.2018.
 */

public class Player {
    public byte color;

    private ArrayList<Piece> pieces;
    private ArrayList<Piece> usedPieces;

    public boolean movesAvailable = true;
    public String name;

    public Player(Player player) {
        usedPieces = (ArrayList) player.usedPieces.clone();
        this.color = player.color;
        this.pieces = (ArrayList) player.pieces.clone();
        this.name = player.name;
    }

    public Player(byte color, String name) {
        this.name = name;
        this.color = color;
        pieces = (ArrayList) Piece.groups.clone();
        usedPieces = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i++) {
            pieces.get(i).color = color;
        }
    }

    public void moveAdd(Move move) {
        pieces.remove(move.getPiece());
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public ArrayList<Piece> getUsedPieces() {
        return usedPieces;
    }
}
