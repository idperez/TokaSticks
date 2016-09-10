package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Contact extends Activity {

    private final String WEB = "web";
    private final String CALL = "call";

    private View tokaWeb;
    private View fourWeb;
    private View tokaCall;
    private View fourCall;
    private View facebook;
    private View twitter;
    private View instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        setBottomBar();
        setViews();
    }

    private void setViews() {

        tokaWeb = findViewById(R.id.toka_web);
        setOnclick(tokaWeb, getResources().getString(R.string.toka_website_link), WEB);

        fourWeb = findViewById(R.id.four_web);
        setOnclick(fourWeb, getResources().getString(R.string.four_webiste_link), WEB);

        tokaCall = findViewById(R.id.call_toka);
        setOnclick(tokaCall, getResources().getString(R.string.toka_phone), CALL);

        fourCall = findViewById(R.id.call_four);
        setOnclick(fourCall, getResources().getString(R.string.four_phone), CALL);

        facebook = findViewById(R.id.facebook);
        setOnclick(facebook, getResources().getString(R.string.facebook), WEB);

        instagram = findViewById(R.id.instagram);
        setOnclick(instagram, getResources().getString(R.string.instagram), WEB);

        twitter = findViewById(R.id.twitter);
        setOnclick(twitter, getResources().getString(R.string.twitter), WEB);
    }

    private void setOnclick(View view, final String link, final String action) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (action) {
                    case WEB:
                        startActivity(setClickableLink(link));
                        break;
                    case CALL:
                        startActivity(setPhoneCall(link));
                }

            }
        });
    }

    private Intent setPhoneCall(String link) {
        Uri number = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_DIAL, number);
        return intent;
    }

    private Intent setClickableLink(String link) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(link));
        return  intent;
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
