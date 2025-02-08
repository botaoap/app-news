# News App Challenge
<div>
  <a href="https://github.com/botaoap/app-news">
  <img src="https://img.shields.io/github/repo-size/botaoap/app-news">
  </a>
</div>

## About the Challenge:

Given the short deadline, prioritize quality. Even if you cannot complete all the requirements, deliver what you can in the most polished way possible.

## Requirements:

- You must create a news app to display the top headlines from BBC News (bbc-news).
- API Documentation: NewsAPI Top Headlines
- Do not spend too much time on complex layouts, but the app must support different screen sizes and orientations.
- The app must be written in Kotlin (preferably) or Java.
- The app must include unit tests.
- The app must run without requiring modifications to the codebase by the reviewer.
- Upon opening, the app should display a screen showing the top headlines from a specific news provider.
- The name of the news provider should be displayed as the screen title.
- The headlines should be presented in a list format.
- Each list item should display the headline title and image (if available).
- Download and cache the images, do not include them in the package.
- Headlines should be sorted by date.
- The user should be able to scroll through the list of headlines.
- When a headline is tapped, a new screen should open displaying:
- Image, title, and description (if available).

## Delivery:

The challenge can be submitted via GitHub, Bitbucket, or similar platforms.

### Bonus:

Use flavors to support two different news providers.

## Prerequisites:

- Before building and running this project, ensure you have the following installed and set up:
- Android Studio (latest stable version recommended)
- Android SDK and required dependencies
- Kotlin plugin enabled in Android Studio
- Gradle properly configured
- An active API key from NewsAPI
- Internet connection for fetching news data
- A physical or virtual Android device with API level 21 or higher

## Technologies Used:

- This project utilizes the following libraries and frameworks:
- Shimmer: For loading placeholders
- Glide: For image loading and caching
- Retrofit: For making API requests
- OkHttp: For handling network requests
- Koin: For dependency injection
- Mockito: For mocking dependencies in tests
- Mockk: For Kotlin-specific mocking
- Coroutines Unit Test: For testing coroutine-based code
- ViewModel Unit Test: For testing ViewModel logic
- OkHttp Unit Test: For testing network interactions

## Architecture:

- This project follows the MVVM (Model-View-ViewModel) architecture, ensuring a clear separation of concerns and maintainability. The main layers include:
- UI Layer: Handles user interactions and displays data.
- ViewModel Layer: Manages UI-related data and business logic.
- Domain Layer: Contains use cases and business rules.
- Data Layer: Manages API requests and local data storage.
- Service Layer: Handles API communication using Retrofit and OkHttp.
- Dependency Injection (DI) Layer: Uses Koin for dependency management.
