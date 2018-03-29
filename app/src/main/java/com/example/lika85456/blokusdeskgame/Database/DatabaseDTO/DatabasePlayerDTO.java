package com.example.lika85456.blokusdeskgame.Database.DatabaseDTO;

/**
 * Created by lika85456 on 27.03.2018.
 */

public class DatabasePlayerDTO {
    public int id;
    public byte color;
    public int score;

    public DatabasePlayerDTO() {

    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

    public byte getColor() {
        return color;
    }
}
