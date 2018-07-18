package com.example.tadje.myapplication.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.tadje.myapplication.R;
import com.example.tadje.myapplication.Util;
import com.example.tadje.myapplication.Persistence.AppDatabase;

import java.io.File;


public class MainActivity extends AppCompatActivity
        implements
        CalcDateFragment.OnFragmentInteractionListener,
        WelcomeFragment.OnFragmentInteractionListener,
        HolidayFragment.OnFragmentInteractionListener,
        EmployeeFragment.OnFragmentInteractionListener,
        EmployeeHolidayFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener

{

    private FloatingActionButton deleteButton;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToFragment(new WelcomeFragment());


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "/HolidayData");
        if (!folder.exists()) {
            Util.copyAssets(getAssets());
        }

        this.deleteButton = findViewById(R.id.deleteFAB);

        Context context = this;

        //Initialization of the database otherwise the app crashes
        AppDatabase.getInstance(context);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_intervall) {

            setToFragment(new CalcDateFragment());


        } else if (id == R.id.nav_welcome) {

            setToFragment(new WelcomeFragment());

        } else if (id == R.id.nav_holidays) {

            setToFragment(new HolidayFragment());

        } else if (id == R.id.nav_employee) {

            setToFragment(new EmployeeFragment());

        } else if (id == R.id.nav_employeeholiday) {

            setToFragment (new EmployeeHolidayFragment());

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setToFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public FloatingActionButton getDeleteButton() {
        return this.deleteButton;
    }
}