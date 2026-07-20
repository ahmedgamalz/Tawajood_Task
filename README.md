# News App

## Architecture

The app follows **Clean Architecture** with the **MVVM** pattern.

* `presentation` – UI and ViewModels
* `domain` – Use Cases and Repository interfaces
* `data` – Repository implementation, Retrofit, Room, and Mappers

## How to Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Add your API key to the `local.properties` file:

   ```properties
   NEWS_API_KEY=your_key_here
   ```
4. Build and run the app.

Gradle injects the key as `BuildConfig.NEWS_API_KEY`.

## Demo

https://drive.google.com/file/d/1Tug243Z0rjERfVolgi-ycgJiPUrv7ql2/view?usp=sharing
