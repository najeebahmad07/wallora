package com.example.wallora.data.model

enum class WallpaperTarget {
    HOME,
    LOCK,
    BOTH
}

sealed class WallpaperSetState {
    object Idle : WallpaperSetState()
    data class Applying(val target: WallpaperTarget) : WallpaperSetState()
    object Success : WallpaperSetState()
    data class Error(val message: String) : WallpaperSetState()
}
