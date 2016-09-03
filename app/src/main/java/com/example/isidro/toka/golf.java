package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Golf extends Activity {

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

    private int currentHole;

    private static final double METER_TO_YARD = 1.09361;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hole_layout);

        final Intent home = new Intent(this, MainActivity.class);
        final Intent list = new Intent(this, List.class);
        final Intent contact = new Intent(this, Contact.class);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_golf);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_home:
                        startActivity(home);
                        break;
                    case R.id.tab_list:
                        startActivity(list);
                        break;
                    case R.id.tab_contact:
                        startActivity(contact);
                        break;
                }
            }
        });

        try {
            JSONObject obj = new JSONObject(loadJSONData());
            this.holes = (JSONObject) obj.getJSONObject("app").getJSONObject("hole_info").getJSONArray("holes").get(currentHole);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*

        PlayerLocation = new Location("Player");
        FrontLocation = new Location("Front");
        MiddleLocation = new Location("Middle");
        BackLocation = new Location("Back");

        try {

            startLocationService();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }

    /*
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
        //stopLocationUpdates();
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

        //startLocationService();
    }
*/
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
