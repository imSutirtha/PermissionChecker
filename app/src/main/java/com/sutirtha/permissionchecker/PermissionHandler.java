package com.sutirtha.permissionchecker;


import android.content.Context;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * The class for handling permission callbacks.
 * <p>
 * Created on 17/4/2025 01:43PM
 * @author Sutirtha Guha Majumder
 */
public abstract class PermissionHandler {

    public abstract void onGranted();

    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
        if (Permissions.loggingEnabled) {
            StringBuilder builder = new StringBuilder("Denied:");
            for (String permission : deniedPermissions) {
                builder.append(" ").append(permission);
            }
            Permissions.log(builder.toString());
        }
        Toast.makeText(context, "Permission Denied.", Toast.LENGTH_SHORT).show();
    }

    public boolean onBlocked(Context context, ArrayList<String> blockedList) {
        if (Permissions.loggingEnabled) {
            StringBuilder builder = new StringBuilder("Set not to ask again:");
            for (String permission : blockedList) {
                builder.append(" ").append(permission);
            }
            Permissions.log(builder.toString());
        }
        return false;
    }

    public void onJustBlocked(Context context, ArrayList<String> justBlockedList,
                              ArrayList<String> deniedPermissions) {
        if (Permissions.loggingEnabled) {
            StringBuilder builder = new StringBuilder("Just set not to ask again:");
            for (String permission : justBlockedList) {
                builder.append(" ").append(permission);
            }
            Permissions.log(builder.toString());
        }
        onDenied(context, deniedPermissions);
    }
}