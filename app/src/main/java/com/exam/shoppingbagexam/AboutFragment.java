package com.exam.shoppingbagexam;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment used for showing the about page for the shopping list app.
 */
public class AboutFragment extends Fragment {

    /**
     * Will create the About view based on the layout specified in the fragment_about.xml file.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //This corresponds to the onCreate that we have in our
        //normal activities
        View v = inflater.inflate(R.layout.fragment_about,container,false);

        return v;
    }

}
