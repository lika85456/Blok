package com.lika85456.lika85456.blokusdeskgame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.lika85456.lika85456.blokusdeskgame.EventLogger;
import com.lika85456.lika85456.blokusdeskgame.R;

public class SinglePlayerChooserActivity extends AppCompatActivity {

    private EventLogger eventLogger;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_single_player_chooser);
        eventLogger = new EventLogger(this);
        Button continueButton = findViewById(R.id.singleplayer_chooser_continue_button);
        continueButton.getBackground().mutate();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinue();
            }
        });

        seekBar = findViewById(R.id.seekBar);
        final TextView timeTextView = findViewById(R.id.timeTextView);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timeTextView.setText(i + 100 + "ms");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void onContinue() {
        Intent intent = new Intent(this, SingleplayerActivity.class);

        intent.putExtra("P1", ((Switch) findViewById(R.id.switch1)).isChecked());
        intent.putExtra("P2", ((Switch) findViewById(R.id.switch2)).isChecked());
        intent.putExtra("P3", ((Switch) findViewById(R.id.switch3)).isChecked());
        intent.putExtra("P4", ((Switch) findViewById(R.id.switch4)).isChecked());
        intent.putExtra("TIME", seekBar.getProgress() + 100);
        intent.putExtra("BACK", ((CheckBox) findViewById(R.id.checkBox)).isChecked());

        eventLogger.logNewGame(
                intent.getBooleanExtra("P1", false),
                intent.getBooleanExtra("P2", false),
                intent.getBooleanExtra("P3", false),
                intent.getBooleanExtra("P4", false));

        startActivity(intent);
    }


}
