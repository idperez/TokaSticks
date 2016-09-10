package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.os.Bundle;

import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "ON CREATE");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setBottomBar();
    }

    private void setBottomBar() {
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
