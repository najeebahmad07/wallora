# 📱 Wallora — Premium Wallpaper & Image Discovery Android App

<p align="center">
  <img src="app/src/main/res/drawable/ic_launcher_foreground.xml" width="128" height="128" alt="Wallora Emblem" />
</p>

<p align="center">
  <strong>Discover. Download. Personalise.</strong>
</p>

<p align="center">
  <em>Developed By Najeeb</em>
</p>

<p align="center">
  <a href="https://kotlinlang.org"><img src="https://img.shields.io/badge/Language-Kotlin%202.2-blue?logo=kotlin" alt="Kotlin"></a>
  <a href="https://developer.android.com/jetpack/compose"><img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose" alt="Jetpack Compose"></a>
  <a href="https://m3.material.io"><img src="https://img.shields.io/badge/Design-Material%203-6750A4?logo=materialdesign" alt="Material 3"></a>
  <a href="https://unsplash.com/developers"><img src="https://img.shields.io/badge/API-Unsplash%20REST-000000?logo=unsplash" alt="Unsplash API"></a>
  <a href="https://github.com/najeebahmad07/wallora"><img src="https://img.shields.io/badge/GitHub-najeebahmad07%2Fwallora-181717?logo=github" alt="GitHub Repo"></a>
</p>

---

## 🌟 Overview

**Wallora** is a modern, feature-rich Android wallpaper and image discovery application crafted with **Kotlin 2.2**, **Jetpack Compose**, **Material 3**, and **Unsplash API**. 

Inspired by modern glassmorphism design trends, Wallora delivers a smooth mobile experience allowing users to search 4K photography, browse curated collections, download high-resolution wallpapers directly to their device gallery, save personal favorites, and set wallpapers directly to the **Home Screen**, **Lock Screen**, or **Both** using Android's native `WallpaperManager`.

---

## 🏷️ Recommended GitHub Topics

When uploading to GitHub, add these topics to your repository settings (`About` > ⚙️ `Edit repository details`):

```text
android, kotlin, jetpack-compose, wallpaper-app, unsplash-api, material3, material-design, mobile-app, android-architecture, mvvm, coroutines, datastore, wallpaper-manager, media-store, ui-ux, developed-by-najeeb
```

---

## ✨ Key Features

- 🎨 **Floating Glassmorphism Navigation Bar**: Translucent floating bottom container with 6 fully functional tabs (**Home**, **Image Search**, **Wallpaper**, **About**, **Contact**, **Source Code**).
- 🖼️ **Direct Set Wallpaper**: Set any image directly to **Home Screen**, **Lock Screen**, or **Both** with real-time status feedback via Android `WallpaperManager`.
- 📥 **High-Resolution Downloads**: Downloads 4K images to `Pictures/Wallora/` using Android `MediaStore` API with real progress percentage feedback.
- 🔍 **Live Unsplash Search**: Search millions of photos with debounced input, keyboard submit, recent search chips, and category shortcuts.
- ❤️ **Persistent Favorites**: Save wallpapers locally to your personal collection powered by reactive SQLite storage.
- 🎨 **Custom Compose Vector Artwork**: Original flat-vector illustrations for Splash, Welcome Onboarding, Home Hero, Empty Search, Empty Favorites, and Info screens.
- 👤 **Personalized Onboarding**: First-time name entry saved via Preferences DataStore, welcoming returning users with custom greetings (e.g., *"Hi Najeeb!"*).
- 🌓 **Adaptive Light & Dark Mode**: Modern color scheme supporting system, light, and dark theme preferences.

---

## 📸 Screen Architecture

| Screen | Description |
| :--- | :--- |
| **Initial Loading** | Resources initialization with animated logo and spinner. |
| **Splash Screen** | Animated entrance with logo emblem, tagline, and progress animation. |
| **Welcome Onboarding** | First-time user greeting with vector art and name entry. |
| **Home Screen** | Personalized header, hero banner, category cards, trending chips & featured wallpapers. |
| **Image Search** | Real-time Unsplash search, recent history, popular suggestions, and loading skeletons. |
| **Wallpaper Studio** | Dedicated wallpaper collections (Featured, 4K, AMOLED, Nature, Minimal, Tech, etc.). |
| **Full Preview** | Fullscreen preview with photographer attribution, favorite toggle, download, share, and **Set Wallpaper** bottom sheet. |
| **My Favorites** | Saved offline wallpaper grid with quick actions. |
| **About Screen** | Branding, version info, developer showcase, and Unsplash attribution. |
| **Contact Screen** | Direct email action (`njbdev07@gmail.com`) and website link. |
| **Source Code** | Tech stack overview and GitHub repository link (`https://github.com/najeebahmad07/wallora`). |
| **Settings Screen** | Edit display name, app theme toggle, download path info, and clear history. |

