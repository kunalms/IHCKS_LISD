package com.indiahacks.lisd;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent i= getIntent();
        trip=(Trip)i.getSerializableExtra("trip_details");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date objectCreatedDate = null;
        try {
            objectCreatedDate = formater.parse(trip.getInitialTime().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formater = new SimpleDateFormat("dd MMMM yyyy hh:mm:ss");
        String strDate = formater.format(objectCreatedDate);

        ArrayList<String> Lat,Lon,timeStamp;
        Lat=trip.getLatitude();
        Lon=trip.getLongitude();
        timeStamp=trip.getTimestamp();
        for(int i=0;i<Lat.size();i++){

            LatLng pos= new LatLng(Double.parseDouble(Lat.get(i)),Double.parseDouble(Lon.get(i)));
            mMap.addMarker(new MarkerOptions().position(pos).title(strDate));
            Log.d("latilong", "onMapReady: "+pos.toString());
            if(i==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 19.0f ) );
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
