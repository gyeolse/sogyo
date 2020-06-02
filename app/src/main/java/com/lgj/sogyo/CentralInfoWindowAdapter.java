package com.lgj.sogyo;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

//getID에 따른 InfoWindowAdapter들로 이루어진 adapterMap
public class CentralInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Map<String, GoogleMap.InfoWindowAdapter> adapterMap;

    public CentralInfoWindowAdapter(Map<String, GoogleMap.InfoWindowAdapter> adapterMap){
        this.adapterMap = adapterMap;
    }

    @Override
    public View getInfoContents(Marker marker){
        GoogleMap.InfoWindowAdapter adapter = adapterMap.get(marker.getId());
        return adapter.getInfoContents(marker);
    }

    @Override
    public View getInfoWindow(Marker marker){
        GoogleMap.InfoWindowAdapter adapter = adapterMap.get(marker.getId());
        return adapter.getInfoWindow(marker);
    }
}
