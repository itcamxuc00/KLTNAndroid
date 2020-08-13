package com.example.dooftsaf.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dooftsaf.R;
import com.example.dooftsaf.ui.common.Common;
import com.example.dooftsaf.ui.service.MainService;
import com.example.dooftsaf.ui.ui.history.HistoryFragment;
import com.example.dooftsaf.ui.ui.home.HomeFragment;
import com.example.dooftsaf.ui.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main2);
        Intent myIntent = new Intent(MainActivity.this, MainService.class);
        // Call startService with Intent parameter.
        this.startService(myIntent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HistoryFragment()).commit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        if(Common.curentOrder==null)  fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new SlideshowFragment()).commit();
                        else fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                        break;
                    case R.id.nav_gallery:
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HistoryFragment()).commit();
                        break;
                    case R.id.nav_slideshow:
                        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new SlideshowFragment()).commit();
                        break;
                    case R.id.nav_logout:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}