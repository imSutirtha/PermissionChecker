🔐 Enhanced Permission Checker
==============================

A lightweight, elegant & easy-to-use Android runtime permission handling library.
**No more boilerplate permission code!** 🙌

Badges:
- Platform: Android
- Language: Java
- License: MIT

✨ Features
-----------
- 🚀 Simple API for runtime permission checks
- 📢 Built-in rationale dialog support
- 🔒 Detects "Don't ask again" state
- ⚙️ Easy redirection to app settings
- 🧼 Clean Java-only implementation (works in legacy apps)
- 💡 Custom callbacks for `Granted`, `Denied`, `Blocked`, and `JustBlocked`

📦 Installation
---------------

Step 1: Add JitPack to your root `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2: Add the dependency:

```groovy
dependencies {
    implementation 'com.github.imSutirtha:PermissionChecker:1.0.0'
}
```

🧩 Basic Usage
--------------

```java
String[] permissions = { Manifest.permission.ACCESS_FINE_LOCATION };

Permissions.check(this, permissions, "We need location to proceed",
    new Permissions.Options()
        .setRationaleDialogTitle("Permission Required")
        .setSettingsDialogTitle("Permission Denied")
        .setSettingsDialogMessage("Location is required. Please enable it from Settings."),
    new PermissionHandler() {
        @Override
        public void onGranted() {
            // Permission granted!
        }

        @Override
        public void onDenied(Context context, ArrayList<String> deniedPermissions) {
            // Handle denial
        }
    }
);
```

⚙️ Advanced Options
-------------------

```java
Permissions.Options options = new Permissions.Options()
    .setRationaleDialogTitle("Why We Need This")
    .setSettingsDialogTitle("Permission Blocked")
    .setSettingsDialogMessage("This permission is necessary. Please enable it from Settings.")
    .sendDontAskAgainToSettings(true) // default: true
    .createNewTask(true); // default: false
```

🧠 Callback Methods
-------------------

| Method | Description |
|--------|-------------|
| `onGranted()` | All permissions granted |
| `onDenied(Context, List)` | At least one denied |
| `onBlocked(Context, List)` | Marked “Don’t ask again” |
| `onJustBlocked(Context, List, List)` | Newly marked “Don’t ask again” |

📋 Requirements
---------------

- Minimum SDK: 28
- Language: Java
- Core Dependencies: AppCompat, Material Components

📄 License
----------

MIT License

```
Permission is hereby granted, free of charge, to any person obtaining a copy...
```

🙌 Contributing
---------------

Pull requests are welcome!  
Open an issue for bugs, suggestions or feature requests.  
If you found this helpful — 🌟 star the repo and share it!
