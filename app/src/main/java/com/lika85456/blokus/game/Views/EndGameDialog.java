package com.lika85456.blokus.game.Views;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lika85456.blokus.R;
import com.lika85456.blokus.game.Activity.MainActivity;
import com.lika85456.blokus.game.Activity.SinglePlayerChooserActivity;

public class EndGameDialog extends Dialog implements View.OnClickListener {

    private Activity c;
    private Button menuButton, playAgainButton;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;

    private int[] score;

    public EndGameDialog(Activity a, int[] score) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.score = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.end_game_dialog);
        menuButton = findViewById(R.id.menuButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        menuButton.setOnClickListener(this);
        playAgainButton.setOnClickListener(this);

        t1 = findViewById(R.id.dialogScore1);
        t2 = findViewById(R.id.dialogScore2);
        t3 = findViewById(R.id.dialogScore3);
        t4 = findViewById(R.id.dialogScore4);

        t1.setText(String.valueOf(score[0]));
        t2.setText(String.valueOf(score[1]));
        t3.setText(String.valueOf(score[2]));
        t4.setText(String.valueOf(score[3]));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuButton:
                //MENU BUTTON
                Intent intent = new Intent(c, MainActivity.class);
                c.startActivity(intent);
                break;
            case R.id.playAgainButton:
                //PLAY AGAIN BUTTON

                Intent intent2 = new Intent(c, SinglePlayerChooserActivity.class);
                c.startActivity(intent2);
                break;
            default:
                break;
        }
        dismiss();
    }


}