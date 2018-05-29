package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.lika85456.lika85456.blokusdeskgame.EventLogger;
import com.lika85456.lika85456.blokusdeskgame.R;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventLogger eventLogger = new EventLogger(this);
        eventLogger.logStart();

        MobileAds.initialize(this, "ca-app-pub-2326084372481940~5170580875");

        Button singlePlayerButton = findViewById(R.id.singlePlayerButton);
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSinglePlayer();
            }
        });



    }

    private void onSinglePlayer() {
        Intent intent = new Intent(this, SinglePlayerChooserActivity.class);
        startActivity(intent);
    }


}
