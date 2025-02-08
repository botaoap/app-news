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

## Get Started
Follow the steps below to get the project up and running on your local machine.

### 1. Clone the Project
```sh
git clone https://github.com/botaoap/app-news.git
cd app-news
```

### 2. Create Your Own API Key
To fetch data from the NewsAPI, you need to create your own API key. Here's how you can do it:

#### Go to the NewsAPI website.
- Site [here](https://newsapi.org)
- Sign up for an account (or log in if you already have one).
- Once logged in, navigate to the API Keys section and generate a new key.

### 3. Add Your API Key to the Project
In order to build and run the app locally, you need to add your API key to the project.

- Locate the file called ApiKey.kt inside the config or resources folder (depending on your project structure).
- Replace the placeholder with your own API key in the file. It should look like this:

```sh
const val API_KEY = "your-api-key-here"
```

- Make sure that your API key is securely added and not exposed in public repositories.

### 4. Build and Run the Project
Once the API key is in place, you should be able to build and run the project locally using Android Studio.

- Open the project in Android Studio.
- Make sure that your Android environment is properly set up (e.g., the required SDK version, Android Emulator, or a physical device).
- Build and run the project either on the emulator or a physical device.

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

## Unit Tests
Unit tests are created for the following layers:
- Web Service
- Repository
- Use case
- Mapper
- ViewModel

## Contact
For any questions or suggestions, please contact gabrielbotao@gmail.com
