# NoteMaster

NoteMaster is an advanced Android note-taking application designed for university students,
professionals, and anyone needing a robust tool to create, edit, and organize notes. Built with
modern Android technologies, it offers a seamless user experience with features like location-based
tagging, offline storage, cloud synchronization, and a sleek UI powered by Jetpack Compose.

## Features

- **User Authentication**: Secure login and signup using Firebase Authentication.
- **Note Management**: Create, edit, and categorize notes with a modern, intuitive interface.
- **Location Tagging**: Tag notes with locations using Google Maps API.
- **Offline Support**: Store notes locally using Room database for offline access.
- **Cloud Sync**: Sync notes across devices with Firebase Firestore.
- **Smooth Animations**: Enhanced user experience with Lottie animations.
- **Scalable Architecture**: Built with Clean Architecture and MVVM for maintainability.

## Problem Solved

NoteMaster addresses the challenges of disorganized note-taking and lack of cross-device
synchronization. It provides a feature-rich solution with offline access, cloud backup, and
location-based organization, making it ideal for students and professionals.

## Target Users

- University students
- Professionals
- Anyone needing a robust, feature-rich note-taking tool

## Tech Stack

- **Frontend**: Android (Kotlin), Jetpack Compose, Lottie animations
- **Backend**: Room (local database), Retrofit (API calls), Firebase (authentication and sync)
- **APIs**: Firebase Authentication, Firestore
- **Architecture**: Clean Architecture, MVVM
- **Tools**: Android Studio, GitHub, Trello, Google Meet

## Prerequisites

Before you begin, ensure you have the following installed:

- Android Studio (latest stable version)
- Kotlin (v1.9 or higher)
- Gradle (compatible with your Android Studio version)
- A Firebase project with:
    - Firebase Authentication enabled (Email/Password provider)
    - Firestore Database configured
- A Google Cloud project with Google Maps API enabled

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/notemaster.git
   cd notemaster
   ```

2. **Set up Firebase**:
    - Download the `google-services.json` file from your Firebase project and place it in the `app/`
      directory.
    - Ensure Firebase Authentication and Firestore are enabled in the Firebase Console.

3. **Set up Google Maps API**:
    - Obtain a Google Maps API key from the Google Cloud Console.
    - Add the API key to your `local.properties` file:
      ```properties
      MAPS_API_KEY=your-api-key
      ```
    - Ensure the key is referenced in your `AndroidManifest.xml`.

4. **Build and run**:
    - Open the project in Android Studio.
    - Sync the project with Gradle.
    - Build and run the app on an emulator or physical device (API 21 or higher).

## Usage

1. **Sign Up / Log In**:
    - Create a new account or log in using your email and password via Firebase Authentication.
2. **Create and Edit Notes**:
    - Use the Jetpack Compose UI to create, edit, and categorize notes.
    - Add tags or location data using the Google Maps integration.
3. **Organize Notes**:
    - Categorize notes and view them in a clean, organized list.
4. **Offline and Sync**:
    - Notes are saved locally with Room for offline access and synced to Firestore when online.

## Sprint Planning

- **Sprint Length**: 4 days
- **Tools**: Trello for task management, Google Meet for team collaboration
