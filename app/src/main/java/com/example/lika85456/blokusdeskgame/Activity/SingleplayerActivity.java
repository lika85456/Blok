package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Piece;
import com.example.lika85456.blokusdeskgame.Model.UI;
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

        final Board board = new Board();

        grid.setListner(zoomViewListener);
        final SquareGroupScrollView scrollView = findViewById(R.id.scrollView);
        LinearLayout consoleContainer = findViewById(R.id.console_container);
        final UI ui = new UI(grid, scrollView, consoleContainer) {
            @Override
            public void onPieceSelected(Piece piece) {
                Log.d("UIListener", "Piece selected");

            }

            @Override
            public void onMoveConfirm(int x, int y) {
                Log.d("UIListener", "Move confirmed");
                Piece piece = getSelectedPiece();
                Move move = new Move(board, piece, x, y);
                removeSquareGroupFromList(piece);
                setSelectedPiece(null);
                //board.move has to be after new move is created
                board.move(move);
                //TODO LOGIC WITH move
            }

            @Override
            public boolean isValid(Piece piece, int x, int y) {
                return board.isValid(piece, x, y);
            }
        };

        ui.gridView.board = board;
    }






    protected void onResume() {
        super.onResume();

    }
}
