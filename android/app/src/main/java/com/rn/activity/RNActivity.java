package com.rn.activity;

import android.os.Bundle;

import com.rn.react.ReactActivity;

public class RNActivity extends ReactActivity {

    @Override
    public Bundle getLaunchOptions() {
        return getIntent().getExtras();
    }
}
