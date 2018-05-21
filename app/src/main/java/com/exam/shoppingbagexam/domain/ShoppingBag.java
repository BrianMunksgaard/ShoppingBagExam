package com.exam.shoppingbagexam.domain;

import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 *
 */
public class ShoppingBag {

    private FirebaseListAdapter<Product> adapter;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mShoppingBagRef = mRootRef.child("bag");

    /**
     *
     */
    public ShoppingBag() {
        Query query = mRootRef.child("bag");

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

    public void addItemToBag(Product product) {
        mShoppingBagRef.push().setValue(new Product(itemText, noOfItems));
    }

    public void addItemToBag(String itemText, int quantity) {
        Product p = new Product(itemText, quantity);
        addItemToBag(p);
    }

    public void clearItemFromBag(int bagPosition) {

    }

    public void clearBag() {

    }
}
