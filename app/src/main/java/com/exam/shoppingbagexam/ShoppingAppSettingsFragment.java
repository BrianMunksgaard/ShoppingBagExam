package com.exam.shoppingbagexam;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


/**
 * Fragment used to handle application settings for the
 * shopping app.
 */
public class ShoppingAppSettingsFragment extends PreferenceFragment {

    /*
     * Keys for various settings.
     */
    private static String SETTINGS_CLEARBAGONSTARTUP = "clearBagOnStartup";

    /**
     * Whether or not to clear the shopping bag when the application starts.
     *
     * @param context
     * @return True if the bag should be cleared, otherwise false.
     */
    public static boolean getClearBagOnStartup(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SETTINGS_CLEARBAGONSTARTUP, false);
    }

    /**
     * Load preferences/settings.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
