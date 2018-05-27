package com.exam.shoppingbagexam;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.Espresso;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Test UI on shopping bag.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class BagUITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * Test shopping bag CRUD on UI.
     *
     * @throws Exception
     */
    @Test
    public void testUICrud() throws Exception {

        // Start with en empty bag.
        Espresso.onView(withId(R.id.deleteAllButton)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Thread.sleep(3000);

        // Add two of BMW 530d to the bag.
        Espresso.onView(withId(R.id.item)).perform(typeText("BMW 530d"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.spinnerQuantity)).perform(click());
        Espresso.onData(allOf(is(instanceOf(String.class)), is("2"))).perform(click());
        Espresso.onView(withId(R.id.addButton)).perform(click());

        // Add one Audi A6 to the bag.
        Espresso.onView(withId(R.id.item)).perform(typeText("Audi A6"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addButton)).perform(click());
        Thread.sleep(3000);

        // Select the first item in list and remove it.
        Espresso.onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).perform(click());
        Espresso.onView(withId(R.id.deleteItemButton)).perform(click());
        Thread.sleep(3000);

        Espresso.onData(anything()).inAdapterView(withId(R.id.list)).atPosition(0).perform(click());
        Espresso.onView(withId(R.id.list)).check(ViewAssertions.matches (ListViewMatchers.withListSize(1)));
        Thread.sleep(3000);

        // Clear bag.
        Espresso.onView(withId(R.id.deleteAllButton)).perform(click());
        Espresso.onView(withId(android.R.id.button1)).perform(click());
        Thread.sleep(3000);
    }
}
