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

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SingleplayerActivity extends AppCompatActivity {

    public boolean[] player = new boolean[4]; //TRUE = AI, FALSE = USER
    private UI ui;
    private SinglePlayerGameHandler gameHandler;
    private InterstitialAd mInterstitialAd;
    private Game game;
    private AI ai;
    private ArrayList<Game> states;
    private boolean saveStates = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);

        Intent intent = getIntent();

        initializeAds();
        initializeGame(intent);

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

                showEndDialog(game.getBoard());


            }

            @Override
            public void noMoves(Player player) {
                Log.d("GameHandler", "NoMoves:" + player.name);
            }

            @Override
            public void onMove(final Player currentPlayer, final Move move) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        ui.onMove(currentPlayer, move);
                    }
                });
                if (move != null)
                    Log.d("GameHandler", "onMove: " + currentPlayer.color + " move value: " + move.score);

                if (player[Game.getNextPlayerId(currentPlayer.color + 2)] == false)
                    if (saveStates)
                        states.add(game.getState());
            }

            @Override
            public void onMoving(final Player currentPlayer) {

                Log.d("GameHandler", "onMoving: " + currentPlayer.color);

                if (player[currentPlayer.color] == true)/*AI*/ {
                    //let AI make a move
                    new Thread(new Runnable() {
                        public void run() {
                            gameHandler.move(currentPlayer, ai.think(gameHandler.game.getBoard(), currentPlayer, game.getBoard().isStartMove(currentPlayer.color)));
                        }
                    }).start();
                } else {
                    //No moves - skip
                    if (!ai.hasPossibleMove(game.getBoard(), currentPlayer)) {
                        currentPlayer.hasMoves = false;

                        gameHandler.move(currentPlayer, null);
                    } else if (player[currentPlayer.color] == false) {

                        if (saveStates)
                            Log.d("State", "Saving state, size: " + states.size());
                    }

                }

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (currentPlayer.hasMoves) {


                            ui.onMoving(currentPlayer);
                            if (player[currentPlayer.color] == true) /*AI*/ {

                                //turn on ads while user is waiting
                                AdView adView = findViewById(R.id.adView);
                                adView.setVisibility(VISIBLE);

                            } else {
                                //Turn off ads to not fuck user
                                AdView adView = findViewById(R.id.adView);
                                adView.setVisibility(GONE);


                            }
                        }
                    }
                });
            }

        };

        GridView gridView = findViewById(R.id.grid);
        gridView.board = game.getBoard();

        ui = new UI(this, player, game) {
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
                Board board = game.getBoard();
                return board.isValid(piece, x, y, board.isStartMove(piece.color));
            }
        };


        gameHandler.onMoving(gameHandler.game.getCurrentPlayer());
        if (saveStates)
            states.add(game.getState());
    }

    public void backState() {
        Log.d("STEB BACK", "STEP BACK");

        if (states.size() > 0) {
            this.game = states.get(states.size() - 1);
            if (states.size() > 1)
                states.remove(states.size() - 1);
            ui.refresh(game);
            gameHandler.game = game;
        }

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

        ai = new AI(intent.getIntExtra("TIME", 1000));
        saveStates = intent.getBooleanExtra("BACK", false);
        //if saveStates then init, otherwise hide back button
        if (saveStates)
            states = new ArrayList<>();
        else
            findViewById(R.id.backButton).setVisibility(GONE);

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
