# Country Explorer Android App
A modern Android application built with Jetpack Compose that displays information about countries.

# Features
- Country List: Browse all countries with flag, name, region, and population
- Search Functionality: Search countries by name with real-time filtering
- Country Details: View detailed information including capital, languages, currencies, and timezones.
- Modern UI: Built with Jetpack Compose and Material Design 3

# Tech Stack
- UI Framework: Jetpack Compose
- Architecture: MVVM with Repository Pattern
- Dependency Injection: Dagger Hilt
- Networking: Retrofit + OkHttp
- Serialization: Kotlinx Serialization
- Image Loading: Coil
- Navigation: Navigation Compose
- Async Operations: Kotlin Coroutines + Flow

# Getting Started
1. Prerequisites
- Android Studio Arctic Fox or newer
- JDK 8 or higher
2. Installation
- Clone the repository.
- Open the project in Android Studio
- Sync the project with Gradle files
- Run the app on an emulator or physical device

# Error Handling
The app includes comprehensive error handling:
- Network errors with retry functionality
- Empty state handling
- Loading states with progress indicators
- User-friendly error messages

#  Known Issues
- Large flag images may take time to load on slower connections
- Some country names may be truncated in the card view on smaller screens
