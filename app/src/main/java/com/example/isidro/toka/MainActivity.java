package com.example.isidro.toka;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location PlayerLocation;
    private Location FrontLocation;
    private Location MiddleLocation;
    private Location BackLocation;

    private JSONObject holes;

    private double frontDistance;
    private double middleDistance;
    private double backDistance;

    private static final double METER_TO_YARD = 1.09361;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "ON CREATE");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        PlayerLocation = new Location("Player");
        FrontLocation = new Location("Front");
        MiddleLocation = new Location("Middle");
        BackLocation = new Location("Back");

        try {
            JSONObject obj = new JSONObject(loadJSONData());
            this.holes = (JSONObject) obj.getJSONObject("app").getJSONObject("hole_info").getJSONArray("holes").get(0);

            MiddleLocation.setLatitude(holes.getDouble("middle_lat"));
            MiddleLocation.setLongitude(holes.getDouble("middle_long"));

            startLocationService();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                }
                return;
        }
    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "CLICKED");
                locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
            }
        });
    }

    @Override
    protected void onPause() {
        Log.d("TAG", "ON PAUSE");

        super.onPause();
        stopLocationUpdates();
    }

    public void startLocationService() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                PlayerLocation.setLatitude(location.getLatitude());
                PlayerLocation.setLongitude(location.getLongitude());

                int distance = (int) Math.round(PlayerLocation.distanceTo(MiddleLocation) * METER_TO_YARD);

                textView.append("\n" + String.valueOf(distance));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
                return;
            }
        } else {
            Log.d("TAG", "CALLED");
            configureButton();
        }
    }

    protected void stopLocationUpdates() {

        if(locationManager != null) {
            Log.d("TAG", "ENDING LOCATION UPDATES");

            locationManager.removeUpdates(locationListener);
            locationManager = null;
        }
    }

    @Override
    public void onResume() {
        Log.d("TAG", "ON RESUME");
        super.onResume();

        startLocationService();
    }

    public String loadJSONData() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("course_info.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
