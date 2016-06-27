package com.example.blacknblack.jonfinalproject4;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMapFragment extends Fragment {

    public String name;
    public String address;
    public double latitude;
    public double longitude;
    public String icon;
    public int open;
    public String photo;
    public String type;

    public MyMapFragment() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_map, container, false);
        TextView nameTV = (TextView) view.findViewById(R.id.tvname);
        TextView addressTV = (TextView) view.findViewById(R.id.tvaddress);
        TextView typeTV = (TextView) view.findViewById(R.id.tvtype);

        nameTV.setText(name);
        addressTV.setText(address);
        typeTV.setText(type);



        MapFragment mapFragment;
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment);
        if (mapFragment == null) {
            mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.fragment);
        }

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                LatLng latLng = new LatLng(latitude, longitude);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                googleMap.moveCamera(cameraUpdate);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My position").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Marker marker = googleMap.addMarker(markerOptions);

            }
        });



        return view;
    }
}
