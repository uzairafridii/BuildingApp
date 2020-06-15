package com.uzair.buildingapp.LoginAndSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.buildingapp.HomeDashBoard.HomePage;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String token, key;
    EditText name, email, phone, country, password;
    TextView dateOfBirth;
    RadioButton male, female;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        initViews();

    }


    private void initViews() {
        token = getIntent().getStringExtra("token");
        key = getIntent().getStringExtra("greeting_key");
        Log.d("tokenSignUp", "onCreate: " + token);


        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);

        name = findViewById(R.id.userNameInBuildingRegsiteration);
        email = findViewById(R.id.emailInBuildingRegistration);
        phone = findViewById(R.id.phoneNumberInBuildingRegistration);
        country = findViewById(R.id.countryCodeInBuildingRegistration);
        password = findViewById(R.id.passwordInBuildingRegistration);
        dateOfBirth = findViewById(R.id.dateOfBirthInBuildingRegistration);

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

    }

    // sign in text click
    public void signIn(View view) {
        startActivity(new Intent(SignUp.this, LoginActivity.class));
        this.finish();
    }


    // signup button click
    public void registerUser(View view) {


        final String userName = name.getText().toString().trim();
        final String userPhone = phone.getText().toString().trim();
        final String userEmail = email.getText().toString().trim();
        final String userPassword = password.getText().toString().trim();
        final String userCountryCode = country.getText().toString().trim();
        final String dob = dateOfBirth.getText().toString().trim();
        final String userGender = getGender();
        Log.d("date", "registerUser: " + dob);

        if (!userName.isEmpty() && !userPhone.isEmpty() && !userEmail.isEmpty() && !userPassword.isEmpty()
                && !userCountryCode.isEmpty() && !dob.isEmpty() && !userGender.isEmpty()) {


            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {

                    final String deviceToken = instanceIdResult.getToken();

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
                                    Intent intent = new Intent(SignUp.this, HomePage.class);
                                    intent.putExtra("token", token);
                                    startActivity(intent);
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

                            data.put("gender", userGender);

                            data.put("national_id_no_type", "ID");

                            data.put("date_of_birth", dob);

                            data.put("location_id", "1024");

                            data.put("company_id", "null");

                            data.put("firebase_id", deviceToken);


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
            });


        } else {

            Toast.makeText(SignUp.this, "Required", Toast.LENGTH_SHORT).show();
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


    // click on edittext to select date of birth
    public void pickDateOfBirth(View view) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(SignUp.this, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int currentYear, int monthOfYear, int dayOfMonth) {

        Date date = new Date(currentYear, monthOfYear, dayOfMonth);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = format.format(date);
        dateOfBirth.setText(strDate);
        // Y-m-d
    }

    private String getGender() {
        String gender = null;
        if (male.isChecked()) {
            gender = "Male";
        } else if (female.isChecked()) {
            gender = "Female";
        }

        return gender;
    }
}
