package com.exam.shoppingbagexam;

import android.support.test.runner.AndroidJUnit4;

import com.exam.shoppingbagexam.domain.Product;
import com.exam.shoppingbagexam.domain.ShoppingBag;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test shopping bag functionality.
 */
@RunWith(AndroidJUnit4.class)
public class BagTest {

    /**
     * Test shopping bag CRUD functions.
     */
    @Test
    public void testShoppingBagCRUD() {
        // Context of the app under test.
        // Context appContext = InstrumentationRegistry.getTargetContext();
        ShoppingBag shoppingBag = null;

        try {

            // Start listening.
            shoppingBag = new ShoppingBag("unitTest");
            shoppingBag.getShoppingBagAdapter().startListening();

            // Start with an empty bag.
            shoppingBag.clearBag();
            Thread.sleep(2000);
            assertEquals(0, shoppingBag.getProductCount());

            // Create.
            assertTrue(shoppingBag.addItemToBag("BMW 530d", 2));
            assertTrue(shoppingBag.addItemToBag("Audi A6", 1));

            Thread.sleep(2000);
            assertEquals(2, shoppingBag.getProductCount());

            // Read.
            Product p = shoppingBag.getProduct(1);
            assertEquals("Audi A6", p.getName());
            assertEquals(1, p.getQuantity());

            // Update.
            shoppingBag.removeItemFromBag(0);
            Thread.sleep(2000);
            assertEquals(1, shoppingBag.getProductCount());

            p = shoppingBag.getProduct(0);
            assertEquals("Audi A6", p.getName());
            assertEquals(1, p.getQuantity());

            // Delete.
            shoppingBag.clearBag();
            Thread.sleep(2000);
            assertEquals(0, shoppingBag.getProductCount());

        }

        catch (Exception e) {

        }

        finally {
            // Stop listening.
            if( shoppingBag != null) {
                shoppingBag.getShoppingBagAdapter().stopListening();
            }
        }
    }
}
