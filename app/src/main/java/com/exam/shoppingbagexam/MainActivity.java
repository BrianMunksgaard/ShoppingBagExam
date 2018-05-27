package com.exam.shoppingbagexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.exam.shoppingbagexam.domain.ShoppingBag;


/**
 * The main function of the main activity is to handle intial setup and
 * the application navigation (loading the correct fragments).
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /*
     * The current context (this).
     */
    private Context context;

    /*
     * Reference to the shopping bag.
     */
    private ShoppingBag shoppingBag;

    /*
     * Return code from preferences.
     */
    private final int RESULT_CODE_PREFERENCES = 1;

    /**
     * The id for the fragment that is currently being shown. This is used for making sure that the
     * same fragment is shown if the application changes rotation.
     */
    private int currentFragmentId;

    /**
     * Peform application initialization and handle startup.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        // Display messages from Firebase in a toast.
        ShoppingAppMessagingService.setOnCloudMessageReceivedCallback(new ShoppingAppMessagingService.OnCloudMessageReceivedListener() {
            @Override
            public void onCloudMessageReceived(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        });

        if (savedInstanceState != null) {
            currentFragmentId = savedInstanceState.getInt("currentFragment");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize shopping bag.
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        shoppingBag = new ShoppingBag(deviceId);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // doing the fragment transaction here - replacing frame with HomeFragment -
        //which is the startup fragment in the app.

        Fragment fragment;
        switch (currentFragmentId) {
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new ShoppingListFragment();
                ((ShoppingListFragment)fragment).setShoppingBag(shoppingBag);
                currentFragmentId = R.id.nav_list;
                break;
        }
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit(); //set starting fragment

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Making sure that we store the currently shown fragment for rotation changes.
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentFragment", currentFragmentId);
    }

    /**
     * Tell shopping bag to listen for
     * Firebase events.
     */
    @Override protected void onStart() {
        super .onStart();
        shoppingBag.getShoppingBagAdapter().startListening();
    }

    /**
     * Tell shpping bag to stop listening for
     * Firebase events.
     */
    @Override protected void onStop() {
        shoppingBag.getShoppingBagAdapter().stopListening();
        super .onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    /**
     * Handle action bar item clicks.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, RESULT_CODE_PREFERENCES);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle the action when users click on items in the navigation drawer.
     * This method will make sure the correct fragment is shown corresponding to the item being
     * clicked.
     *
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //Checking if the item is in checked state or not, if not make it in checked state
        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Check to see which item was being clicked and perform appropriate action
        Fragment fragment = null;
        String title= "";

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_about:
                // Show the about fragment
                fragment = new AboutFragment();
                title = getResources().getString(R.string.nav_about);
                currentFragmentId = id;
                break;
            case R.id.nav_list:
                // Show the shopping bag list
                fragment = new ShoppingListFragment();
                ((ShoppingListFragment)fragment).setShoppingBag(shoppingBag);
                title = getResources().getString(R.string.nav_shopping_list);
                currentFragmentId = id;
                break;
            case R.id.nav_share:
                // Share the shopping bag through an implicit intent
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "ShoppingList");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shoppingBag.toString());
                startActivity(Intent.createChooser(sharingIntent, "Share shoppinglist"));
                break;
            case R.id.nav_crash:
                // Force a crash of the app
                Crashlytics.getInstance().crash();
                break;
        }

        // Show the correct fragment if needed
        if (fragment != null) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title); //set the title of the action bar
        }

        //Closing drawer on item click
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
