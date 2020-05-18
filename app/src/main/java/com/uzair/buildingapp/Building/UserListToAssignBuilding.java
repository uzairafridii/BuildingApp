package com.uzair.buildingapp.Building;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.uzair.buildingapp.HomeDashBoard.HomePage;
import com.uzair.buildingapp.LoginAndSignUp.SignUp;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserListToAssignBuilding extends AppCompatActivity {


    private TextView noUserAvailable;
    private RecyclerView userListRecycler;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String token , companyId , buildingGuid;
    private ProgressDialog progressDialog;
    private List<UsersModel> usersModelList;
    private AdapterForUserListRecycler adapterForUserListRecycler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_to_assign_building);

        initViews();
        getCompanyId();
    }


    private void initViews()
    {
         usersModelList = new ArrayList<>();
        noUserAvailable = findViewById(R.id.noUserFound);
        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        token = getIntent().getStringExtra("access_token");
        buildingGuid = getIntent().getStringExtra("building_key");
        Log.d("tokenFromBottomSheet", "initViews: "+token);
        Log.d("buildingKeyBottomSheet", "initViews: "+buildingGuid);

        toolbar = findViewById(R.id.userListToolbar);
        setSupportActionBar(toolbar);

        userListRecycler = findViewById(R.id.usersListRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        userListRecycler.setLayoutManager(layoutManager);

        adapterForUserListRecycler = new AdapterForUserListRecycler(  this , usersModelList);
    }

    // fab clic to add new user
    public void addNewUserFabClick(View view)
    {
        View addNewUserFormView = LayoutInflater.from(this).inflate(R.layout.add_user_form, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(addNewUserFormView);

        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);

        final EditText name , phone , email , country;
        name = addNewUserFormView.findViewById(R.id.userNameInBuildingRegsiteration);
        phone = addNewUserFormView.findViewById(R.id.phoneNumberInBuildingRegistration);
        email = addNewUserFormView.findViewById(R.id.emailInBuildingRegistration);
        country = addNewUserFormView.findViewById(R.id.countryCodeInBuildingRegistration);



        Button registerBtn = addNewUserFormView.findViewById(R.id.registerBtnInBuildingRegistration);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userName  = name.getText().toString();
                final String userPhone = phone.getText().toString();
                final String userEmail = email.getText().toString();
                final String userPassword = String.valueOf(UUID.randomUUID());
                final String userCountryCode = country.getText().toString();

               createNewUser(userName , userPhone,  userEmail , userPassword , userCountryCode);

                Log.d("registrationData", "onClick: "+userName+""+userPassword+""
                        +userPhone+""+userCountryCode+""+userEmail);

                dialog.dismiss();



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

                    data.put("company_id", companyId);


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
            Toast.makeText(UserListToAssignBuilding.this, userName+","+userPassword+""
                    +userPhone+""+userCountryCode+""+userEmail, Toast.LENGTH_SHORT).show();

            Toast.makeText(UserListToAssignBuilding.this, "Required", Toast.LENGTH_SHORT).show();
        }


    }

    // to get company id
    private void getCompanyId()
    {

        StringRequest requestToGetCompanyId = new StringRequest(Request.Method.GET, UrlsContract.GET_COMPANY_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonResponse = jsonObject.getJSONObject("response");
                            companyId = jsonResponse.get("company_id").toString();
                            Log.d("companyId", "onResponse: "+companyId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserListToAssignBuilding.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(UserListToAssignBuilding.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String , String> tokenMap = new HashMap<>();
                tokenMap.put("Authorization" ,"Bearer "+token);
                return tokenMap;
            }
        };


        MySingleton.getInstance(this).addToRequestQueue(requestToGetCompanyId);

    }

    // to get the list of users
    private void getCompanyUserList()
    {
        String  userListUrl = UrlsContract.GET_USERS_UID;

        StringRequest getCompanyUsers = new StringRequest(Request.Method.GET, userListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response != null) {
                    noUserAvailable.setVisibility(View.INVISIBLE);
                }
                try {
                    Gson gson  = new Gson();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("response");

                    Log.d("jsonResultBuilding", "onResponse: " + result.getJSONArray("data"));
                    JSONArray jArray = result.getJSONArray("data");

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                      //  String uid = json_data.get("guid").toString();
                        UsersModel userList = gson.fromJson(String.valueOf(json_data), UsersModel.class);
                        usersModelList.add(userList);
                        userListRecycler.setAdapter(adapterForUserListRecycler);

                        Log.d("jsonDataList", "onResponse: " + usersModelList);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserListToAssignBuilding.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String , String> companyUserToken = new HashMap<>();
                companyUserToken.put("Authorization", "Bearer "+token);

                return companyUserToken;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(getCompanyUsers);


    }


    @Override
    protected void onResume()
    {
        super.onResume();
        getCompanyUserList();


    }
}
