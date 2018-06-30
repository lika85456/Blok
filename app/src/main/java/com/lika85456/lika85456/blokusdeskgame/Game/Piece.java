package com.lika85456.lika85456.blokusdeskgame.Game;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class Piece implements Comparable<Piece> {
    private static final ArrayList<Piece> groups;
    private static int lastIndex = 0;

    static {
        groups = new ArrayList<>(21);
        groups.add(new Piece(new Point(2, 2)));
        groups.add(new Piece(new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(1, 2), new Point(2, 2), new Point(2, 3)));
        groups.add(new Piece(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(3, 3)));
        groups.add(new Piece(new Point(2, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(1, 1), new Point(2, 1), new Point(1, 2), new Point(2, 2)));
        groups.add(new Piece(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 3)));
        groups.add(new Piece(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 3), new Point(2, 4)));
        groups.add(new Piece(new Point(2, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(3, 3)));
        groups.add(new Piece(new Point(2, 1), new Point(2, 2), new Point(1, 3), new Point(2, 3), new Point(3, 3)));
        groups.add(new Piece(new Point(2, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(2, 3)));
        groups.add(new Piece(new Point(3, 1), new Point(3, 2), new Point(3, 3), new Point(2, 3), new Point(1, 3)));
        groups.add(new Piece(new Point(3, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2)));
        groups.add(new Piece(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 3), new Point(3, 3)));
        groups.add(new Piece(new Point(1, 1), new Point(2, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(3, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(1, 3)));
        groups.add(new Piece(new Point(1, 1), new Point(3, 1), new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(3, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2)));
        groups.add(new Piece(new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(3, 2), new Point(4, 2)));

    }

    public ArrayList<Point> list;
    public byte color;
    public ArrayList<Point> seeds; //List of points which can be placed on seed (wtf?)
    public int index;

    public Piece(Piece piece) {
        this.list = new ArrayList<>();
        for (Point point : piece.list)
            list.add(new Point(point));
        this.color = piece.color;
        this.index = piece.index;
        this.seeds = generateSeeds();
    }

    public Piece(Point... params) {
        this.list = new ArrayList<>();
        Collections.addAll(this.list, params);
        this.index = lastIndex;
        lastIndex++;
        this.seeds = generateSeeds();
    }

    public static ArrayList<Piece> getAllPieces(byte color) {
        ArrayList<Piece> toRet = new ArrayList<>();
        for (Piece toCopy : groups) {
            Piece temp = new Piece(toCopy);
            temp.color = color;
            toRet.add(temp);

        }
        return toRet;
    }

    private static void rotateMatrix(byte[][] matrix) {
        if (matrix == null)
            return;
        if (matrix.length != matrix[0].length)//INVALID INPUT
            return;
        getTranspose(matrix);
        rorateAlongMidRow(matrix);
    }

    private static void getTranspose(byte[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                byte temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    private static void rorateAlongMidRow(byte[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len / 2; i++) {
            for (int j = 0; j < len; j++) {
                byte temp = matrix[i][j];
                matrix[i][j] = matrix[len - 1 - i][j];
                matrix[len - 1 - i][j] = temp;
            }
        }
    }

    public Piece clone() {
        Piece piece = new Piece(this);
        return piece;
    }

    private ArrayList<Point> generateSeeds() {
        ArrayList<Point> points = new ArrayList<>();
        for (Point point : this.list) {
            if (isSeed(point.x - 1, point.y - 1) ||
                    isSeed(point.x + 1, point.y - 1) ||
                    isSeed(point.x + 1, point.y + 1) ||
                    isSeed(point.x - 1, point.y + 1)) points.add(point);

        }
        return points;
    }

    private boolean isSeed(int x, int y) {
        Point point = new Point(x, y);
        return (isPiece(point.x - 1, point.y - 1) == true ||
                isPiece(point.x + 1, point.y + 1) == true ||
                isPiece(point.x + 1, point.y - 1) == true ||
                isPiece(point.x - 1, point.y + 1) == true) &&
                (isPiece(point.x - 1, point.y) != true &&
                        isPiece(point.x + 1, point.y) != true &&
                        isPiece(point.x, point.y - 1) != true &&
                        isPiece(point.x, point.y + 1) != true);
    }

    private boolean isPiece(int x, int y) {
        for (Point piece : list) {
            if (x == piece.x && y == piece.y) return true;
        }
        return false;
    }

    public void flip() {
        byte[][] array = toArray();
        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < array[j].length / 2; i++) {
                int temp = array[j][i];
                array[j][i] = array[j][array[j].length - i - 1];
                array[j][array[j].length - i - 1] = (byte) temp;
            }
        }

        this.list = new ArrayList<>();
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                if (array[x][y] == 1)
                    list.add(new Point(x, y));
        seeds = generateSeeds();

    }

    /***
     * Converts this.list (ArrayList) of points to 2D array (to be rotated)
     * @return 2D array
     */
    private byte[][] toArray() {
        byte[][] toRet = new byte[5][5];
        //filling array
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                toRet[x][y] = 0;

        //adding list points to the array
        for (int i = 0; i < list.size(); i++) {
            Point temp = list.get(i);
            toRet[temp.x][temp.y] = 1;
        }
        return toRet;
    }

    public ArrayList<Point> getSquares() {
        return this.list;
    }

    /***
     * calculates the middle point of mass
     * @return the middle point of mass
     */
    public PointF getMass() {
        float x = 0;
        float y = 0;
        for (int i = 0; i < list.size(); i++) {
            Point temp = list.get(i);
            x += temp.x;
            y += temp.y;
        }
        x /= list.size();
        y /= list.size();
        return new PointF(x, y);
    }

    /***
     * Rotates whole piece by 90 degree
     */
    public void rotateBy90() {
        byte[][] arr = toArray();
        rotateMatrix(arr);


        this.list = new ArrayList<>();
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 5; y++)
                if (arr[x][y] == 1)
                    list.add(new Point(x, y));
        seeds = generateSeeds();
    }

    public String toString() {
        String toRet = "";
        byte[][] array = toArray();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++)
                toRet += array[x][y] == 0 ? 0 : 1;
            toRet += "\n";
        }
        return toRet;
    }


    @Override
    public int compareTo(@NonNull Piece o) {
        return this.list.size() - o.list.size();
    }
}
