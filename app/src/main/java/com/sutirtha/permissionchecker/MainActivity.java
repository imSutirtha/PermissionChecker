package com.sutirtha.permissionchecker;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String[] locationPermissions = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private String[] mediaPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔄 Dynamically decide which permissions to request based on Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33+)
            mediaPermissions = new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.RECORD_AUDIO
            };
        } else {
            // Android 12 and below
            mediaPermissions = new String[] {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
            };
        }

        // Button to check location permissions
        Button btnLocationPermission = findViewById(R.id.btn_location_permission);
        btnLocationPermission.setOnClickListener(v -> requestLocationPermissions());

        // Button to check media and audio permissions
        Button btnMediaPermission = findViewById(R.id.btn_media_permission);
        btnMediaPermission.setOnClickListener(v -> requestMediaPermissions());
    }

    // Method to check location permissions
    private void requestLocationPermissions() {
        Permissions.check(
                this,
                locationPermissions,
                "Location permission is required to access location-based features.",
                new Permissions.Options()
                        .setRationaleDialogTitle("Location Permission Required")
                        .setSettingsDialogMessage("Go to settings to grant location permissions."),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "Location permissions granted! ✅", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "Location Permission Denied: " + deniedPermissions, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onBlocked(Context context, ArrayList<String> blockedPermissions) {
                        Toast.makeText(context, "Location Permission Blocked: " + blockedPermissions, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );
    }

    // Method to check media permissions (storage, read media images, audio)
    private void requestMediaPermissions() {
        Permissions.check(
                this,
                mediaPermissions,
                "Camera, media, and audio access are required to scan QR codes and access files.",
                new Permissions.Options()
                        .setRationaleDialogTitle("Media Permissions Required")
                        .setSettingsDialogMessage("Go to settings to grant media permissions."),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "All media permissions granted! ✅", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        Toast.makeText(context, "Media Permissions Denied: " + deniedPermissions, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public boolean onBlocked(Context context, ArrayList<String> blockedPermissions) {
                        Toast.makeText(context, "Media Permissions Blocked: " + blockedPermissions, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
        );
    }
}