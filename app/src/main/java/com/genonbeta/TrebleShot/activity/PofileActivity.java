package com.genonbeta.TrebleShot.activity;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.genonbeta.TrebleShot.R;
import com.genonbeta.TrebleShot.callback.HttpRequestUtilsZerox;
import com.genonbeta.TrebleShot.config.ZeroxVMConfig;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;


public class PofileActivity extends AppCompatActivity {

    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    HttpRequestUtilsZerox  httpRequestUtilsZerox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
          httpRequestUtilsZerox = new HttpRequestUtilsZerox(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pofile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        textView = findViewById(R.id.profile_id);
        textView.setText("This  is testing");

        textView1 = findViewById(R.id.profile_id2);
        textView1.setText("This  is testing  Hellog testing ");

        textView2 = findViewById(R.id.profile_id3);
        textView2.setText("This  is testing  Hellog testing ");

        updateProfile(getApplicationContext());
    }

    private void updateProfile(Context context) {
        System.out.println(" This is Testing ");
        RequestQueue requestQueue = httpRequestUtilsZerox.getRequestQueue();

        String baseUrl = ZeroxVMConfig.baseURLOfZerox.concat(ZeroxVMConfig.GET_PROFILE_DETAILS).concat("?emailOrMobile=rathor.rajeev@gmail.com");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(" This is ==========================" + response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(" Request failed to execute "+ error.getStackTrace().toString());
                        System.out.println(" -===========================********** "+ error.getCause());
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonObjectRequest);


    }

}
