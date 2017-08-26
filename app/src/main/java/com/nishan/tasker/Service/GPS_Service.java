package com.nishan.tasker.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nishan.tasker.Database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishan on 8/23/17.
 */

public class GPS_Service extends Service {
    private LocationListener listener;
    private LocationManager locationManager;

    DatabaseHandler db=new DatabaseHandler(this);
    List<Double> arlist=new ArrayList<Double>();
    List<Double> arrylist=new ArrayList<Double>();




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

       listener = new LocationListener() {
           @Override
           public void onLocationChanged(Location location) {
               Intent a = new Intent("location_update");
               a.putExtra("coordinate_X", location.getLatitude());
               a.putExtra("coordinate_Y", location.getLongitude());
               sendBroadcast(a);

               if (location != null) {
                 //  String Text = "My current location is: " + "Latitude = " + location.getLatitude() + "Longitude = " + location.getLongitude();
                   SQLiteDatabase y = db.getWritableDatabase();
                   //Toast.makeText(getApplicationContext(), Text, Toast.LENGTH_SHORT).show();

                   Cursor cr = null;
                   Cursor cur = null;
                   cr = y.rawQuery("SELECT Latitude FROM" + "TASK_TABLE", null);
                   cr.moveToFirst();
                   cur = y.rawQuery("SELECT Longitude FROM" + "TASK_TABLE", null);
                   cur.moveToFirst();

                   while (!cr.isAfterLast()) {

                       arlist.add(cr.getDouble(cr.getColumnIndex("latitude")));
                       cr.moveToNext();
                   }
                   while (!cur.isAfterLast()) {

                       arrylist.add(cr.getDouble(cr.getColumnIndex("longitude")));
                       cur.moveToNext();
                   }

                   for (int i = 0; i < arlist.size(); i++) {
                       Location locB = new Location("point B");
                       locB.setLatitude(arlist.get(i));
                       locB.setLongitude(arrylist.get(i));
                   //   float distance = location.distanceTo(locB);
                   }

                  // if(distance<5){
                      // Intent myIntent=new Intent(GPS_Service.this,NotifyService.class);
                      // startActivity(myIntent);

                  for (int j = 0; j < arlist.size(); j++) {
                       if (calcDistance(location.getLatitude(), location.getLongitude(), arlist.get(j), arrylist.get(j)) < 5) {
                           Intent myIntent = new Intent(GPS_Service.this, NotifyService.class);
                           startActivity(myIntent);
                       }


                   }
               }
           }



           @Override
           public void onStatusChanged(String s, int i, Bundle bundle) {

           }

           @Override
           public void onProviderEnabled(String s) {

           }

           @Override
           public void onProviderDisabled(String s) {

               Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(i);

           }
       };

       locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,30000,0,listener);

        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null){
            locationManager.removeUpdates(listener);
        }
    }

    public double calcDistance(double latA, double longA, double latB, double longB) {

        double theDistance = (Math.sin(Math.toRadians(latA)) *  Math.sin(Math.toRadians(latB)) + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)) * Math.cos(Math.toRadians(longA - longB)));
        return new Double((Math.toDegrees(Math.acos(theDistance))) * 69.09*1.6093);
    }

}







