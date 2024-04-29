package com.lika85456.blokus.game;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class EventLogger {
    private FirebaseAnalytics mFirebaseAnalytics;

    public EventLogger(Context context) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public void logStart() {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "AppStart");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void logNewGame(boolean i, boolean i1, boolean i2, boolean i3) {
        String toSave = "";
        if (i) toSave += "1";
        else toSave += "0";
        if (i1) toSave += "1";
        else toSave += "0";
        if (i2) toSave += "1";
        else toSave += "0";
        if (i3) toSave += "1";
        else toSave += "0";
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GameStart");
        bundle.putString(FirebaseAnalytics.Param.CONTENT, toSave);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void logGameEnd(int[] score) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "GameEnd");
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, score[0] + " " + score[1] + " " + score[2] + " " + score[3]);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
