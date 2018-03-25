package com.example.lika85456.blokusdeskgame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lika85456.blokusdeskgame.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_main);

        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);
        Button multiPlayerButton = findViewById(R.id.multiPlayerButton);

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSinglePlayer();
            }
        });


        multiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMultiPlayer();
            }
        });

    }

    private void onSinglePlayer() {
        Intent intent = new Intent(this, SinglePlayerChooserActivity.class);
        startActivity(intent);
    }

    private void onMultiPlayer() {
        //Intent intent = new Intent(this,M.class);
        //startActivity(intent);
    }


}
