package com.example.isidro.toka;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.location.LocationManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "ON CREATE");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        final Intent golf = new Intent(this, Golf.class);
        final Intent list = new Intent(this, List.class);
        final Intent contact = new Intent(this, Contact.class);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_home);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_golf:
                        startActivity(golf);
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
