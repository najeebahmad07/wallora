package com.example.wallora.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WalloraDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DB_NAME, null, DB_VERSION), FavoriteDao {

    private val _favoritesFlow = MutableStateFlow<List<FavoriteEntity>>(emptyList())

    init {
        refreshFavorites()
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE favorites (
                id TEXT PRIMARY KEY,
                title TEXT NOT NULL,
                description TEXT,
                thumbUrl TEXT NOT NULL,
                regularUrl TEXT NOT NULL,
                fullUrl TEXT NOT NULL,
                rawUrl TEXT NOT NULL,
                authorName TEXT NOT NULL,
                authorUsername TEXT NOT NULL,
                authorProfileUrl TEXT,
                width INTEGER NOT NULL,
                height INTEGER NOT NULL,
                color TEXT NOT NULL,
                likes INTEGER NOT NULL,
                category TEXT NOT NULL,
                timestamp INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS favorites")
        onCreate(db)
    }

    private fun refreshFavorites() {
        val list = mutableListOf<FavoriteEntity>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM favorites ORDER BY timestamp DESC", null)
        cursor.use { c ->
            while (c.moveToNext()) {
                list.add(
                    FavoriteEntity(
                        id = c.getString(c.getColumnIndexOrThrow("id")),
                        title = c.getString(c.getColumnIndexOrThrow("title")),
                        description = c.getString(c.getColumnIndexOrThrow("description")),
                        thumbUrl = c.getString(c.getColumnIndexOrThrow("thumbUrl")),
                        regularUrl = c.getString(c.getColumnIndexOrThrow("regularUrl")),
                        fullUrl = c.getString(c.getColumnIndexOrThrow("fullUrl")),
                        rawUrl = c.getString(c.getColumnIndexOrThrow("rawUrl")),
                        authorName = c.getString(c.getColumnIndexOrThrow("authorName")),
                        authorUsername = c.getString(c.getColumnIndexOrThrow("authorUsername")),
                        authorProfileUrl = c.getString(c.getColumnIndexOrThrow("authorProfileUrl")),
                        width = c.getInt(c.getColumnIndexOrThrow("width")),
                        height = c.getInt(c.getColumnIndexOrThrow("height")),
                        color = c.getString(c.getColumnIndexOrThrow("color")),
                        likes = c.getInt(c.getColumnIndexOrThrow("likes")),
                        category = c.getString(c.getColumnIndexOrThrow("category")),
                        timestamp = c.getLong(c.getColumnIndexOrThrow("timestamp"))
                    )
                )
            }
        }
        _favoritesFlow.value = list
    }

    override fun getAllFavorites(): Flow<List<FavoriteEntity>> = _favoritesFlow.asStateFlow()

    override fun isFavorite(wallpaperId: String): Flow<Boolean> = _favoritesFlow.map { list ->
        list.any { it.id == wallpaperId }
    }

    override fun getFavoriteIds(): Flow<List<String>> = _favoritesFlow.map { list ->
        list.map { it.id }
    }

    override suspend fun insertFavorite(favorite: FavoriteEntity) = withContext(Dispatchers.IO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", favorite.id)
            put("title", favorite.title)
            put("description", favorite.description)
            put("thumbUrl", favorite.thumbUrl)
            put("regularUrl", favorite.regularUrl)
            put("fullUrl", favorite.fullUrl)
            put("rawUrl", favorite.rawUrl)
            put("authorName", favorite.authorName)
            put("authorUsername", favorite.authorUsername)
            put("authorProfileUrl", favorite.authorProfileUrl)
            put("width", favorite.width)
            put("height", favorite.height)
            put("color", favorite.color)
            put("likes", favorite.likes)
            put("category", favorite.category)
            put("timestamp", favorite.timestamp)
        }
        db.insertWithOnConflict("favorites", null, values, SQLiteDatabase.CONFLICT_REPLACE)
        refreshFavorites()
    }

    override suspend fun deleteFavoriteById(wallpaperId: String) = withContext(Dispatchers.IO) {
        val db = writableDatabase
        db.delete("favorites", "id = ?", arrayOf(wallpaperId))
        refreshFavorites()
    }

    override suspend fun deleteFavorite(favorite: FavoriteEntity) {
        deleteFavoriteById(favorite.id)
    }

    companion object {
        private const val DB_NAME = "wallora_database.db"
        private const val DB_VERSION = 1

        @Volatile
        private var INSTANCE: WalloraDatabase? = null

        fun getDatabase(context: Context): WalloraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = WalloraDatabase(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
