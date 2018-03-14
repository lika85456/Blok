package com.example.lika85456.blokusdeskgame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lika85456.blokusdeskgame.ColorSliderFragment;
import com.example.lika85456.blokusdeskgame.R;

public class SinglePlayerChooserActivity extends AppCompatActivity {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 4;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utility.hideTopBar(this);
        setContentView(R.layout.activity_single_player_chooser);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.colorPager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        Button continueButton = findViewById(R.id.singleplayer_chooser_continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onContinue();
            }
        });
    }

    private void onContinue() {
        Intent intent = new Intent(this, SingleplayerActivity.class);
        intent.putExtra("playerColor", mPager.getCurrentItem());
        startActivity(intent);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ColorSliderFragment toRet = new ColorSliderFragment();
            toRet.position(position);
            return toRet;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
