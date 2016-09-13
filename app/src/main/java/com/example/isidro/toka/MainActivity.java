package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends Activity {

    public View newsletter;
    public View tee_times;
    public View scorecard;
    public View weather;

    public View facebook;
    public View instagram;
    public View twitter;

    public TextView title;
    public TextView weather_string;
    public TextView scorecard_string;
    public TextView newsletter_string;
    public TextView tee_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "ON CREATE");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setBottomBar();
        setText();
        setViews();
    }

    private void setText() {
        title = (TextView) findViewById(R.id.main_title_string);
        weather_string = (TextView) findViewById(R.id.weather_string);
        scorecard_string = (TextView) findViewById(R.id.scorecard_string);
        newsletter_string = (TextView) findViewById(R.id.newsletter_string);
        tee_string = (TextView) findViewById(R.id.tee_string);

        setFont();
    }

    private void setFont() {
        Typeface font_1 = Typeface.createFromAsset(getAssets(), "fonts/DroidSerif-Italic.ttf");
        title.setTypeface(font_1);

        Typeface font_2 = Typeface.createFromAsset(getAssets(), "fonts/DroidSerif-Regular.ttf");
        weather_string.setTypeface(font_2);
        scorecard_string.setTypeface(font_2);
        newsletter_string.setTypeface(font_2);
        tee_string.setTypeface(font_2);
    }

    private void setViews() {
        newsletter = findViewById(R.id.newsletter);
        setOnclick(newsletter, getResources().getString(R.string.newsletter_link));
        setOnclick(newsletter_string, getResources().getString(R.string.newsletter_link));

        tee_times = findViewById(R.id.tee_times);
        setOnclick(tee_times, getResources().getString(R.string.tee_times_link));
        setOnclick(tee_string, getResources().getString(R.string.tee_times_link));

        scorecard = findViewById(R.id.scorecard);
        setOnclick(scorecard, getResources().getString(R.string.scorecard_link));
        setOnclick(scorecard_string, getResources().getString(R.string.scorecard_link));

        weather = findViewById(R.id.weather);
        setOnclick(weather, getResources().getString(R.string.weather_link));
        setOnclick(weather_string, getResources().getString(R.string.weather_link));

        facebook = findViewById(R.id.facebook);
        setOnclick(facebook, getResources().getString(R.string.facebook));

        instagram = findViewById(R.id.instagram);
        setOnclick(instagram, getResources().getString(R.string.instagram));

        twitter = findViewById(R.id.twitter);
        setOnclick(twitter, getResources().getString(R.string.twitter));
    }

    private void setOnclick(View view, final String link) {

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(setClickableLink(link));
            }
        });
    }

    private Intent setClickableLink(String link) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(link));
        return  intent;
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
