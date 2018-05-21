package com.exam.shoppingbagexam.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ConfirmationSnack {

    private boolean reallyRemoveItem = true;

    /*
     * Call back reference for confirmation.
     */
    private OnConfirmationListener confirmCallback = null;

    /**
     * Define interface to implement if user of
     * dialog wants to handle positive clicks.
     */
    public interface OnConfirmationListener {
        void onConfirmed();
    }

    /**
     * Set the onConfirmationListener.
     *
     * @param listener
     */
    public void setConfirmCallback(OnConfirmationListener listener) {
        confirmCallback = listener;
    }

    /**
     * Show snack.
     *
     * @param parent
     * @param snackQuestionText
     * @param snackCancelledText
     */
    public void showSnack(final View parent, final String snackQuestionText, final String snackCancelledText) {

        reallyRemoveItem = true;
        Snackbar snackbar = Snackbar
                .make(parent, snackQuestionText, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reallyRemoveItem = false;
                        Snackbar snackbar = Snackbar.make(parent, snackCancelledText, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (reallyRemoveItem) {
                    if(confirmCallback != null) {
                        confirmCallback.onConfirmed();
                    }
                }
            }

        });
        snackbar.show();
    }
}
