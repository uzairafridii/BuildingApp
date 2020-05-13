package com.uzair.buildingapp.HomeDashBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.uzair.buildingapp.Building.BuildingList;
import com.uzair.buildingapp.LoginAndSignUp.LoginActivity;
import com.uzair.buildingapp.R;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initViews();
    }


    private void initViews()
    {
        token = getIntent().getStringExtra("loginToken");
        Log.d("tokenInBuildingList", "onNavigationItemSelected: "+token);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout();
    }

    private void drawerLayout()
    {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this , drawerLayout ,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(this , LoginActivity.class));
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //on toggle button the navigation drawer action
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.building:
            {
                Log.d("tokenInBuildingList", "onNavigationItemSelected: "+token);
                Intent buildingIntent = new Intent(this , BuildingList.class);
                buildingIntent.putExtra("loginToken" , token);
                startActivity(buildingIntent);
                break;

            }
        }

        return false;
    }
}

