package com.example.wallora.data.local

import com.example.wallora.data.model.WallpaperItem

data class FavoriteEntity(
    val id: String,
    val title: String,
    val description: String?,
    val thumbUrl: String,
    val regularUrl: String,
    val fullUrl: String,
    val rawUrl: String,
    val authorName: String,
    val authorUsername: String,
    val authorProfileUrl: String?,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)

fun FavoriteEntity.toWallpaperItem(): WallpaperItem {
    return WallpaperItem(
        id = id,
        title = title,
        description = description,
        thumbUrl = thumbUrl,
        regularUrl = regularUrl,
        fullUrl = fullUrl,
        rawUrl = rawUrl,
        authorName = authorName,
        authorUsername = authorUsername,
        authorProfileUrl = authorProfileUrl,
        width = width,
        height = height,
        color = color,
        likes = likes,
        isFavorite = true,
        category = category
    )
}

fun WallpaperItem.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        title = title,
        description = description,
        thumbUrl = thumbUrl,
        regularUrl = regularUrl,
        fullUrl = fullUrl,
        rawUrl = rawUrl,
        authorName = authorName,
        authorUsername = authorUsername,
        authorProfileUrl = authorProfileUrl,
        width = width,
        height = height,
        color = color,
        likes = likes,
        category = category
    )
}
