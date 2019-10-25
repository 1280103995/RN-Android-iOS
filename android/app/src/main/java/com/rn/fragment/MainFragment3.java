package com.rn.fragment;

import android.os.Bundle;

import com.google.gson.Gson;
import com.rn.entity.RNRouteInfo;
import com.rn.react.ReactFragment;

public class MainFragment3 extends ReactFragment {

    @Override
    public Bundle getLaunchOptions() {
        RNRouteInfo route = new RNRouteInfo();
        route.setRouteName("MainTab");
        Bundle bundle = new Bundle();
        bundle.putString(RNRouteInfo.NATIVE_ROUTE_INFO, new Gson().toJson(route));
        return bundle;
    }
}
