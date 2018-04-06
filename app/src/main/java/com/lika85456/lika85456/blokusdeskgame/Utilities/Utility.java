package com.lika85456.lika85456.blokusdeskgame.Utilities;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by lika85456 on 08.03.2018.
 */

public class Utility {

    public static int convertDpToPixels(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static void rotateMatrix(byte[][] matrix) {
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

}
