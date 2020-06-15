package com.uzair.buildingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uzair.buildingapp.HomeDashBoard.HomePage;
import com.uzair.buildingapp.LoginAndSignUp.LoginActivity;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    private String key = null;
    private SharedPreferences saveKeyPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            public void run() {

                StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlsContract.GREETING_KEY_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("jsonResult", "onResponse: "+response);
                                JSONObject  jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);

                                    key = jsonObject.get("key").toString();
                                    Log.d("key", "onResponse: "+key);

                                    // store greeting key
                                    saveKeyPref = getSharedPreferences(LoginActivity.USER_DETAILS, MODE_PRIVATE);
                                    editor = saveKeyPref.edit();
                                    editor.putString("key", key);
                                    editor.commit();
                                    editor.apply();
                                    // move to home page
                                    startActivity(new Intent(Splash.this , HomePage.class));
                                    Splash.this.finish();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("jsonError", "onResponse: "+e.getMessage());
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", "splash: Error"+error.getMessage());
                    }
                });

                MySingleton.getInstance(Splash.this).addToRequestQueue(stringRequest);




            }
        }, 1 * 1000);

    }
}
