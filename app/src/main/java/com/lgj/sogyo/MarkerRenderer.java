package com.lgj.sogyo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.ClusterRenderer;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import org.w3c.dom.Text;

import java.util.Set;

public class MarkerRenderer extends DefaultClusterRenderer<MyItem> {
    public View marker_root_view;
    public TextView tv_marker;

    public MarkerRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        marker_root_view = LayoutInflater.from(context).inflate(R.layout.marker,null);
        tv_marker=(TextView) marker_root_view.findViewById(R.id.tv_marker);
    }

    @Override
    public void onClustersChanged(Set<? extends Cluster<MyItem>> clusters) {
        super.onClustersChanged(clusters);
//        onCameraIdle();
    }

    //Icon 설정 과정.
    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        tv_marker.setText(item.cnt.substring(0,2));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        marker_root_view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker_root_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker_root_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker_root_view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker_root_view.getMeasuredWidth(), marker_root_view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        marker_root_view.draw(canvas);

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.imgbtn_blue_normal));
    }


    @Override
    public void setOnClusterItemClickListener(ClusterManager.OnClusterItemClickListener<MyItem> listener) {
        super.setOnClusterItemClickListener(listener);
    }
}