# SSB Preparation App

A comprehensive Android application designed to help candidates prepare for the Services Selection Board (SSB) examination.

## Features

### Current Implementation

- **Login System**: Secure login with username and password validation
- **Home Screen**: Dashboard with 4 clickable sections:
  - Screening
  - Ground Tasks
  - Psychology
  - Interview
- **User Session Management**: Login state persistence using SharedPreferences
- **Logout Functionality**: Secure logout with session clearing

## Project Structure

```
SSBPrep/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/ssbprep/activities/
│   │       │   ├── LoginActivity.java
│   │       │   └── HomeActivity.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_login.xml
│   │       │   │   └── activity_home.xml
│   │       │   ├── values/
│   │       │   │   ├── colors.xml
│   │       │   │   ├── strings.xml
│   │       │   │   └── themes.xml
│   │       │   └── drawable/
│   │       │       ├── button_background.xml
│   │       │       ├── edit_text_background.xml
│   │       │       └── block_*.xml (color blocks)
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Requirements

- Android SDK 24+
- Gradle 8.0+
- Android Studio or equivalent IDE

## Technologies Used

- Android SDK
- Java
- Material Design Components
- SharedPreferences for local storage

## Building & Running

1. Open the project in Android Studio
2. Build using Gradle: `./gradlew build` (or `gradlew.bat build` on Windows)
3. Run the app on an emulator or device: `./gradlew installDebug`

## Next Steps

This is the foundation. The following features will be added:

- Individual screens for each section (Screening, Ground Tasks, Psychology, Interview)
- Content and quiz modules within each section
- Progress tracking
- Results analysis
- User profile management
- Database integration for storing user progress

## Color Scheme

- **Screening**: Red (#FF6B6B)
- **Ground Tasks**: Teal (#4ECDC4)
- **Psychology**: Blue (#45B7D1)
- **Interview**: Light Salmon (#FFA07A)

## License

This project is created for educational purposes.
