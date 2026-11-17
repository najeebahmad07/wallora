package com.example.wallora.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallora.data.local.FavoriteDao
import com.example.wallora.data.local.UserPreferencesRepository
import com.example.wallora.data.local.WalloraDatabase
import com.example.wallora.data.local.toFavoriteEntity
import com.example.wallora.data.local.toWallpaperItem
import com.example.wallora.data.model.DownloadState
import com.example.wallora.data.model.WallpaperItem
import com.example.wallora.data.model.WallpaperSetState
import com.example.wallora.data.model.WallpaperTarget
import com.example.wallora.data.network.UnsplashRepository
import com.example.wallora.utils.ImageDownloader
import com.example.wallora.utils.WallpaperHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WalloraViewModel(application: Application) : AndroidViewModel(application) {

    private val userPrefsRepo = UserPreferencesRepository(application)
    private val favoriteDao: FavoriteDao = WalloraDatabase.getDatabase(application)
    private val unsplashRepo = UnsplashRepository()

    val userName: StateFlow<String> = userPrefsRepo.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val isFirstLaunch: StateFlow<Boolean> = userPrefsRepo.isFirstLaunch
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val darkThemeMode: StateFlow<String> = userPrefsRepo.darkThemeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "SYSTEM")

    val recentSearches: StateFlow<List<String>> = userPrefsRepo.recentSearches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteWallpapers: StateFlow<List<WallpaperItem>> = favoriteDao.getAllFavorites()
        .map { entities -> entities.map { it.toWallpaperItem() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val favoriteIds: StateFlow<List<String>> = favoriteDao.getFavoriteIds()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Home Wallpapers
    private val _homeWallpapers = MutableStateFlow<List<WallpaperItem>>(emptyList())
    val homeWallpapers: StateFlow<List<WallpaperItem>> = combine(_homeWallpapers, favoriteIds) { list, favs ->
        list.map { item -> item.copy(isFavorite = favs.contains(item.id)) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isHomeLoading = MutableStateFlow(true)
    val isHomeLoading: StateFlow<Boolean> = _isHomeLoading.asStateFlow()

    // Search Wallpapers
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<WallpaperItem>>(emptyList())
    val searchResults: StateFlow<List<WallpaperItem>> = combine(_searchResults, favoriteIds) { list, favs ->
        list.map { item -> item.copy(isFavorite = favs.contains(item.id)) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isSearchLoading = MutableStateFlow(false)
    val isSearchLoading: StateFlow<Boolean> = _isSearchLoading.asStateFlow()

    // Dedicated Wallpaper Tab Collection Wallpapers
    private val _selectedCollection = MutableStateFlow("Featured")
    val selectedCollection: StateFlow<String> = _selectedCollection.asStateFlow()

    private val _tabWallpapers = MutableStateFlow<List<WallpaperItem>>(emptyList())
    val tabWallpapers: StateFlow<List<WallpaperItem>> = combine(_tabWallpapers, favoriteIds) { list, favs ->
        list.map { item -> item.copy(isFavorite = favs.contains(item.id)) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isTabLoading = MutableStateFlow(true)
    val isTabLoading: StateFlow<Boolean> = _isTabLoading.asStateFlow()

    // Download State & Wallpaper Set State
    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState: StateFlow<DownloadState> = _downloadState.asStateFlow()

    private val _wallpaperSetState = MutableStateFlow<WallpaperSetState>(WallpaperSetState.Idle)
    val wallpaperSetState: StateFlow<WallpaperSetState> = _wallpaperSetState.asStateFlow()

    // Active Wallpaper Selected for Preview
    private val _selectedWallpaper = MutableStateFlow<WallpaperItem?>(null)
    val selectedWallpaper: StateFlow<WallpaperItem?> = combine(_selectedWallpaper, favoriteIds) { item, favs ->
        item?.copy(isFavorite = favs.contains(item.id))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        loadHomeWallpapers()
        loadTabWallpapers("Featured")
    }

    fun loadHomeWallpapers() {
        viewModelScope.launch {
            _isHomeLoading.value = true
            val items = unsplashRepo.getCuratedWallpapers("Popular")
            _homeWallpapers.value = items
            _isHomeLoading.value = false
        }
    }

    fun loadTabWallpapers(collection: String) {
        viewModelScope.launch {
            _selectedCollection.value = collection
            _isTabLoading.value = true
            val items = unsplashRepo.searchWallpapers(query = collection)
            _tabWallpapers.value = items
            _isTabLoading.value = false
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            _isSearchLoading.value = false
            return
        }

        viewModelScope.launch {
            userPrefsRepo.addRecentSearch(query)
            _isSearchLoading.value = true
            val results = unsplashRepo.searchWallpapers(query)
            _searchResults.value = results
            _isSearchLoading.value = false
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            userPrefsRepo.saveUserName(name)
        }
    }

    fun setDarkThemeMode(mode: String) {
        viewModelScope.launch {
            userPrefsRepo.setDarkThemeMode(mode)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            userPrefsRepo.clearRecentSearches()
        }
    }

    fun toggleFavorite(wallpaper: WallpaperItem) {
        viewModelScope.launch {
            if (wallpaper.isFavorite) {
                favoriteDao.deleteFavoriteById(wallpaper.id)
            } else {
                favoriteDao.insertFavorite(wallpaper.toFavoriteEntity())
            }
        }
    }

    fun selectWallpaperForPreview(wallpaper: WallpaperItem) {
        _selectedWallpaper.value = wallpaper
        _downloadState.value = DownloadState.Idle
        _wallpaperSetState.value = WallpaperSetState.Idle
    }

    fun downloadWallpaper(wallpaper: WallpaperItem) {
        viewModelScope.launch {
            ImageDownloader.downloadImage(
                context = getApplication(),
                imageUrl = wallpaper.fullUrl,
                title = wallpaper.title
            ).collect { state ->
                _downloadState.value = state
            }
        }
    }

    fun applyWallpaper(wallpaper: WallpaperItem, target: WallpaperTarget) {
        viewModelScope.launch {
            _wallpaperSetState.value = WallpaperSetState.Applying(target)
            val result = WallpaperHelper.setWallpaper(
                context = getApplication(),
                imageUrl = wallpaper.fullUrl,
                target = target
            )
            _wallpaperSetState.value = result
        }
    }

    fun resetWallpaperSetState() {
        _wallpaperSetState.value = WallpaperSetState.Idle
    }
}
