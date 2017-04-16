package com.arny.lubereckiy.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastMaker {

    /**
     * Displays a Toast notification for a short duration.
     *
     * @param context
     * @param message
     */
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays a new Toast notification for a short duration.
     *
     * @param context
     * @param message
     * @param success
     */
    public static void toast(Context context, String message, boolean success) {
        if (success) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error:" + message, Toast.LENGTH_SHORT).show();
        }
    }

}