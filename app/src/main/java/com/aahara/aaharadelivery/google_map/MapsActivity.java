package com.aahara.aaharadelivery.google_map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aahara.aaharadelivery.History;
import com.aahara.aaharadelivery.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,TaskLoadedCallback{


    private GoogleMap mMap;
    private static final int PLACE_PICKER_REQUEST = 2;
    int TAG_CODE_PERMISSION_LOCATION = 1;
    ImageView ivBack;
    // latitude and longitude
    double latitude = 15.1475871;
    double longitude = 76.9033749;
    String restorentName ="pola";
    ArrayList<MarkerOptions> listPoints;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToOrderList = new Intent(MapsActivity.this, History.class);
                startActivity(backToOrderList);
            }
        });



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        placingPicker();
        movingCamera();


        currentLocations();


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void markerposition2(){
        // latitude and longitude
        double lati =15.164927 ;
        double longi = 76.88578 ;

// create marker
        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(lati, longi)).title("Parvati nagar ");

// adding marker
        mMap.addMarker(marker2);

    }
    private void placingPicker() {

//        Intent intent = getIntent();
//        Bundle b = intent.getExtras();
//
//        if(b!= null){
//            restorentName = (String)b.get("Restorent Name");
//            latitude = (double)b.get("lt");
//            longitude = (double)b.get("lg");
//        }
        // create marker
        MarkerOptions marker1 = new MarkerOptions().position(new LatLng(latitude, longitude)).title(restorentName);
        MarkerOptions marker2 = new MarkerOptions().position(new LatLng(15.164927, 76.88578)).title("Parvati nagar ");
        // adding marker
        mMap.addMarker(marker1);
        mMap.addMarker(marker2);

            String url = getRequestUrl(marker1.getPosition(), marker2.getPosition(),"driving");
            new FetchURL(this,getApplicationContext()).execute(url,"driving");

    }

    private String getRequestUrl(LatLng origin, LatLng dest, String driving){

        String str_origin = "origin" + origin.latitude + "," + origin.longitude;
        String str_dest = "dest" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + driving;
        String sensor = "sensor=false";
        String output = "json";
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key) + sensor;
     //   String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }



    private void movingCamera() {

        LatLng hotel = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(hotel).title(restorentName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hotel));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(longitude, latitude)).zoom(17).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
    private void currentLocations(){
        //current location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, TAG_CODE_PERMISSION_LOCATION);
        }

        //zoom button
        mMap.getUiSettings().setZoomControlsEnabled(true); // true to enable
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        //my location

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);


    }
}