---

## 🛠️ Technical Architecture & Stack

```
com.example.wallora/
├── data/
│   ├── local/            # SQLite Database, Favorites DAO, DataStore Preferences
│   ├── model/            # WallpaperItem, SearchCategory, DownloadState, WallpaperSetState
│   └── network/          # Retrofit Service, Unsplash DTOs & Unsplash Repository
├── navigation/           # Compose Navigation Host & Screen Routes
├── ui/
│   ├── components/       # Custom Illustrations, Floating Nav Bar, Wallpaper Cards, Bottom Sheet
│   ├── screens/          # 12 Functional Application Screens
│   └── theme/            # Color palette, Material 3 Light/Dark Schemes, Typography
├── utils/                # WallpaperHelper (WallpaperManager) & ImageDownloader (MediaStore)
└── viewmodel/            # WalloraViewModel (MVVM + StateFlow + Coroutines)
```

### Libraries & Tools Used
- **Kotlin 2.2**: Clean, idiomatic Kotlin coroutines & flow.
- **Jetpack Compose & Material 3**: Declarative UI components and dynamic theming.
- **Navigation Compose (`2.8.8`)**: Single-activity seamless screen navigation.
- **Coil Compose (`2.7.0`)**: Asynchronous image loading and caching.
- **Retrofit 2 & Gson**: REST API client for Unsplash photo integration.
- **Preferences DataStore (`1.1.2`)**: Key-value user preference persistence.
- **Android WallpaperManager**: Native OS wallpaper application API.
- **Android MediaStore API**: Scoped storage image downloader.

---

## 🎨 Color Palette

| Color Name | Hex Code | Purpose |
| :--- | :--- | :--- |
| **Primary Blue** | `#315CEB` | Primary actions, buttons, active tab indicators |
| **Light Blue** | `#8EAEF7` | Accent glows, secondary badges |
| **Soft Background** | `#F2F7FC` | Bright, photography-focused light theme background |
| **Navy Blue** | `#17145C` | Hero banners, dark accents, logo gradients |
| **Coral Accent** | `#FF827D` | Heart favorites, highlight badges, warm accents |
| **Text Primary** | `#182238` | High-contrast body text |
| **Text Secondary** | `#697386` | Subtitles and captions |

---

## 🚀 How to Run the Project locally

### Prerequisites
- **Android Studio** Ladybug (2024.2.1) or newer.
- **Android SDK**: `compileSdk = 37`, `minSdk = 24`.
- **JDK**: Java 11 or higher.

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/najeebahmad07/wallora.git
   cd wallora
   ```

2. **Open in Android Studio**:
   - Open Android Studio -> `File` -> `Open` -> Select the `Wallora` folder.
   - Allow Gradle Sync to finish.

3. **Run on Device or Emulator**:
   - Connect an Android device (API 24+) with USB Debugging enabled or start an emulator.
   - Click **Run `app`** (Shift + F10).

---

## 📤 How to Upload / Push to GitHub

If you are uploading this codebase to GitHub for the first time, execute the following commands in your terminal inside the project root:

```bash
# 1. Initialize Git repository
git init

# 2. Add all project files
git add .

# 3. Commit changes
git commit -m "Initial commit: Wallora Android Application"

# 4. Set main branch name
git branch -M main

# 5. Add remote GitHub repository
git remote add origin https://github.com/najeebahmad07/wallora.git

# 6. Push code to GitHub
git push -u origin main
```

---

## 👨‍💻 Developer & Credits

- **App Name**: Wallora
- **Developer**: Developed By Najeeb
- **Contact Email**: `njbdev07@gmail.com`
- **GitHub Repository**: [https://github.com/najeebahmad07/wallora](https://github.com/najeebahmad07/wallora)
- **API Provider**: Powered by [Unsplash API](https://unsplash.com/developers)

---

<p align="center">
  Made with ❤️ by Najeeb
</p>
