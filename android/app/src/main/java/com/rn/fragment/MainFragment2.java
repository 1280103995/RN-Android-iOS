package com.rn.fragment;

import android.os.Bundle;

import com.rn.react.ReactFragment;

public class MainFragment2 extends ReactFragment {


    @Override
    public Bundle getLaunchOptions() {
        Bundle bundle = new Bundle();
        bundle.putInt("entrance", 5);
        return bundle;
    }

}
