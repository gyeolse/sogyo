//DriverInfoAdapter.java
/*
package com.lgj.sogyo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DriverInfoAdapter implements GoogleMap.InfoWindowAdapter {
    public View window;
    public MyItem myItem;

    public DriverInfoAdapter(View window, MyItem myItem){
        this.window = window;
        this.myItem = myItem;
    }

    //BizName, upperCategory, floor, IsOpen, copenYear, closeYear,
    //마커를 눌렀을 때 생성할 레이아웃 view를 리턴함.
    //둘 중 하나가 null을 리턴하면 나머지가 실행되는 구조
    @Override
    public View getInfoWindow(Marker marker) {
        TextView BizNameTextView = window.findViewById(R.id.BizName);
        TextView categoryTextView = window.findViewById(R.id.category);
        TextView floorTextView = window.findViewById(R.id.floor);
        TextView IsOpenTextView = window.findViewById(R.id.IsOpen);
        TextView openYearTextView = window.findViewById(R.id.openYear);
        TextView closeYearTextView = window.findViewById(R.id.closeYear);
        BizNameTextView.setText(myItem.BizName);
        categoryTextView.setText(myItem.Category);
        floorTextView.setText(myItem.floor);
        IsOpenTextView.setText(myItem.IsOpen);
        openYearTextView.setText(myItem.openYear);
        closeYearTextView.setText(myItem.closeYear);

        System.out.println("DriverInfoAdapter "+ myItem.BizName);
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
//////////////////////////////////////////////////////////////////
/*
package com.lgj.sogyo;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DriverInfoAdapter implements GoogleMap.InfoWindowAdapter {
    public final View markerItemView;
//    public final Store mystore;
    public DriverInfoAdapter(View window){
        this.markerItemView = window;
//        this.mystore = store;
    }

    //마커를 눌렀을 때 생성할 레이아웃 view를 리턴함.
    //둘 중 하나가 null을 리턴하면 나머지가 실행되는 구조
    //BizName, upperCategory, floor, IsOpen, copenYear, closeYear,
    @Override
    public View getInfoWindow(Marker marker) {
/*
        TextView BizNameTextView = markerItemView.findViewById(R.id.storeName);
        TextView upperCategoryTextView = markerItemView.findViewById(R.id.category);
        TextView floorTextView = markerItemView.findViewById(R.id.floor);
        TextView IsOpenTextView = markerItemView.findViewById(R.id.IsOpen);
        TextView openYearTextView = markerItemView.findViewById(R.id.openYear);
        TextView closeYearTextView = markerItemView.findViewById(R.id.closeYear);

        BizNameTextView.setText(mystore.getBizName());
        upperCategoryTextView.setText(mystore.getLowerCategory());
        floorTextView.setText(mystore.getFloor());
        IsOpenTextView.setText(mystore.getIsOpen());
        openYearTextView.setText(mystore.getOpenYear());
        closeYearTextView.setText(mystore.getCloseYear());
        return markerItemView;
    }
    public View getInfoContents(Marker marker) {
        return null;
    }
}
*/