package com.example.wallora.data.model

data class WallpaperItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val thumbUrl: String,
    val regularUrl: String,
    val fullUrl: String,
    val rawUrl: String,
    val authorName: String,
    val authorUsername: String,
    val authorProfileUrl: String? = null,
    val width: Int = 1080,
    val height: Int = 1920,
    val color: String = "#315CEB",
    val likes: Int = 0,
    val isFavorite: Boolean = false,
    val category: String = "All"
)
