package com.exam.shoppingbagexam.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class ConfirmCancelSnack {

    /*
     * A flag used to indicate whether or an action should be confirmed
     * or cancelled.
     */
    private boolean confirmed = true;

    /*
     * Call back reference for confirmation.
     */
    private OnConfirmListener confirmCallback = null;

    /*
     * Call back reference for cancellation.
     */
    private OnCancelListener cancelCallback = null;

    /**
     * Define interface to implement if user of
     * snack wants to listen for snack confirmed
     * action.
     */
    public interface OnConfirmListener {
        void onConfirmed();
    }

    /**
     * Define interface to implement if user of
     * snack wants to listen for snack cancel action.
     */
    public interface OnCancelListener {
        void onCancelled();
    }

    /**
     * Set the onConfirmationListener.
     *
     * @param listener
     */
    public void setConfirmCallback(OnConfirmListener listener) {
        confirmCallback = listener;
    }

    /**
     * Show snack.
     *
     * @param parent                Parent view for the snack.
     * @param snackQuestionText     Ask the user this question.
     * @param snackCancelledText    Display this text if the user cancels (hits UNDO) the operation.
     */
    public void showSnack(final View parent, final String snackQuestionText, final String snackCancelledText) {

        confirmed = true;
        Snackbar snackbar = Snackbar
                .make(parent, snackQuestionText, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmed = false;
                        Snackbar snackbar = Snackbar.make(parent, snackCancelledText, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (confirmed) {
                    if(confirmCallback != null) {
                        confirmCallback.onConfirmed();
                    }
                } else {
                    if(cancelCallback != null) {
                        cancelCallback.onCancelled();
                    }
                }
            }

        });
        snackbar.show();
    }

    /**
     * Show snack with confirmation callback listener.
     *
     * @param parent                Parent view for the snack.
     * @param snackQuestionText     Ask the user this question.
     * @param snackCancelledText    Display this text if the user cancels (hits UNDO) the operation.
     * @param callbackListener      Callback lister for positive confirmation.
     */
    public static void showSnack(final View parent, final String snackQuestionText, final String snackCancelledText, OnConfirmListener callbackListener) {
        ConfirmCancelSnack cs = new ConfirmCancelSnack();
        cs.setConfirmCallback(callbackListener);
        cs.showSnack(parent, snackQuestionText, snackCancelledText);
    }
}
