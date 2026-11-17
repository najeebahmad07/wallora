package com.example.wallora.data.model

data class SearchCategory(
    val name: String,
    val keyword: String,
    val imageUrl: String
)

val WALLORA_CATEGORIES = listOf(
    SearchCategory("Nature", "nature wallpaper", "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=600&auto=format&fit=crop"),
    SearchCategory("Mountains", "mountains wallpaper", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600&auto=format&fit=crop"),
    SearchCategory("Technology", "technology cyber wallpaper", "https://images.unsplash.com/photo-1518770660439-4636190af475?w=600&auto=format&fit=crop"),
    SearchCategory("Abstract", "abstract wallpaper 4k", "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=600&auto=format&fit=crop"),
    SearchCategory("Cars", "supercars wallpaper", "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&auto=format&fit=crop"),
    SearchCategory("Architecture", "modern architecture wallpaper", "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=600&auto=format&fit=crop"),
    SearchCategory("Animals", "wildlife animals wallpaper", "https://images.unsplash.com/photo-1546182990-dffeafbe841d?w=600&auto=format&fit=crop"),
    SearchCategory("Space", "galaxy space wallpaper", "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=600&auto=format&fit=crop"),
    SearchCategory("Flowers", "blossom flowers wallpaper", "https://images.unsplash.com/photo-1490750967868-88aa4486c946?w=600&auto=format&fit=crop"),
    SearchCategory("Minimal", "minimalist wallpaper", "https://images.unsplash.com/photo-1494438639946-1ebd1d20bf85?w=600&auto=format&fit=crop"),
    SearchCategory("Travel", "japan travel wallpaper", "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?w=600&auto=format&fit=crop"),
    SearchCategory("Art", "digital art wallpaper", "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=600&auto=format&fit=crop")
)
