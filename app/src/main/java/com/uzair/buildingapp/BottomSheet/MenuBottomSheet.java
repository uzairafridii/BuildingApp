package com.uzair.buildingapp.BottomSheet;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.uzair.buildingapp.R;
import com.uzair.buildingapp.SingletonVolley.MySingleton;
import com.uzair.buildingapp.Utils.UrlsContract;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {


    private ImageView addUser, addLog, edit;
    private double lat, lng;
    private String token, selectedColor;
    private View menuView;
    private ProgressDialog progressDialog;

    public MenuBottomSheet() {
    }

    public MenuBottomSheet(double lat, double lng, String token) {
        // Required empty public constructor
        this.lat = lat;
        this.lng = lng;
        this.token = token;
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
                Toast.makeText(getContext(), "Edit click current location is " + lat + "\n" + lng, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    // add log details
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
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Pink";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        parrot.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Parrot";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Purple";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Green";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
             }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Red";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Yellow";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                selectedColor = "Orange";
                Toast.makeText(getContext(), selectedColor, Toast.LENGTH_SHORT).show();
                addLogColorData(selectedColor);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    /// to add color and all other details
    private void addLogColorData(final String color) {


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

                progressDialog.setMessage("wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                final String deviceToken = instanceIdResult.getToken();
                Log.d("deviceToken", "onSuccess: "+deviceToken);


                final StringRequest logCreateRequest = new StringRequest(Request.Method.POST, UrlsContract.CREATE_LOG_URLS,
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
                        String uniqueId = UUID.randomUUID().toString();
                        logData.put("building_guid", uniqueId);
                        logData.put("mobile_device_id", deviceToken);
                        logData.put("detection_decice_hash", deviceToken);
                        logData.put("status", "pending");
                        logData.put("lat", String.valueOf(lat));
                        logData.put("lng", String.valueOf(lng));
                        logData.put("detection_type", color);


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

    // to add user details
    private void addUserDetails() {


    }
}
