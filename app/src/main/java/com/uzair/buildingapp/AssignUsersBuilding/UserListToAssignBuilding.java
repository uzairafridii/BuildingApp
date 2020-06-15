package com.uzair.buildingapp.AssignUsersBuilding;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListToAssignBuilding extends AppCompatActivity {


    public static final int PICK_IMAGE_REQUEST = 1;
    private TextView noUserAvailable;
    private RecyclerView userListRecycler;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String token , companyId , buildingGuid, image;
    private ProgressDialog progressDialog;
    private List<AssignUsersModel> assignUsersModelList;
    private AdapterForUserListRecycler adapterForUserListRecycler;
    private CircleImageView imageView;
    private Bitmap bitmap, lastBitmap = null;
    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_to_assign_building);

        initViews();
        getCompanyId();
        getCompanyUserList();
    }


    private void initViews()
    {
        assignUsersModelList = new ArrayList<>();
        noUserAvailable = findViewById(R.id.noUserFound);
        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        token = getIntent().getStringExtra("access_token");
        buildingGuid = getIntent().getStringExtra("building_key");
        Log.d("tokenFromBottomSheet", "initViews: "+token);
        Log.d("buildingKeyBottomSheet", "initViews: "+buildingGuid);

        toolbar = findViewById(R.id.userListToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Users List");

        userListRecycler = findViewById(R.id.usersListRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        userListRecycler.setLayoutManager(layoutManager);

        adapterForUserListRecycler = new AdapterForUserListRecycler(  this ,assignUsersModelList);
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

        imageView = addNewUserFormView.findViewById(R.id.selectImage);

        Button registerBtn,addImageBtn;
        registerBtn = addNewUserFormView.findViewById(R.id.registerBtnInBuildingRegistration);
        addImageBtn = addNewUserFormView.findViewById(R.id.addUserImageBtn);

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermissions()) {

                    startGalleryIntent();
                }
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

                final String userName  = name.getText().toString();
                final String userPhone = phone.getText().toString();
                final String userEmail = email.getText().toString();
                final String userPassword = String.valueOf(UUID.randomUUID());
                final String userCountryCode = country.getText().toString();

               createNewUser(userName , userPhone,  userEmail , userPassword , userCountryCode , image);

                Log.d("registrationData", "onClick: "+userName+""+userPassword+""
                        +userPhone+""+userCountryCode+""+userEmail);





            }
        });

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            Log.d("uri", "onActivityResult: "+filePath);

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                lastBitmap = bitmap;
                if(lastBitmap != null) {//encoding image to string
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(lastBitmap);
                    image = getStringImage(lastBitmap);
                    Log.d("imageString", "onActivityResult: " + image);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp) {
        //converting image to base64 string
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return imageString;

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // add user detail to create new account
    private void createNewUser(final String userName , final String userPhone , final String userEmail
            , final String userPassword, final String userCountryCode , String imageUrl)
    {

        if(!userName.isEmpty() && !userPhone.isEmpty() && !userEmail.isEmpty()
                && !userPassword.isEmpty()
                && !userCountryCode.isEmpty()
                && imageUrl != null) {


            progressDialog.setMessage("Please wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                    UrlsContract.CREATE_USER_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.e("serverResult", "status code: " + response.statusCode);
                            String resultResponse = new String(response.data);
                            try {

                                JSONObject result = new JSONObject(resultResponse);
                                String name = result.getString("name");
                                Log.e("severName", name + " " );
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("CatchError", "onResponse: "+e.getMessage());
                            }

                            Toast.makeText(UserListToAssignBuilding.this, "Successfully added", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("ErrorMessageMultipart", "onErrorResponse: "+error.getCause()+"\n"+error.getMessage());
                            progressDialog.dismiss();
                        }
                    }) {


                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have parameters with the image
                 * which is tags
                 * */
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

                    data.put("building_guid", buildingGuid);

                    return data;
                }


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> tokenData = new HashMap<>();
                    tokenData.put("Authorization", "Bearer " + token);

                    return tokenData;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();

                    params.put("avatar", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                    return params;
                }

            };

            //adding the request to volley
            MySingleton.getInstance(this).addToRequestQueue(volleyMultipartRequest);
        }
        else
        {
            Toast.makeText(UserListToAssignBuilding.this, "Sorry! Image and all fields are required", Toast.LENGTH_LONG).show();
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
                        AssignUsersModel userList = gson.fromJson(String.valueOf(json_data), AssignUsersModel.class);
                        assignUsersModelList.add(userList);
                        userListRecycler.setAdapter(adapterForUserListRecycler);

                        Log.d("jsonDataList", "onResponse: " + assignUsersModelList);
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


    // check runtime storage permission
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    // check permission is granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startGalleryIntent();
            }
            return;
        }
    }

    // galler intent to select image
    private void startGalleryIntent()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }


}
