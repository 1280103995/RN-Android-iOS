package com.rn.entity;

import androidx.collection.ArrayMap;

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
}
