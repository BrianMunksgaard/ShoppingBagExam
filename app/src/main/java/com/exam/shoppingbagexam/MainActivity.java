package com.exam.shoppingbagexam;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.exam.shoppingbagexam.domain.ShoppingBag;
import com.exam.shoppingbagexam.utils.ConfirmCancelSnack;
import com.exam.shoppingbagexam.utils.YNDialog;

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
     * Reference to the ListView used to
     * display the products in the bag.
     */
    private ListView listView;

    /*
     * The number of the currently checked item in
     * the product list.
     */
    private int currentCheckedItem = -1;

    /*
     * Items for the quantity spinner.
     */
    private String[] spinnerItems = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Initialize shopping bag.
        shoppingBag = new ShoppingBag();

        // Setup list view and connect adapter.
        listView = findViewById(R.id.list);
        listView.setAdapter(shoppingBag.getShoppingBagAdapter());
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        setButtonEventHandlers();
        setProductItemEventHandler();

        // Put numbers 1-9 in the quantity spinner.
        Spinner spinnerQuantity = findViewById(R.id.spinnerQuantity);
        ArrayAdapter<String> spinnerQuantityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        spinnerQuantity.setAdapter(spinnerQuantityAdapter);
    }

    /*
     * Register event handlers for shopping bag buttons.
     */
    private void setButtonEventHandlers() {

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToBag_onClick(v);
            }
        });

        findViewById(R.id.deleteItemButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteItemFromBag_onClick(v);
            }
        });

        findViewById(R.id.deleteAllButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearBag_onClick(v);
            }
        });

    }

    /*
     * Set event handlers for product items.
     */
    private void setProductItemEventHandler() {

        // Updates the currentCheckedItem whenever a product/item in
        // the list is clicked.
        ((ListView)findViewById(R.id.list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentCheckedItem = i;
            }
        });
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
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Add an item and the quantity to the shopping bag.
     *
     * @param view
     */
    public void addToBag_onClick(View view) {

        // Get product item.
        EditText itemRef = findViewById(R.id.item);
        String itemText = itemRef.getText().toString();

        // Only retrieve quantity if there is
        // an item.
        if(!itemText.isEmpty()) {

            // Get quantity from spinner.
            Spinner spinnerQuantity = findViewById(R.id.spinnerQuantity);
            int quantityFromSpinner = spinnerQuantity.getSelectedItemPosition() + 1;

            // Get quantity from edit text.
            EditText quantityRef = findViewById(R.id.itemQuantity);
            String quantityText = quantityRef.getText().toString();

            // Determine the number of items.
            int noOfItems = quantityFromSpinner;
            if(!quantityText.isEmpty()) {
                noOfItems = Integer.valueOf(quantityText);
            }

            // Add item and quantity to bag.
            shoppingBag.addItemToBag(itemText, noOfItems);

            // Reset input fields.
            itemRef.getText().clear();
            quantityRef.getText().clear();
            spinnerQuantity.setSelection(0);
        }
    }

    /**
     * Delete the currently selected item from the shopping bag.
     * The user is presented with a snackbar allowing the delete
     * operation to be cancelled.
     *
     * @param view
     */
    public void deleteItemFromBag_onClick(View view) {

        // Get the name of the product we are about to delete.
        final String productName = shoppingBag.getProduct(currentCheckedItem).getName();

        // Hide the keyboard.
        hideKeyboard(view);

        // Show snack allowing user to cancel deletion.
        String snackQuestionText = String.format("Really remove %s?", productName);
        String snackCancelledText = String.format("Remove of %s cancelled!", productName);
        View parent = findViewById(R.id.layout);
        ConfirmCancelSnack.showSnack(parent, snackQuestionText, snackCancelledText, new ConfirmCancelSnack.OnConfirmListener() {
            @Override
            public void onConfirmed() {
                shoppingBag.removeItemFromBag(currentCheckedItem);
            }
        });
    }

    /**
     * Clear the shopping bag.
     *
     * @param view
     */
    public void clearBag_onClick(View view) {

        // Use dialog to confirm clear bag.
        YNDialog dialog = new YNDialog();
        dialog.setPositiveCallback(new YNDialog.OnPositiveListener() {

            @Override
            public void onPositiveClicked() {
                shoppingBag.clearBag();
            }
        });

        dialog.show(getFragmentManager(), "YNFragment");
    }

    /*
     * Hide the keyboard.
     */
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
