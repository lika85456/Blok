package com.lika85456.lika85456.blokusdeskgame.Game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lika85456 on 26.03.2018.
 */

public class Player {
    public byte color;

    private ArrayList<Piece> pieces;
    public boolean hasMoves = true;
    public String name;

    public Player(int color, String name) {
        this.name = name;
        this.color = (byte) color;
        pieces = Piece.getAllPieces((byte) color);
        Collections.reverse(pieces);
       
    }

    public String getName() {
        return name;
    }

    public void onMove(Move move) {
        if (move != null)
            removePieceWithIndex(move.getPiece().index);
    }

    private void removePieceWithIndex(int index) {
        for (int i = 0; i < pieces.size(); i++) {
            Piece toRemove = pieces.get(i);
            if (toRemove.index == index) {
                pieces.remove(toRemove);
                return;
            }
        }
    }
    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> toRet = new ArrayList<>();
        for (Piece piece : pieces) {
            toRet.add(new Piece(piece));
        }
        return toRet;
    }


}
