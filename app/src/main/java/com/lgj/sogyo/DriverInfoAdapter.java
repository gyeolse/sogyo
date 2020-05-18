package com.lgj.sogyo;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DriverInfoAdapter implements GoogleMap.InfoWindowAdapter {
    View window;

    public DriverInfoAdapter(View window){
        this.window = window;
    }

    //마커를 눌렀을 때 생성할 레이아웃 view를 리턴함.
    //둘 중 하나가 null을 리턴하면 나머지가 실행되는 구조
    @Override
    public View getInfoWindow(Marker marker) {
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
