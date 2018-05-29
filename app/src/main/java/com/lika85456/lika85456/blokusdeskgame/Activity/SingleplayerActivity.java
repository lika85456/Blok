package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.lika85456.lika85456.blokusdeskgame.EventLogger;
import com.lika85456.lika85456.blokusdeskgame.Game.AI;
import com.lika85456.lika85456.blokusdeskgame.Game.Board;
import com.lika85456.lika85456.blokusdeskgame.Game.Game;
import com.lika85456.lika85456.blokusdeskgame.Game.Move;
import com.lika85456.lika85456.blokusdeskgame.Game.Piece;
import com.lika85456.lika85456.blokusdeskgame.Game.Player;
import com.lika85456.lika85456.blokusdeskgame.Model.SinglePlayerGameHandler;
import com.lika85456.lika85456.blokusdeskgame.Model.UI;
import com.lika85456.lika85456.blokusdeskgame.R;
import com.lika85456.lika85456.blokusdeskgame.Views.EndGameDialog;
import com.lika85456.lika85456.blokusdeskgame.Views.GridView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SingleplayerActivity extends AppCompatActivity {

    public boolean[] player = new boolean[4]; //TRUE = AI, FALSE = USER
    private UI ui;
    private SinglePlayerGameHandler gameHandler;
    private InterstitialAd mInterstitialAd;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        Intent intent = getIntent();

        initializeAds();
        initializeGame(intent);

        final Board board = game.getBoard();
        gameHandler = new SinglePlayerGameHandler(game) {
            @Override
            public void onGameEnd(Game game) {

                Log.d("GameHandler", "OnGameEnd");
                //TODO add game ending
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showAd();
                    }
                });

                showEndDialog(board);


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
                if (move != null)
                    Log.d("GameHandler", "onMove: " + player.color + " move value: " + move.score);

            }

            @Override
            public void onMoving(final Player currentPlayer) {

                Log.d("GameHandler", "onMoving: " + currentPlayer.color);
                runOnUiThread(new Runnable() {
                    public void run() {
                        ui.onMoving(currentPlayer);
                        if (player[currentPlayer.color] == true) /*AI*/ {
                            AdView adView = findViewById(R.id.adView);
                            adView.setVisibility(VISIBLE);
                        } else {
                            AdView adView = findViewById(R.id.adView);
                            adView.setVisibility(GONE);
                        }
                    }
                });
                if (player[currentPlayer.color] == true) /*AI*/ {

                    final AI ai = new AI(1);

                    new Thread(new Runnable() {
                        public void run() {
                            gameHandler.move(currentPlayer, ai.think(gameHandler.game.getBoard(), currentPlayer, board.isStartMove(currentPlayer.color)));
                        }
                    }).start();

                } else {

                    AI ai = new AI(1);
                    if (!ai.hasPossibleMove(board, currentPlayer)) {
                        currentPlayer.hasMoves = false;
                        //no moves possible - skip
                        gameHandler.move(currentPlayer, null);
                    }
                }
            }

        };

        GridView gridView = findViewById(R.id.grid);
        gridView.board = board;

        ui = new UI(this, player) {
            @Override
            public void onPieceSelected(Piece piece) {
                Log.d("UIListener", "Piece selected");

            }

            @Override
            public void onMoveConfirm(int x, int y) {
                Log.d("UIListener", "Move confirmed");
                Piece piece = getSelectedPiece();
                Move move = new Move(piece, x, y);
                removeSquareGroupFromList(piece);
                setSelectedPiece(null);
                gameHandler.move(game.players[colorTurn], move);
            }

            @Override
            public boolean isValid(Piece piece, int x, int y) {
                return board.isValid(piece, x, y, board.isStartMove(piece.color));
            }
        };


        gameHandler.onMoving(gameHandler.game.getCurrentPlayer());
    }

    private void initializeGame(Intent intent) {
        Board board = new Board();
        Player[] players = new Player[4];

        players[0] = new Player(0, "Red");
        players[1] = new Player(1, "Green");
        players[2] = new Player(2, "Blue");
        players[3] = new Player(3, "Yellow");

        player[0] = intent.getBooleanExtra("P1", true);
        player[1] = intent.getBooleanExtra("P2", true);
        player[2] = intent.getBooleanExtra("P3", true);
        player[3] = intent.getBooleanExtra("P4", true);

        game = new Game(players);
        game.setBoard(board);
    }

    private void initializeAds() {
        //INTERESTIAL
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2326084372481940/5334232407");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        //BANNER
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    private void showEndDialog(final Board board) {
        final int[] score = new int[4];
        score[0] = board.getColorScore(0);
        score[1] = board.getColorScore(1);
        score[2] = board.getColorScore(2);
        score[3] = board.getColorScore(3);

        final Activity activity = this;
        runOnUiThread(new Runnable() {
            public void run() {
                EventLogger eventLogger = new EventLogger(activity);
                eventLogger.logGameEnd(score);
                EndGameDialog endGameDialog = new EndGameDialog(activity, score);
                endGameDialog.show();
            }
        });

    }


    private void showAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        } else {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }

                }

                @Override
                public void onAdOpened() {


                }

                @Override
                public void onAdFailedToLoad(int errorCode) {

                }
            });
        }
        ui.onEnd();
    }

    protected void onResume() {
        super.onResume();

    }
}
