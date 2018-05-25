package com.exam.shoppingbagexam;

import com.exam.shoppingbagexam.domain.Product;
import com.exam.shoppingbagexam.domain.ShoppingBag;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShoppingBagTests {

    @Test
    public void TestShoppingBagCRUD() {

        ShoppingBag shoppingBag = new ShoppingBag("unitTest");

        shoppingBag.addItemToBag("BMW 530d", 2);
        shoppingBag.addItemToBag("Audi A6", 1);



    }
}
