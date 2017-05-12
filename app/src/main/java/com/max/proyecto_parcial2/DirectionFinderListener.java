package com.max.proyecto_parcial2;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import com.max.proyecto_parcial2.Route;


public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
