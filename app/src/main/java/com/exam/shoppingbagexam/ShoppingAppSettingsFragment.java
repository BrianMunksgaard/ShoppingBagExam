package com.exam.shoppingbagexam;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


/**
 *
 */
public class ShoppingAppSettingsFragment extends PreferenceFragment {

    private static String SETTINGS_AUTOFILLKEY = "autofill";

    public static boolean shouldAutoFill(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SETTINGS_AUTOFILLKEY, true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
