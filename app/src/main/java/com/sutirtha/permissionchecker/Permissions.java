package com.sutirtha.permissionchecker;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Permission request entry point. Call `Permissions.check(...)` to initiate the flow.
 */
public class Permissions {

    public static final String TAG = "Permissions";
    public static boolean loggingEnabled = false;

    /**
     * Check permissions using minimal API.
     */
    public static void check(Activity activity, String[] permissions, PermissionHandler handler) {
        check(activity, permissions, null, new Options(), handler);
    }

    /**
     * Check permissions with a rationale message.
     */
    public static void check(Activity activity, String[] permissions, String rationale,
                             PermissionHandler handler) {
        check(activity, permissions, rationale, new Options(), handler);
    }

    /**
     * Check permissions with full options and custom handler.
     */
    public static void check(Activity activity, String[] permissions, String rationale,
                             Options options, PermissionHandler handler) {
        Intent intent = new Intent(activity, PermissionsActivity.class);
        intent.putExtra("permissions", permissions);
        intent.putExtra("rationale", rationale);
        intent.putExtra("options", options);
        PermissionsActivity.handler = handler;
        activity.startActivity(intent);
    }

    public static void log(String message) {
        if (loggingEnabled) {
            Log.d(TAG, message);
        }
    }

    /**
     * Optional configuration wrapper for extra behavior.
     */
    public static class Options implements java.io.Serializable {
        public String rationaleDialogTitle = "Permission Required";
        public String settingsDialogTitle = "Permission Denied";
        public String settingsDialogMessage = "Some permissions are permanently denied. Open settings to grant them manually?";
        public boolean sendDontAskAgainToSettings = false;
        public boolean createNewTask = false;

        public Options setRationaleDialogTitle(String title) {
            this.rationaleDialogTitle = title;
            return this;
        }

        public Options sendDontAskAgainToSettings(boolean sendToSettings) {
            this.sendDontAskAgainToSettings = sendToSettings;
            return this;
        }

        public Options setSettingsDialogTitle(String title) {
            this.settingsDialogTitle = title;
            return this;
        }

        public Options setSettingsDialogMessage(String message) {
            this.settingsDialogMessage = message;
            return this;
        }

        public Options setCreateNewTask(boolean newTask) {
            this.createNewTask = newTask;
            return this;
        }
    }
}