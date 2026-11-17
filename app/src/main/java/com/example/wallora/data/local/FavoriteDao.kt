package com.example.wallora.data.local

import kotlinx.coroutines.flow.Flow

interface FavoriteDao {
    fun getAllFavorites(): Flow<List<FavoriteEntity>>
    fun isFavorite(wallpaperId: String): Flow<Boolean>
    fun getFavoriteIds(): Flow<List<String>>
    suspend fun insertFavorite(favorite: FavoriteEntity)
    suspend fun deleteFavoriteById(wallpaperId: String)
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}
