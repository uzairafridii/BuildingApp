package com.uzair.buildingapp.Building;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.uzair.buildingapp.HomeDashBoard.HomePage;
import com.uzair.buildingapp.LoginAndSignUp.SignUp;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserListToAssignBuilding extends AppCompatActivity {


    private RecyclerView userListRecycler;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String token;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_to_assign_building);

        initViews();
    }


    private void initViews()
    {

        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        token = getIntent().getStringExtra("access_token");
        Log.d("tokenFromBottomSheet", "initViews: "+token);

        toolbar = findViewById(R.id.userListToolbar);
        setSupportActionBar(toolbar);

        userListRecycler = findViewById(R.id.usersListRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        userListRecycler.setLayoutManager(layoutManager);
    }

    // fab clic to add new user
    public void addNewUserFabClick(View view)
    {
        View addNewUserFormView = LayoutInflater.from(this).inflate(R.layout.add_user_form, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(addNewUserFormView);

        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);

        EditText name , phone , email , password , country;
        name = addNewUserFormView.findViewById(R.id.userName);
        phone = addNewUserFormView.findViewById(R.id.phoneNumber);
        email = addNewUserFormView.findViewById(R.id.email);
        country = addNewUserFormView.findViewById(R.id.countryCode);
        password = addNewUserFormView.findViewById(R.id.password);

        final String userName  = name.getText().toString().trim();
        final String userPhone = phone.getText().toString().trim();
        final String userEmail = email.getText().toString().trim();
        final String userPassword = password.getText().toString().trim();
        final String userCountryCode = country.getText().toString().trim();


        addNewUserFormView.findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewUser(userName , userPhone,  userEmail , userPassword , userCountryCode );
            }
        });

        dialog.show();

    }


    // add user detail to create new account
    private void createNewUser(final String userName , final String userPhone , final String userEmail
            , final String userPassword, final String userCountryCode)
    {



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
                            Toast.makeText(UserListToAssignBuilding.this, "Successfully Register ", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();


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
                        Toast.makeText(UserListToAssignBuilding.this, resultError, Toast.LENGTH_LONG).show();
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

            MySingleton.getInstance(UserListToAssignBuilding.this).addToRequestQueue(stringRequest);


        }
        else
        {
            Toast.makeText(this, "Please all fields are required", Toast.LENGTH_SHORT).show();
        }





    }
}
