package com.uzair.buildingapp.BottomSheet;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.uzair.buildingapp.Utils.MyCurrentLocation;
import com.uzair.buildingapp.AssignUsersBuilding.UserListToAssignBuilding;
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {


    private ImageView addUser, addLog, edit;
    private double lat, lng;
    private String token, selectedColor , buildingGuid ;
    private int uid;
    private View menuView;
    private ProgressDialog progressDialog;

    public MenuBottomSheet() {
    }

    public MenuBottomSheet(String token , String buildingGuid , int uid) {
        // Required empty public constructor
        this.token = token;
        this.buildingGuid = buildingGuid;
        this.uid = uid;
        Log.d("building guid", "MenuBottomSheet: "+buildingGuid);
        Log.d("user guid", "MenuBottomSheet: "+uid);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        menuView = inflater.inflate(R.layout.fragment_menu_bottom_sheet, container, false);
        LinearLayout layout = menuView.findViewById(R.id.parentLayout);
        layout.setBackgroundResource(R.drawable.ed_bg);

        progressDialog = new ProgressDialog(getContext());

        initViews();


        return menuView;
    }

    private void initViews() {
        addUser = menuView.findViewById(R.id.addUser);
        addLog = menuView.findViewById(R.id.addLog);
        edit = menuView.findViewById(R.id.edit);

        addLog.setOnClickListener(this);
        addUser.setOnClickListener(this);
        edit.setOnClickListener(this);

        lat  = MyCurrentLocation.getCurrentLat();
        lng  = MyCurrentLocation.getCurrentLng();

        progressDialog = new ProgressDialog(getContext());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addUser:
                {
                addUserDetails();
                break;
            }
            case R.id.addLog: {
                addLogDetails();
                break;
            }
            case R.id.edit: {
                editBuildingDetails();
                break;
            }
        }
    }

    /**
     *  log  methods to add log on some building
     */
    private void addLogDetails() {

        View colorView = LayoutInflater.from(getContext()).inflate(R.layout.detection_log_form, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(colorView);
        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);


        ImageView skyBlue, pink, parrot, orange, purple, red, yellow, green;
        skyBlue = colorView.findViewById(R.id.skyBlue);
        pink = colorView.findViewById(R.id.pink);
        parrot = colorView.findViewById(R.id.parrot);
        orange = colorView.findViewById(R.id.orange);
        purple = colorView.findViewById(R.id.purple);
        red = colorView.findViewById(R.id.red);
        yellow = colorView.findViewById(R.id.yellow);
        green = colorView.findViewById(R.id.darkGreen);


        skyBlue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Sky Blue";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Pink";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        parrot.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Parrot";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Purple";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Green";
                addLogColorData(selectedColor);
                dialog.dismiss();
             }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Red";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Yellow";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Orange";
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void addLogColorData(final String color) {


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                progressDialog.setMessage("wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                final String deviceToken = instanceIdResult.getToken();
                Log.d("deviceToken", "onSuccess: "+deviceToken);


                final StringRequest logCreateRequest = new StringRequest(Request.Method.POST, UrlsContract.CREATE_LOG_URL,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getContext(), "Successfully added your log data", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "error in response "+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("errorMessage", "onErrorResponse: "+error.getMessage());
                        progressDialog.dismiss();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> logData = new HashMap<>();
                        logData.put("building_guid", buildingGuid);
                        logData.put("mobile_device_id", deviceToken);
                        logData.put("detection_decice_hash", deviceToken);
                        logData.put("status", "pending");
                        logData.put("lat", String.valueOf(lat));
                        logData.put("lng", String.valueOf(lng));
                        logData.put("detection_type", color);
                        logData.put("created_by", String.valueOf(uid));
                        return logData;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> tokenMap = new HashMap<>();
                        tokenMap.put("Authorization", "Bearer " + token);

                        return tokenMap;
                    }
                };


                MySingleton.getInstance(getContext()).addToRequestQueue(logCreateRequest);

            }
        });

    }


    /**
     *  add user or assign to user method below
     */
    private void addUserDetails()
    {

        Intent intent = new Intent(getContext() , UserListToAssignBuilding.class);
        intent.putExtra("access_token", token);
        intent.putExtra("building_key", buildingGuid);
        startActivity(intent);
    }


    // to edit the current building details
    private void editBuildingDetails()
    {

        View myView = LayoutInflater.from(getContext()).inflate(R.layout.update_building_details, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(myView);

        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);

        final EditText buildingName , floorNumbers;
        buildingName = myView.findViewById(R.id.buildingName);
        floorNumbers = myView.findViewById(R.id.noOfFloor);

        myView.findViewById(R.id.updateBuildingBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                updateValues(buildingName.getText().toString(), floorNumbers.getText().toString());
                dialog.dismiss();

            }
        });

          dialog.show();


    }


    private void updateValues(final String name  , final String noOfFloor)
    {
        String url = UrlsContract.UPDATE_BUILDING_DETAILS + buildingGuid;

        if(!name.isEmpty() && !noOfFloor.isEmpty()) {

            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            StringRequest updateBuildingRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("updateBuildingResponse", "onResponse: " + response);
                    Toast.makeText(getContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("updateBuildingError", "onErrorResponse: " + error.getMessage());
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> updateBuildingMap = new HashMap<>();
                    updateBuildingMap.put("name", name);
                    updateBuildingMap.put("no_of_floor", noOfFloor);


                    return updateBuildingMap;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> updateHeader = new HashMap<>();
                    updateHeader.put("Authorization", "Bearer " + token);

                    return updateHeader;
                }
            };


            MySingleton.getInstance(getContext()).addToRequestQueue(updateBuildingRequest);

        }
        else
        {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
        }

    }
}
