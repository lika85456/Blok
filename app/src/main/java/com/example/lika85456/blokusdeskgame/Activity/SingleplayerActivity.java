package com.example.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.lika85456.blokusdeskgame.Game.Board;
import com.example.lika85456.blokusdeskgame.Game.ComputerPlayer;
import com.example.lika85456.blokusdeskgame.Game.Game;
import com.example.lika85456.blokusdeskgame.Game.Move;
import com.example.lika85456.blokusdeskgame.Game.Piece;
import com.example.lika85456.blokusdeskgame.Game.Player;
import com.example.lika85456.blokusdeskgame.Model.GameHandler;
import com.example.lika85456.blokusdeskgame.Model.UI;
import com.example.lika85456.blokusdeskgame.R;
import com.example.lika85456.blokusdeskgame.Views.GridView;
import com.example.lika85456.blokusdeskgame.Views.SquareGroupScrollView;

public class SingleplayerActivity extends AppCompatActivity {

    public UI ui;
    public GameHandler gameHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);
        final GridView grid = findViewById(R.id.grid);

        final Board board = new Board();

        final SquareGroupScrollView scrollView = findViewById(R.id.scrollView);
        LinearLayout consoleContainer = findViewById(R.id.console_container);

        byte MY_COLOR = 1;

        for (int i = 0; i < Piece.groups.size(); i++) {
            Piece.groups.get(i).color = MY_COLOR;
        }

        String MY_NAME = "You";
        final Player user = new Player(MY_COLOR, MY_NAME);
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++) {
            if (i == MY_COLOR) {
                players[i] = user;
            } else
                players[i] = new ComputerPlayer((byte) i);
        }

        final Game game = new Game(players);
        game.setBoard(board);

        gameHandler = new GameHandler(game) {
            @Override
            public void onGameEnd(Game game) {
                Log.d("GameHandler", "OnGameEnd");
            }

            @Override
            public void noMoves(Player player) {
                Log.d("GameHandler", "NoMoves:" + player.name);
            }

            @Override
            public void onMove(Player player, Move move) {
                ui.onMove(player, move);
            }

        };

        ui = new UI(grid, scrollView, consoleContainer) {
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
        gameHandler.askForMove();
    }






    protected void onResume() {
        super.onResume();

    }
}
