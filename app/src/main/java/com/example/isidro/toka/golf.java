package com.example.isidro.toka;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Golf extends Activity {

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location PlayerLocation;
    private Location FrontLocation;
    private Location MiddleLocation;
    private Location BackLocation;

    private JSONObject holes;

    private TextView hole;
    private TextView par;
    private TextView men_hcp;
    private TextView lad_hcp;
    private TextView tee_1;
    private TextView tee_2;
    private TextView tee_3;
    private TextView tee_4;

    private TextView front;
    private TextView middle;
    private TextView back;

    private static final int MAX_YARDAGE = 2000;
    private static final double METER_TO_YARD = 1.09361;
    private static final int HOLE_MAX = 17;
    private static final int HOLE_MIN = 0;

    private boolean flag = true;

    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hole_layout);

        setBottomBar();

        View decrementHole = findViewById(R.id.left_arrow);
        decrementHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != HOLE_MIN) {
                    index--;
                    setLocationText();
                    loadHoleData();
                }
            }
        });

        View incrementHole = findViewById(R.id.right_arrow);
        incrementHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index != HOLE_MAX) {
                    index++;
                    setLocationText();
                    loadHoleData();
                }
            }
        });

        hole = (TextView) findViewById(R.id.hole);
        par = (TextView) findViewById(R.id.par);
        men_hcp = (TextView) findViewById(R.id.mhdc);
        lad_hcp = (TextView) findViewById(R.id.lhdc);
        tee_1 = (TextView) findViewById(R.id.tee_1);
        tee_2 = (TextView) findViewById(R.id.tee_2);
        tee_3 = (TextView) findViewById(R.id.tee_3);
        tee_4 = (TextView) findViewById(R.id.tee_4);

        front = (TextView) findViewById(R.id.front);
        middle = (TextView) findViewById(R.id.middle);
        back = (TextView) findViewById(R.id.back);

        PlayerLocation = new Location("Player");
        FrontLocation = new Location("Front");
        MiddleLocation = new Location("Middle");
        BackLocation = new Location("Back");

        loadHoleData();

        startLocationService();
    }

    public void loadHoleData() {

        try {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                index = Integer.valueOf(getIntent().getExtras().getString("Hole")) - 1;
            }
            JSONObject obj = new JSONObject(loadJSONData());
            this.holes = (JSONObject) obj.getJSONObject("app").getJSONObject("hole_info").getJSONArray("holes").get(index);
            setHoleData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setHoleData() throws JSONException {

        hole.setText(holes.get("hole").toString());
        par.setText(holes.get("par").toString());
        men_hcp.setText(holes.get("men_hcp").toString());
        lad_hcp.setText(holes.get("lad_hcp").toString());
        tee_1.setText(holes.get("black").toString());
        tee_3.setText(holes.get("gold").toString());
        tee_2.setText(holes.get("silver").toString());
        tee_4.setText(holes.get("jade").toString());

        FrontLocation.setLongitude(Double.valueOf(holes.get("front_long").toString()));
        FrontLocation.setLatitude(Double.valueOf(holes.get("front_lat").toString()));

        MiddleLocation.setLongitude(Double.valueOf(holes.get("middle_long").toString()));
        MiddleLocation.setLatitude(Double.valueOf(holes.get("middle_lat").toString()));

        BackLocation.setLongitude(Double.valueOf(holes.get("back_long").toString()));
        BackLocation.setLatitude(Double.valueOf(holes.get("back_lat").toString()));
    }

    @Override
    protected void onPause() {
        Log.d("TAG", "ON PAUSE");

        super.onPause();
        stopLocationUpdates();
        flag = false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startLocationService() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if(flag) {
                    PlayerLocation.setLatitude(location.getLatitude());
                    PlayerLocation.setLongitude(location.getLongitude());

                    int frontDistance = (int) Math.round(PlayerLocation.distanceTo(FrontLocation) * METER_TO_YARD);
                    int middleDistance = (int) Math.round(PlayerLocation.distanceTo(MiddleLocation) * METER_TO_YARD);
                    int backDistance = (int) Math.round(PlayerLocation.distanceTo(BackLocation) * METER_TO_YARD);

                    if(middleDistance > 100) {
                        front.setText(String.valueOf("OUT"));
                        middle.setText(String.valueOf("OF"));
                        back.setText(String.valueOf("RAN"));

                        //Toast.makeText(Golf.this, "Must Be Within " + MAX_YARDAGE + " Yards To Use GPS",
                              //  Toast.LENGTH_SHORT).show();
                    } else {
                        front.setText(String.valueOf(frontDistance));
                        middle.setText(String.valueOf(middleDistance));
                        back.setText(String.valueOf(backDistance));
                    }

                    Log.d("TAG", "DISTANCE CALLED");
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
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            Log.d("TAG", "*****CALLED*****");
            if(flag) {
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
            }
        }
    }

    protected void stopLocationUpdates() {

        if (locationManager != null) {
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

    public void setLocationText() {
        front.setText(String.valueOf("..."));
        middle.setText(String.valueOf("..."));
        back.setText(String.valueOf("..."));
    }

    public void setBottomBar() {
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

    }
}
