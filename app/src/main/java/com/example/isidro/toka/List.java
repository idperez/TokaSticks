package com.example.isidro.toka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class List extends Activity {

    private final int MAX = 18;

    private TextView hole;
    private TextView par;

    private TextView list_title;
    private TextView hole_string;
    private TextView par_string;

    private JSONObject holes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setText();

        LinearLayout layout = (LinearLayout)findViewById(R.id.root);
        LayoutInflater inflater = getLayoutInflater();

        for(int i = 1; i <= MAX; i++) {

            int currentHole = i;

            View listLayout = inflater.inflate(R.layout.list_item, null, false);

            hole = (TextView) listLayout.findViewById(R.id.list_hole);
            hole.setText(String.valueOf(currentHole));

            try {
                currentHole--;
                JSONObject obj = new JSONObject(loadJSONData());
                holes = (JSONObject) obj.getJSONObject("app").getJSONObject("hole_info").getJSONArray("holes").get(currentHole);

                par = (TextView) listLayout.findViewById(R.id.list_par);
                par.setText(String.valueOf(String.valueOf(holes.get("par"))));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final int touch = i;

            listLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Golf.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Hole", String.valueOf(touch));
                    getApplicationContext().startActivity(intent);
                }
            });

            layout.addView(listLayout);
        }

        setBottomBar();
    }

    public void setText() {
        list_title = (TextView) findViewById(R.id.list_title);
        par_string = (TextView) findViewById(R.id.par_list_string);
        hole_string = (TextView) findViewById(R.id.hole_list_string);

        setFont();
    }

    public void setFont() {
        Typeface font_1 = Typeface.createFromAsset(getAssets(), "fonts/DroidSerif-Regular.ttf");
        list_title.setTypeface(font_1);
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

    public void setBottomBar() {
        final Intent golf = new Intent(this, Golf.class);
        final Intent home = new Intent(this, MainActivity.class);
        final Intent contact = new Intent(this, Contact.class);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_list);
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
                    case R.id.tab_contact:
                        startActivity(contact);
                        break;
                }
            }
        });
    }
}
