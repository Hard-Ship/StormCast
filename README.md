<br/>
<p align="center">
  <img src="https://raw.githubusercontent.com/Hard-Ship/StormCast/main/app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" alt="StormCast Logo" width="100"/>
</p>

<h1 align="center">StormCast</h1>

<p align="center">
  <strong>A beautiful, forecast-first weather application for Android.</strong>
</p>

<p align="center">
  <a href="https://github.com/Hard-Ship/StormCast/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License: MIT">
  </a>
  <img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform: Android">
  <img src="https://img.shields.io/badge/Java-17-orange.svg" alt="Language: Java">
</p>

---

## 🌩️ Overview

StormCast is a modern, responsive Android weather app that goes beyond basic current temperatures. Built using the OpenWeatherMap API, StormCast provides real-time local weather tracking, rich animated meteorology icons, and a highly detailed 5-Day forecast broken down into 3-hour intervals.

## ✨ Features

*   **Real-time Conditions**: Instantly view the current temperature, primary weather condition (Cloudy, Sunny, Snow, etc.), and localized time.
*   **Rich Metrics Strip**: A horizontal scrolling strip providing crucial metrics including "Feels Like" temperature, Min/Max temperatures, Atmospheric Pressure, Cloud Cover, Visibility, and detailed local Sunrise/Sunset times.
*   **5-Day / 3-Hour Forecast**: A dedicated navigation tab displays the upcoming week's weather, automatically filtered and presented with localized day, time, expected temperature, and animated Lottie icons matching the forecasted condition.
*   **Dynamic Lottie Animations**: Smooth, high-quality weather animations that bring the UI to life based on the current weather state.
*   **City Search Engine**: Look up the forecast for any city worldwide using the seamless integrated floating search bar.

## 🛠️ Tech Stack

*   **Language**: Java
*   **Architecture**: Fragment-based UI with Bottom Navigation
*   **Networking**: Retrofit2 & Gson (for JSON parsing)
*   **Animations**: Lottie (by Airbnb)
*   **Material Design 3**: Leveraging modern Material Components (Elevated Cards, dynamic colors, etc.)
*   **API**: [OpenWeatherMap API](https://openweathermap.org/) (Current Weather Data & 5 Day / 3 Hour Forecast)

## 🚀 Getting Started

### Prerequisites

*   Android Studio Ladybug (or newer)
*   JDK 17
*   An API Key from [OpenWeatherMap](https://openweathermap.org/api)

### Installation & Build

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Hard-Ship/StormCast.git
    ```
2.  **Open in Android Studio:**
    Open Android Studio and select `File > Open`, then choose the cloned `StormCast` directory.
3.  **Add your API Key:**
    Create or open the `local.properties` file in the root directory (it is git-ignored for your security) and add your key:
    ```properties
    OPENWEATHER_API_KEY="your_api_key_here"
    ```
4.  **Sync and Run:**
    Click the **Sync Project with Gradle Files** button. Once finished, hit **Run** (`Shift + F10`) to deploy to your emulator or physical device.

## 🤝 Contributing

Contributions, issues, and feature requests are always welcome! Feel free to check the [issues page](https://github.com/Hard-Ship/StormCast/issues).

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
