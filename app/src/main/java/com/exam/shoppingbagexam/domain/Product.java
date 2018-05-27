package com.exam.shoppingbagexam.domain;

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
        setName(name);
        setQuantity​(quantity​);
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
        this.name = name == null ? "" : name;
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
        this.quantity​ =  quantity​ > 0 ? quantity​ : 0;
    }

    /**
     * This implementation returns both product name and quantity.
     */
    @Override
    public String toString() {
        return quantity​ + " " + name;
    }

}
