package com.exam.shoppingbagexam;

import android.view.View;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher used to compare ListView properties.
 */
public class ListViewMatchers {

    /**
     * Compare list size.
     *
     * @param size
     * @return
     */
    public static Matcher<View> withListSize (final int size) {

        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("ListView should have " + size + " items");
            }
        };
    }
}