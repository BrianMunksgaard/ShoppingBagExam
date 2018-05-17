package com.exam.shoppingbagexam.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The product class is used to represent an item
 * in a shopping bag.
 */
public class Product {

    /*
     * Product name.
     */
    private String name;

    /*
     * Product quantity.
     */
    private int quantity​;

    /**
     * Default empty constructor.
     */
    public Product() {}

    /**
     * Product constructor used to set product name and quantity.
     */
    public Product(String name, int quantity​)
    {
        this.name = name;
        this.quantity​ = quantity​;
    }

    /**
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The product quantity.
     */
    public int getQuantity() {
        return quantity​;
    }

    /**
     * Sets the quantity.
     * @param quantity​
     */
    public void setQuantity​(int quantity​) {
        this.quantity​ = quantity​;
    }

    /**
     * This implementation returns both product name and quantity.
     */
    @Override
    public String toString() {
        return name + " " + quantity​;
    }

}
