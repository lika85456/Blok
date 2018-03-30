package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.Piece;
import com.example.lika85456.blokusdeskgame.Listeners.UIListener;
import com.example.lika85456.blokusdeskgame.Model.UIHandler;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareGroupScrollView;
import com.example.lika85456.blokusdeskgame.Views.ZoomView;

public class SingleplayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);
        final GridView grid = findViewById(R.id.grid);
        grid.setMaxZoom(6.f);

        ZoomView.ZoomViewListener zoomViewListener = new ZoomView.ZoomViewListener() {
            @Override
            public void onZoomStarted(float zoom, float zoomx, float zoomy) {
            }

            @Override
            public void onZooming(float zoom, float zoomx, float zoomy) {
            }

            @Override
            public void onZoomEnded(float zoom, float zoomx, float zoomy) {
            }
        };

        Board board = new Board();

        grid.setListner(zoomViewListener);
        final SquareGroupScrollView scrollView = findViewById(R.id.scrollView);
        LinearLayout consoleContainer = findViewById(R.id.console_container);
        final UIHandler uiHandler = new UIHandler(grid, scrollView, consoleContainer);
        UIListener uiListener = new UIListener() {

            @Override
            public void onPieceSelected(Piece piece) {
                Log.d("UIListener", "Piece selected");

            }

            @Override
            public void onMoveConfirm(int x, int y) {
                Log.d("UIListener", "Move confirmed");
                Piece piece = uiHandler.getSelectedPiece();
            }
        };

        uiHandler.setUiListener(uiListener);
        uiHandler.gridView.board = board;
    }






    protected void onResume() {
        super.onResume();

    }
}
