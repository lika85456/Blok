package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

public class SingleplayerActivity extends AppCompatActivity {

    public UI ui;
    public SinglePlayerGameHandler gameHandler;
    public byte MY_COLOR;
    public byte[] difficulties = new byte[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_singleplayer);

        Intent intent = getIntent();
        Log.d("DEBUG",intent.getStringExtra("MY_COLOR"));
        MY_COLOR = Byte.parseByte(intent.getStringExtra("MY_COLOR"));



        final String MY_NAME = "You";

        final Board board = new Board();

        final Player user = new Player(MY_COLOR, MY_NAME);
        Player[] players = new Player[4];
        int tI = 1;
        for (int i = 0; i < 4; i++) {
            if (i == MY_COLOR) {
                players[i] = user;
            } else
            {
                players[i] = new Player((byte) i, "Bot #" + i);
                difficulties[i] = Byte.parseByte(intent.getStringExtra("D"+tI));
                tI++;
            }

        }

        final Game game = new Game(players);
        game.setBoard(board);


        gameHandler = new SinglePlayerGameHandler(game) {
            @Override
            public void onGameEnd(Game game) {

                Log.d("GameHandler", "OnGameEnd");
                //TODO add game ending
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ui.onEnd();
                    }
                });
            }

            @Override
            public void noMoves(Player player) {
                Log.d("GameHandler", "NoMoves:" + player.name);
            }

            @Override
            public void onMove(final Player player, final Move move) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ui.onMove(player, move);
                    }
                });
                Log.d("GameHandler", "onMove: "+player.color);
            }

            @Override
            public void onMoving(final Player player) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        ui.onMoving(player);
                    }
                });
                Log.d("GameHandler", "onMoving: "+player.color);

                if (player != user) {
                    final AI ai = new AI(difficulties[player.color]);

                    new Thread(new Runnable() {
                        public void run() {
                            gameHandler.move(player, ai.think(gameHandler.game.board, player, board.isStartMove(player.color)));
                        }
                    }).start();

                } else {
                    AI ai = new AI(1);
                    if (!ai.hasPossibleMove(board, user)) {
                        //no moves possible - skip
                        gameHandler.move(user, null);
                    }
                }
            }

        };

        GridView gridView = findViewById(R.id.grid);
        gridView.board = board;

        ui = new UI(this, user) {
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


        gameHandler.onMoving(gameHandler.game.getCurrentPlayer());

    }






    protected void onResume() {
        super.onResume();

    }
}
