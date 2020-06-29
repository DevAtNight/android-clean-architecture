<h1 align='center'>
    Android Clean Architecture
</h1>

<p align="center">
  <a href="https://img.shields.io/badge/Platform-Android%206.0-36da7e?logo=android&style=for-the-badge">
    <img src="https://img.shields.io/badge/Platform-Android%206.0-36da7e?logo=android&style=for-the-badge" alt="Platform" />
  </a>
  <a href="https://img.shields.io/badge/Kotlin-1.3.61-orange?logo=kotlin&style=for-the-badge">
    <img src="https://img.shields.io/badge/Kotlin-1.3.61-orange?logo=kotlin&style=for-the-badge" alt="Language" />
  </a>
  <a href="https://github.com/htdangkhoa/android-clean-architecture/releases">
    <img src="https://img.shields.io/github/v/release/htdangkhoa/android-clean-architecture?style=for-the-badge" alt="Version" />
  </a>
  <a href="https://www.codefactor.io/repository/github/htdangkhoa/android-clean-architecture">
    <img src="https://img.shields.io/codefactor/grade/github/htdangkhoa/android-clean-architecture?style=for-the-badge" alt="CodeFactor" />
	</a>
</p>

![android_clean_architecture](https://raw.githubusercontent.com/htdangkhoa/android-clean-architecture/master/art/android_clean_architecture.svg?sanitize=true)

## Features

- 100% Kotlin.
- App architecture - [(MVVM)](https://developer.android.com/jetpack/docs/guide).
- Asynchronous programming - [(Corountines)](https://kotlinlang.org/docs/reference/coroutines-overview.html).
- Dependency injection - [(Koin)](https://insert-koin.io/).
- Observable data holder class - [(Livedata)](https://developer.android.com/topic/libraries/architecture/livedata).
- HTTP client - [(OkHttp)](https://github.com/square/okhttp).
- Type-safe HTTP client - [(Retrofit)](https://github.com/square/retrofit).
- A Java serialization/deserialization library to convert Java Objects into JSON and back - [(Gson)](https://github.com/google/gson).
- Android logging - [(Timber)](https://github.com/JakeWharton/timber).
- Android SharedPreferences delegation library for Kotlin - [(Kotpref)](https://github.com/chibatching/Kotpref).
- A beautiful, fluid, and extensible dialogs API for Kotlin & Android - [(material-dialogs)](https://github.com/afollestad/material-dialogs).
- Android library which makes playing with sensor events & detecting gestures a breeze - [(Sensey)](https://github.com/nisrulz/sensey).
- Useful extensions to eliminate boilerplate code in Android SDK and focus on productivity - [(KAndroid)](https://github.com/pawegio/KAndroid).
- Android utilities - [(AndroidUtilCode)](https://github.com/Blankj/AndroidUtilCode).
- Code Formatting - [(ktlint)](https://github.com/pinterest/ktlint) & [(ktlint-gradle)](https://github.com/JLLeitschuh/ktlint-gradle).

## Configuration Host

**Manually:**

```gradle
// build.gradle (app)


android {
  ...

  buildTypes {
    def BASE_URL = "BASE_URL"

    def DEV = "\"" + "<ENTER_YOUR_HOST>" + "\""

    def PROD = "\"" + "<ENTER_YOUR_HOST>" + "\""

    debug {
      buildConfigField "String", BASE_URL, DEV
    }

    release {
      buildConfigField "String", BASE_URL, PROD

      ...
    }
  }

  ...
}
```

**Dynamically:**

You can shake your **real** device, or you can use **`Cmd+M`** when running in an Android emulator on Mac OS and **`Ctrl+M`** on Windows and Linux to open dialog menu.

> The dialog menu is disabled in release (production) builds.

<p align="center">
  <img src="https://raw.githubusercontent.com/htdangkhoa/android-clean-architecture/develop/art/development.gif" width="250" alt="Development" />
</p>
