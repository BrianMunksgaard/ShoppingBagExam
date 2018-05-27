package com.exam.shoppingbagexam;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Handles messages from Firebase Cloud Messaging.
 */
public class ShoppingAppMessagingService extends FirebaseMessagingService {

    /*
     * Call back reference used when messages are received.
     */
    private static OnCloudMessageReceivedListener onCloudMessageReceivedCallback = null;

    /**
     * Define interface to implement messages
     * should be handled elsewhere.
     */
    public interface OnCloudMessageReceivedListener {
        void onCloudMessageReceived(String msg);
    }

    /**
     * Set the callback reference in order to receive and handle
     * messages elsewhere.
     *
     * @param callback
     */
    public static void setOnCloudMessageReceivedCallback(OnCloudMessageReceivedListener callback) {
        onCloudMessageReceivedCallback = callback;
    }

    /**
     * Handle messages from Firebase. Generates a new message
     * and invokes the OnCloudMessageReceivedListener.
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String message = String.format("%s says: '%s'.", remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
        if (onCloudMessageReceivedCallback != null) {
            onCloudMessageReceivedCallback.onCloudMessageReceived(message);
        }
    }
}