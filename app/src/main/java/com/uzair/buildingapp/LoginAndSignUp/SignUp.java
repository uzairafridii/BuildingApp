package com.uzair.buildingapp.LoginAndSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uzair.buildingapp.HomeDashBoard.HomePage;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    String token , key;
    EditText name, email, phone, country, password;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        initViews();

    }


    private void initViews()
    {
        token = getIntent().getStringExtra("token");
        key  = getIntent().getStringExtra("greeting_key");
        Log.d("tokenSignUp", "onCreate: "+token);


        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        name = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phoneNumber);
        country = findViewById(R.id.countryCode);
        password = findViewById(R.id.password);

    }

    // sign in text click
    public void signIn(View view) {
        startActivity(new Intent(SignUp.this, MainActivity.class));
        this.finish();
    }


    // signup button click
    public void registerUser(View view) {


        final String userName  = name.getText().toString().trim();
        final String userPhone = phone.getText().toString().trim();
        final String userEmail = email.getText().toString().trim();
        final String userPassword = password.getText().toString().trim();
        final String userCountryCode = country.getText().toString().trim();

        if(!userName.isEmpty() && !userPhone.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty()
          && !userCountryCode.isEmpty()) {


            progressDialog.setMessage("Please wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsContract.CREATE_USER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.d("createUserResponse", "onResponse: " + response);
                            Toast.makeText(SignUp.this, "Successfully Register ", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(SignUp.this, HomePage.class));
                            SignUp.this.finish();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    String responseBody = null;
                    try {

                        responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responseBody);
                        String resultError = data.get("error").toString();
                        Log.d("resultErrorInSignUp", "onResponse: " + resultError);
                        Toast.makeText(SignUp.this, resultError, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("jsonError", "onResponse: " + e.getMessage());
                        progressDialog.dismiss();
                    }

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> data = new HashMap<>();

                    data.put("name", userName);

                    data.put("phone", userPhone);

                    data.put("email", userEmail);

                    data.put("national_id_no", userCountryCode);

                    data.put("password", userPassword);

                    data.put("gender", "Male");

                    data.put("national_id_no_type", "ID");

                    data.put("date_of_birth", "1989-10-10");

                    data.put("location_id", "1024");

                    data.put("company_id", "38");



                    return data;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> tokenData = new HashMap<>();
                    tokenData.put("Authorization", "Bearer " + token);

                    return tokenData;
                }
            };

            MySingleton.getInstance(SignUp.this).addToRequestQueue(stringRequest);


        }
        else
        {
            Toast.makeText(this, "Please all fields are required", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlsContract.TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("jsonResult", "onResponse: " + response);
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            token = jsonObject.get("access_token").toString();
                            Log.d("tokenSignUp", "onResponse: " + token);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("jsonError", "onResponse: " + e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "login: Error" + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> data = new HashMap<>();
                data.put("grant_type", "password");
                data.put("password", "nifty@20#");
                data.put("username", "uzairned@gmail.org");
                data.put("scope", "*");
                return data;


            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> token = new HashMap<>();
                token.put("Greeting-Key", key);

                return token;
            }
        };

        MySingleton.getInstance(SignUp.this).addToRequestQueue(stringRequest);


    }



}
