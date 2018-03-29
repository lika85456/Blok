package com.example.lika85456.blokusdeskgame.Game;

import android.graphics.Point;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class Piece {
    public static final ArrayList<Piece> groups;

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

    public Piece(Point... params)
    {
        this.list = new ArrayList<>();
        for(int i = 0;i<params.length;i++)
        {
            this.list.add(params[i]);
        }
    }

    public Piece(Piece piece) {
        this.list = (ArrayList<Point>) piece.list.clone();
        this.color = piece.color;

    }

    /***
     * calculates the middle point of mass
     * @return the middle point of mass
     */
    public PointF mass()
    {
        float x=0;
        float y=0;
        for(int i = 0;i<list.size();i++)
        {
            Point temp = list.get(i);
            x += temp.x;
            y += temp.y;
        }
        x/=list.size();
        y/=list.size();
        return new PointF(x,y);
    }

    /***
     * returns true if it can be placed on clear board (false if its not all in the board)
     * @param x
     * @param y
     * @return validity of position
     */
    public boolean validPositionOnTheBoard(int x,int y)
    {
        for(int i = 0;i<list.size();i++)
        {
            Point temp = list.get(i);
            int tX = temp.x;
            int tY = temp.y;
            if(tX+x>19 || tX+x<0) return false;
            if(tY+y>19 || tY+y<0) return false;
        }
        return true;
    }

    /***
     * Converts this.list (ArrayList) of points to 2D array (to be rotated)
     * @return 2D array
     */
    private byte[][] toArray()
    {
        byte[][] toRet = new byte[5][5];
        //filling array
        for(int x = 0;x<5;x++)
            for(int y = 0;y<5;y++)
                toRet[x][y] = 0;

        //adding list points to the array
        for(int i = 0;i<list.size();i++)
        {
            Point temp = list.get(i);
            toRet[temp.x][temp.y] = 1;
        }
        return toRet;
    }

    /***
     * Rotates whole piece by 90 degree
     */
    public void rotateBy90()
    {
        byte[][] arr = toArray();
        byte[][] newArray = new byte[5][5];
        //filling array
        for(int x = 0;x<5;x++)
            for(int y = 0;y<5;y++)
                newArray[x][y] = 0;

        for(int i=0; i<arr[0].length; i++){
            for(int j=arr.length-1; j>=0; j--){
                newArray[i][j] = arr[j][i];
            }
        }

        this.list = new ArrayList<>();
        for(int x = 0;x<5;x++)
            for(int y = 0;y<5;y++)
                if(newArray[x][y]==1)
                    list.add(new Point(x,y));
    }


}

/*
		String toParse = "2,2\n"
				+ "2,2,3,2\n"
				+ "1,2,2,2,3,2\n"
				+ "1,2,2,2,2,3\n"
				+ "0,2,1,2,2,2,3,2\n"
				+ "1,2,2,2,3,2,3,3\n"
				+ "2,1,1,2,2,2,3,2\n"
				+ "1,1,2,1,1,2,2,2\n"
				+ "1,1,1,2,2,2,2,3\n"
				+ "1,1,1,2,2,2,2,3,2,4\n"
				+ "2,1,1,2,2,2,3,2,3,3\n"
				+ "2,1,2,2,1,3,2,3,3,3\n"
				+ "2,1,1,2,2,2,3,2,2,3\n"
				+ "3,1,3,2,3,3,2,3,1,3\n"
				+ "3,1,1,2,2,2,3,2,4,2\n"
				+ "1,1,1,2,2,2,2,3,3,3\n"
				+ "1,1,2,1,1,2,2,2,3,2\n"
				+ "3,1,1,2,2,2,3,2,1,3\n"
				+ "1,1,3,1,1,2,2,2,3,2\n"
				+ "3,1,0,2,1,2,2,2,3,2\n"
				+ "0,2,1,2,2,2,3,2,4,2";

		String[] splited = toParse.split("\n");

		for(int i =0;i<splited.length;i++)
		{

			String[] pointSplited = splited[i].split(",");
			String pointString = "new Point("+pointSplited[0]+","+pointSplited[1]+")";
			for(int x=2;x<pointSplited.length;x+=2)
			{
				pointString+=",new Point("+pointSplited[x]+","+pointSplited[x+1]+")";
			}
			System.out.println("groups.add(new Piece("+pointString+"));");
		}
 */