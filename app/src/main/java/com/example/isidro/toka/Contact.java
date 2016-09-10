package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setBottomBar();
    }

    public void setBottomBar() {
        final Intent golf = new Intent(this, Golf.class);
        final Intent home = new Intent(this, MainActivity.class);
        final Intent list = new Intent(this, List.class);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_contact);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.tab_golf:
                        startActivity(golf);
                        break;
                    case R.id.tab_home:
                        startActivity(home);
                        break;
                    case R.id.tab_list:
                        startActivity(list);
                        break;
                }
            }
        });
    }
}
