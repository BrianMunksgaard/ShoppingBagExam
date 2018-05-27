package com.exam.shoppingbagexam;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Activity for showing the settings fragment
 */
public class SettingsActivity extends Activity {

    /**
     * Method for creating the fragment and make sure it is shown to the user.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ShoppingAppSettingsFragment())
                .commit();

    }
}
