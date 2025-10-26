# CineFiles

<div align="center">
  <img src=".github/screenshots/cinefiles-logo.jpg" alt="CineFiles Logo" width="200"/>
</div>

Android application for managing and viewing movie information.

## 📱 Screenshots

<div align="center">
  <img src=".github/screenshots/cinefile-screen1.png" alt="Login Screen" width="200"/>
  <img src=".github/screenshots/cinefile-screen2.png" alt="Sign Up" width="200"/>
  <img src=".github/screenshots/cinefile-screen3.png" alt="Home" width="200"/>
  <img src=".github/screenshots/cinefile-screen4.png" alt="Movie Details" width="200"/>
</div>

## Developer

Developed by **AndreOids** - Mobile Development Solutions

## Technologies

- Kotlin
- Jetpack Compose
- Android SDK

## Setup

### 1. Get TMDB API Key

1. Create a free account at [The Movie Database](https://www.themoviedb.org/)
2. Go to [API Settings](https://www.themoviedb.org/settings/api) and request an API key
3. Copy your API key (v3 auth)

### 2. Configure local.properties

1. Copy `local.properties.example` to `local.properties`
2. Update `sdk.dir` with your Android SDK path
3. Add your TMDB API key:
```
TMDB_API_KEY=your_api_key_here
```

## How to Run

1. Clone the repository
2. Complete the Setup steps above
3. Open the project in Android Studio
4. Sync Gradle
5. Run on emulator or physical device

## License

This project is licensed under the license specified in the LICENSE file.

