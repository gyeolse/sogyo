package com.lgj.sogyo;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterItem;

import java.util.Locale;

// BizName, upperCategory, floor, IsOpen, copenYear, closeYear,
public class MyItem implements ClusterItem {
    public final LatLng mPosition; //LatLng형 mPosition 객체
    public final String cnt;

    //    public final String BizName;
//    public final String Category;
//    public final String floor;
//    public final String IsOpen;
//    public final String openYear;
//    public final String closeYear;
    //Constructor
    public MyItem(LatLng location, String cnt) {
        this.mPosition = location;
        this.cnt = cnt;
    }
    @Override
    public LatLng getPosition() {
        return mPosition; //Position 반환
    }
}
    /*
    public MyItem (double lat,double lng, String Bizname, String category, String floor, String IsOpen, String openYear, String closeYear){
        mPosition = new LatLng(lat,lng);
        this.BizName = Bizname;
        this.Category = category;
        this.floor = floor;
        this.IsOpen  = IsOpen;
        this.openYear=openYear;
        this.closeYear=closeYear;
    }
    @Override
    public LatLng getPosition() {
        return mPosition; //Position 반환
    }
}
*/
