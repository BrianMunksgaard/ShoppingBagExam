package com.exam.shoppingbagexam;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.exam.shoppingbagexam.domain.Product;
import com.exam.shoppingbagexam.domain.ShoppingBag;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BagTest {

    @Test
    public void testShoppingBagCRUD() {
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.exam.shoppingbagexam", appContext.getPackageName());


        // Create.
        {
            ShoppingBag shoppingBag = new ShoppingBag("unitTest");

            assertTrue(shoppingBag.addItemToBag("BMW 530d", 2));
            assertTrue(shoppingBag.addItemToBag("Audi A6", 1));
            assertEquals(2, shoppingBag.getProductCount());
        }

        // Read.
        {
            ShoppingBag shoppingBag = new ShoppingBag("unitTest");
            assertEquals(2, shoppingBag.getProductCount());

            Product p = shoppingBag.getProduct(1);
            assertEquals("Audi A6", p.getName());
            assertEquals(1, p.getQuantity());
        }


        // Update.
        {
            ShoppingBag shoppingBag = new ShoppingBag("unitTest");
            assertEquals(2, shoppingBag.getProductCount());

            shoppingBag.removeItemFromBag(0);

            Product p = shoppingBag.getProduct(0);
            assertEquals("Audi A6", p.getName());
            assertEquals(1, p.getQuantity());
        }

        // Delete.
        {
            ShoppingBag shoppingBag = new ShoppingBag("unitTest");
            shoppingBag.clearBag();
            assertEquals(0, shoppingBag.getProductCount());
        }
    }
}
