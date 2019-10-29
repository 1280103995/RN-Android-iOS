package com.rn.entity;

import android.os.Bundle;

import androidx.collection.ArrayMap;

import com.google.gson.Gson;

import java.io.Serializable;

public class RNRouteInfo implements Serializable {

    public static final String NATIVE_ROUTE_INFO = "NativeRouteInfo";

    private String routeName;
    private ArrayMap routeParams;

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public ArrayMap getRouteParams() {
        return routeParams;
    }

    public void setRouteParams(ArrayMap routeParams) {
        this.routeParams = routeParams;
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putString(RNRouteInfo.NATIVE_ROUTE_INFO, new Gson().toJson(this));
        return bundle;
    }
}
