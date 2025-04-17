package com.sutirtha.permissionchecker;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * Transparent activity used for requesting permissions in a generic way.
 */
public class PermissionsActivity extends Activity {

    // Change handler to public or protected to allow access from other classes
    public static PermissionHandler handler;

    private String[] permissions;
    private Permissions.Options options;
    private boolean blockedShown = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve data passed from the calling activity
        permissions = getIntent().getStringArrayExtra("permissions");
        options = (Permissions.Options) getIntent().getSerializableExtra("options");
        handler = (PermissionHandler) getIntent().getSerializableExtra("handler");

        // Show rationale if available
        String rationale = getIntent().getStringExtra("rationale");
        if (rationale != null && !rationale.trim().isEmpty()) {
            showRationale(rationale);
        } else {
            requestPermissions();
        }
    }

    // Request the permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, 101);
    }

    // Show rationale dialog to the user
    private void showRationale(String rationale) {
        new AlertDialog.Builder(this)
                .setTitle(options.rationaleDialogTitle)
                .setMessage(rationale)
                .setPositiveButton("OK", (dialog, which) -> requestPermissions())
                .setNegativeButton("Cancel", (dialog, which) -> {
                    if (handler != null) handler.onDenied(this, arrayToList(permissions));
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    // Handle the result of the permissions request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] results) {
        if (requestCode == 101) {
            ArrayList<String> denied = new ArrayList<>();
            ArrayList<String> blocked = new ArrayList<>();
            ArrayList<String> justBlocked = new ArrayList<>();

            // Check which permissions were denied or blocked
            for (int i = 0; i < perms.length; i++) {
                if (results[i] == PackageManager.PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, perms[i])) {
                        denied.add(perms[i]);
                    } else {
                        blocked.add(perms[i]);
                    }
                }
            }

            // Handle the result based on permission state
            if (!denied.isEmpty()) {
                if (handler != null) handler.onDenied(this, denied);
            }
            if (!blocked.isEmpty()) {
                if (handler != null) handler.onBlocked(this, blocked);
            }
            finish();
        }
    }

    private ArrayList<String> arrayToList(String[] array) {
        ArrayList<String> list = new ArrayList<>();
        for (String item : array) {
            list.add(item);
        }
        return list;
    }
}