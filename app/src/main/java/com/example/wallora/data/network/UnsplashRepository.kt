package com.example.wallora.data.network

import com.example.wallora.data.model.WallpaperItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashRepository {

    private val apiService: UnsplashApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApiService::class.java)
    }

    suspend fun searchWallpapers(query: String, page: Int = 1): List<WallpaperItem> {
        return try {
            val response = apiService.searchPhotos(query = query, page = page)
            if (response.isSuccessful && response.body() != null) {
                val results = response.body()!!.results.map { dto -> dto.toWallpaperItem(query) }
                if (results.isNotEmpty()) results else getFallbackWallpapers(query)
            } else {
                getFallbackWallpapers(query)
            }
        } catch (e: Exception) {
            getFallbackWallpapers(query)
        }
    }

    suspend fun getCuratedWallpapers(collection: String = "Popular", page: Int = 1): List<WallpaperItem> {
        return try {
            val response = apiService.getEditorialPhotos(page = page)
            if (response.isSuccessful && response.body() != null) {
                val results = response.body()!!.map { dto -> dto.toWallpaperItem(collection) }
                if (results.isNotEmpty()) results else getFallbackWallpapers(collection)
            } else {
                getFallbackWallpapers(collection)
            }
        } catch (e: Exception) {
            getFallbackWallpapers(collection)
        }
    }

    private fun UnsplashPhotoDto.toWallpaperItem(category: String): WallpaperItem {
        val titleText = altDescription ?: description ?: "Wallpaper $id"
        val formattedTitle = titleText.split(" ")
            .take(4)
            .joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

        return WallpaperItem(
            id = id,
            title = formattedTitle,
            description = description ?: altDescription,
            thumbUrl = urls.small,
            regularUrl = urls.regular,
            fullUrl = urls.full,
            rawUrl = urls.raw,
            authorName = user.name,
            authorUsername = user.username,
            authorProfileUrl = user.profileImage?.medium,
            width = width ?: 1080,
            height = height ?: 1920,
            color = color ?: "#315CEB",
            likes = likes ?: 240,
            isFavorite = false,
            category = category
        )
    }

    private fun getFallbackWallpapers(query: String): List<WallpaperItem> {
        val curations = listOf(
            WallpaperItem(
                id = "wall_1",
                title = "Majestic Mountain Peak",
                description = "Dramatic snowy mountain peaks under blue sky",
                thumbUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=2160&auto=format&fit=crop",
                authorName = "Kalvis Alberts",
                authorUsername = "kalvisalberts",
                color = "#2b3d4f",
                likes = 1420,
                category = "Mountains"
            ),
            WallpaperItem(
                id = "wall_2",
                title = "Cyber Neon Grid",
                description = "Futuristic abstract glowing geometric lines",
                thumbUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=2160&auto=format&fit=crop",
                authorName = "Milad Fakurian",
                authorUsername = "fakurian",
                color = "#17145C",
                likes = 3290,
                category = "Abstract"
            ),
            WallpaperItem(
                id = "wall_3",
                title = "Deep Space Nebula",
                description = "Vibrant galaxy dust and glittering cosmic stars",
                thumbUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=2160&auto=format&fit=crop",
                authorName = "NASA",
                authorUsername = "nasa",
                color = "#0B0C10",
                likes = 5410,
                category = "Space"
            ),
            WallpaperItem(
                id = "wall_4",
                title = "Minimalist Horizon",
                description = "Clean aesthetic ocean horizon with soft blue glow",
                thumbUrl = "https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85?w=2160&auto=format&fit=crop",
                authorName = "Scott Webb",
                authorUsername = "scottwebb",
                color = "#8EAEF7",
                likes = 890,
                category = "Minimal"
            ),
            WallpaperItem(
                id = "wall_5",
                title = "Sleek Supercar Matte",
                description = "High performance modern sports car in dark studio",
                thumbUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=2160&auto=format&fit=crop",
                authorName = "Campbell",
                authorUsername = "campbell",
                color = "#182238",
                likes = 4120,
                category = "Cars"
            ),
            WallpaperItem(
                id = "wall_6",
                title = "Kyoto Zen Temple",
                description = "Traditional Japanese architecture framed by cherry blossoms",
                thumbUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=2160&auto=format&fit=crop",
                authorName = "Sora Sagano",
                authorUsername = "sorasagano",
                color = "#FF827D",
                likes = 2750,
                category = "Architecture"
            ),
            WallpaperItem(
                id = "wall_7",
                title = "Lush Emerald Forest",
                description = "Sunbeams breaking through dense misty pine trees",
                thumbUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=2160&auto=format&fit=crop",
                authorName = "Kilian Seiler",
                authorUsername = "kilianseiler",
                color = "#1e3a29",
                likes = 1890,
                category = "Nature"
            ),
            WallpaperItem(
                id = "wall_8",
                title = "AMOLED OLED Lights",
                description = "Pure black background with vivid neon reflections",
                thumbUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=600&auto=format&fit=crop",
                regularUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=1080&auto=format&fit=crop",
                fullUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=2160&auto=format&fit=crop",
                rawUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?w=2160&auto=format&fit=crop",
                authorName = "Alexandre Debiève",
                authorUsername = "alexandre_debiève",
                color = "#000000",
                likes = 6300,
                category = "AMOLED"
            )
        )

        return if (query.isBlank()) curations else curations.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true) ||
            (it.description?.contains(query, ignoreCase = true) == true)
        }.ifEmpty { curations }
    }
}
