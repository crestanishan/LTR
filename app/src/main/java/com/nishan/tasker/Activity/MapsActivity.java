package com.nishan.tasker.Activity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.nishan.tasker.MainActivity;
import com.nishan.tasker.R;
import com.nishan.tasker.Service.GPS_Service;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    public String str;
    Marker marker;

    private Button btnAdd;

    String selectedLat = "";
    String selectedLog = "";


    LocationManager locationManager;

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                   intent.getExtras().get("coordinates_X");
                    intent.getExtras().get("coordinates_Y");




                }
            };
        }

        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    public void startService(){
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnAdd = findViewById(R.id.btn_add_location);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedLat.isEmpty() || selectedLog.isEmpty()) {
                    Toast.makeText(MapsActivity.this, " No Location Selected", Toast.LENGTH_SHORT).show();

                    Toast.makeText(MapsActivity.this, " No Location Selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(MapsActivity.this, " Location selected", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);

                   intent.putExtra("lat", selectedLat);
                   intent.putExtra("log", selectedLog);

                   startActivity(intent);



                    finish();

                }


            }
        });


         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng latLng = new LatLng(latitude, longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getFeatureName();
                        Address address = addressList.get(0);
                        // LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

            startService();
        }



        else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {





                    }

                @Override
                public void onStatusChanged (String provider,int status, Bundle extras){

                }

                @Override
                public void onProviderEnabled (String provider){

                }

                @Override
                public void onProviderDisabled (String provider){

                }
            });

            }
        }

        startService();
    }


    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;
        String str;


        if (location != null || !location.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
                str = addressList.get(0).getLocality() + ",";
                str += addressList.get(0).getFeatureName();
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                if (marker != null) {
                    marker.remove();
                }

                marker = mMap.addMarker(new MarkerOptions().position(latLng).title(str));

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.setMaxZoomPreference(10);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude:" + ll.latitude);
                    tvLng.setText("Longitude:" + ll.longitude);

                    selectedLat = String.valueOf(ll.latitude);
                    selectedLog = String.valueOf(ll.longitude);

                    return v;
                }
            });
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                if (marker != null) {
                    marker.remove();
                }

                MarkerOptions options = new MarkerOptions().position(
                        new LatLng(point.latitude, point.longitude)
                ).title("Selected Location");
                marker = googleMap.addMarker(options);
                marker.showInfoWindow();

            }

        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                // TODO Auto-generated method stub
                Geocoder gc = new Geocoder(MapsActivity.this);
                LatLng ll = marker.getPosition();
                double lat = ll.latitude;
                double lng = ll.longitude;

                List<Address> list = null;

                try {
                    list = gc.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Address add = list.get(0);
                marker.setTitle(add.getLocality());


            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // TODO Auto-generated method stub
                //LatLng temp = marker.getPosition();


            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

//
//    @Override
//    public View getInfoWindow(Marker marker) {
//        return prepareInfoView(marker);
//    }

//    private View prepareInfoView(Marker marker) {
//
//        LinearLayout infoView = new LinearLayout(MapsActivity.this);
//        LinearLayout.LayoutParams infoViewParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        infoView.setOrientation(LinearLayout.HORIZONTAL);
//        infoView.setLayoutParams(infoViewParams);
//
//        LinearLayout subInfoView = new LinearLayout(MapsActivity.this);
//        LinearLayout.LayoutParams subInfoViewParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        subInfoView.setOrientation(LinearLayout.VERTICAL);
//
//        subInfoView.setLayoutParams(subInfoViewParams);
//
//        TextView subInfoLat = new TextView(MapsActivity.this);
//        TextView subInfoLnt = new TextView(MapsActivity.this);
//        TextView addressInfo = new TextView(MapsActivity.this);
//
//        subInfoLat.setText("Latitude: " + marker.getPosition().latitude);
//        subInfoLnt.setText("Longitude: " + marker.getPosition().longitude);
//
//
//        addressInfo.setText(str);
//        subInfoView.addView(subInfoLat);
//        subInfoView.addView(subInfoLnt);
//        subInfoView.addView(addressInfo);
//        infoView.addView(subInfoView);
//        return infoView;
//    }
//
////     @Override
//    public View getInfoContents(Marker marker) {
//        return prepareInfoView(marker);
//    }
}
