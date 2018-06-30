package com.lika85456.lika85456.blokusdeskgame.Views;

/**
 * Created by lika85456 on 24.03.2018.
 */

public class SquareColor {

    public static final byte UNKNOWN = -2;
    public static final byte BLANK = -1;
    public static final byte RED = 0;
    public static final byte GREEN = 1;
    public static final byte BLUE = 2;
    public static final byte YELLOW = 3;

    public static int getColorFromCode(byte color) {
        switch (color) {
            case -1:
                return 0xFF303030;
            case RED:
                return 0xCCFF0000;
            case GREEN:
                return 0xCC00FF00;
            case BLUE:
                return 0xCC1111EE;
            case YELLOW:
                return 0xCCFFFF00;
            default:
                return 0xCCFF0000;
        }

    }

}
