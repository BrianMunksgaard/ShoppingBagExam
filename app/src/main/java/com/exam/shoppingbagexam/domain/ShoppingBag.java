package com.exam.shoppingbagexam.domain;

import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * This class is used to represent a shopping bag. Items can be added and
 * removed from the bag and the bag can be emptied. For persistence, a
 * Firebase DB is used.
 */
public class ShoppingBag {

    /*
     * The shopping bag implemented with a FirebaseListAdapter.
     */
    private FirebaseListAdapter<Product> adapter;

    /*
     * Top level reference to the Firebase DB.
     */
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

    /*
     * Shopping bag Firebase reference.
     */
    private DatabaseReference mShoppingBagRef;

    /**
     * Initialize new Firebase shopping bag.
     */
    public ShoppingBag(String deviceId) {

        // Reference to all bags collection.
        DatabaseReference bagsRef = mRootRef.child("bags");

        // Reference to current device.
        DatabaseReference deviceRef = bagsRef.child(deviceId);

        // Set the reference to the bag for the current device.
        mShoppingBagRef = deviceRef.child("bag");

        // Connect firebase and adapter.
        Query query = deviceRef.child("bag");
        FirebaseListOptions<Product> options = new FirebaseListOptions.Builder<Product>()
                .setQuery( query , Product. class )
                .setLayout(android.R.layout. simple_list_item_checked )
                .build();

        adapter = new FirebaseListAdapter<Product>(options) {
            @Override
            protected void populateView(View v, Product product, int position) {
                TextView textView = v.findViewById( android.R.id . text1 );
                textView.setTextSize( 24 ); //modify this if you want different size
                textView.setText(product.toString());
            }
        };
    }

    /**
     * @return The adapter used for the shopping bag.
     */
    public FirebaseListAdapter<Product> getShoppingBagAdapter()
    {
        return adapter;
    }

    /**
     * Add product to the shopping bag.
     *
     * @param product
     * @return true if successful, otherwise false.
     */
    public boolean addItemToBag(Product product) {
        try {
            mShoppingBagRef.push().setValue(product);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Add product to the shopping bag.
     *
     * @param itemText
     * @param quantity
     * @return true if successful, otherwise false.
     */
    public boolean addItemToBag(String itemText, int quantity) {
        Product p = new Product(itemText, quantity);
        return addItemToBag(p);
    }

    /**
     * Removes the item at itemPosition from the shopping bag.
     *
     * @param itemPosition
     * @return true if successful, otherwise false.
     */
    public boolean removeItemFromBag(int itemPosition) {
        boolean status = false;

        try {
            if (itemPosition >= 0 && itemPosition < adapter.getCount()) {
                adapter.getRef(itemPosition).setValue(null);
                status = true;
            }
        }
        catch (Exception e) {
            status = false;
        }

        return  status;
    }

    /**
     * Remove all items from the shopping bag.
     *
     * @return true if successful, otherwise false.
     */
    public boolean clearBag() {
        try {
            mShoppingBagRef.removeValue();
            adapter.notifyDataSetChanged();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieves the product at the specified location.
     *
     * @param index
     * @return
     */
    public Product getProduct(int index) {
        Product p = null;
        if (index >= 0 && index < adapter.getCount()) {
            p = adapter.getItem(index);
        }
        return p;
    }

    /**
     * The number of products in the bag.
     *
     * @return
     */
    public int getProductCount() {
        return adapter == null ? 0 : adapter.getCount();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adapter.getCount(); i++) {
            Product p = adapter.getItem(i);
            sb.append(p.toString() + "\n");
        }

        return sb.toString();
    }
}
