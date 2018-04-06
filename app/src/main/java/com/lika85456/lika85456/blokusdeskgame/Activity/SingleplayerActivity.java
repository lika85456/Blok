package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.lika85456.lika85456.blokusdeskgame.Game.AI;
import com.lika85456.lika85456.blokusdeskgame.Game.Board;
import com.lika85456.lika85456.blokusdeskgame.Game.Game;
import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Piece;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;
import com.lika85456.lika85456.blokusdeskgame.Model.SinglePlayerGameHandler;
import com.lika85456.lika85456.blokusdeskgame.Model.UI;
import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Views.GridView;
import com.lika85456.lika85456.blokusdeskgame.Views.SquareGroupScrollView;

public class SingleplayerActivity extends AppCompatActivity {

    public UI ui;
    public SinglePlayerGameHandler gameHandler;

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

        String MY_NAME = "You";
        final Player user = new Player(MY_COLOR, MY_NAME);
        Player[] players = new Player[4];
        for (int i = 0; i < 4; i++) {
            if (i == MY_COLOR) {
                players[i] = user;
            } else
                players[i] = new Player((byte) i, "Bot #" + i);
        }

        final Game game = new Game(players);
        game.setBoard(board);

        gameHandler = new SinglePlayerGameHandler(game) {
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

            @Override
            public void onMoving(Player player) {
                ui.onMoving(player);
                if (player != user) {
                    AI ai = new AI(1);
                    gameHandler.move(player, ai.think(gameHandler.game.board, player, board.isStartMove(player.color)));
                }
            }

        };

        ui = new UI(grid, scrollView, consoleContainer, user) {
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
                gameHandler.move(user, move);
                //TODO LOGIC WITH move
            }

            @Override
            public boolean isValid(Piece piece, int x, int y) {
                return board.isValid(piece, x, y, board.isStartMove(piece.color));
            }
        };

        ui.gridView.board = board;
        gameHandler.onMoving(gameHandler.game.getCurrentPlayer());
    }






    protected void onResume() {
        super.onResume();

    }
}
