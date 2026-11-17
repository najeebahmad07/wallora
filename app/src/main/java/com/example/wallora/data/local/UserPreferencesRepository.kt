package com.example.wallora.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wallora_preferences")

class UserPreferencesRepository(private val context: Context) {

    companion object {
        private val KEY_USER_NAME = stringPreferencesKey("user_name")
        private val KEY_IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        private val KEY_DARK_THEME_MODE = stringPreferencesKey("dark_theme_mode") // "SYSTEM", "LIGHT", "DARK"
        private val KEY_RECENT_SEARCHES = stringPreferencesKey("recent_searches") // Comma separated
    }

    val userName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_USER_NAME] ?: ""
    }

    val isFirstLaunch: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_FIRST_LAUNCH] ?: true
    }

    val darkThemeMode: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_DARK_THEME_MODE] ?: "SYSTEM"
    }

    val recentSearches: Flow<List<String>> = context.dataStore.data.map { prefs ->
        val raw = prefs[KEY_RECENT_SEARCHES] ?: ""
        if (raw.isBlank()) emptyList() else raw.split(",").map { it.trim() }
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_NAME] = name.trim()
            prefs[KEY_IS_FIRST_LAUNCH] = false
        }
    }

    suspend fun setFirstLaunchCompleted() {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_FIRST_LAUNCH] = false
        }
    }

    suspend fun setDarkThemeMode(mode: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DARK_THEME_MODE] = mode
        }
    }

    suspend fun addRecentSearch(query: String) {
        if (query.isBlank()) return
        context.dataStore.edit { prefs ->
            val current = (prefs[KEY_RECENT_SEARCHES] ?: "")
                .split(",")
                .map { it.trim() }
                .filter { it.isNotBlank() && !it.equals(query, ignoreCase = true) }
                .toMutableList()
            current.add(0, query.trim())
            val trimmed = current.take(10).joinToString(",")
            prefs[KEY_RECENT_SEARCHES] = trimmed
        }
    }

    suspend fun clearRecentSearches() {
        context.dataStore.edit { prefs ->
            prefs[KEY_RECENT_SEARCHES] = ""
        }
    }
}
