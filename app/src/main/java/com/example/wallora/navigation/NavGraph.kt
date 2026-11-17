package com.example.wallora.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wallora.ui.components.FloatingBottomNavigation
import com.example.wallora.ui.components.NavDestination
import com.example.wallora.ui.screens.*
import com.example.wallora.viewmodel.WalloraViewModel

object Screen {
    const val INITIAL_LOADING = "initial_loading"
    const val SPLASH = "splash"
    const val WELCOME_ONBOARDING = "welcome_onboarding"
    const val HOME = "home"
    const val SEARCH = "search"
    const val WALLPAPER = "wallpaper"
    const val ABOUT = "about"
    const val CONTACT = "contact"
    const val SOURCE_CODE = "source_code"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val PREVIEW = "preview"
}

@Composable
fun WalloraNavGraph(
    navController: NavHostController,
    viewModel: WalloraViewModel,
    modifier: Modifier = Modifier
) {
    val userName by viewModel.userName.collectAsState()
    val isFirstLaunch by viewModel.isFirstLaunch.collectAsState()
    val darkThemeMode by viewModel.darkThemeMode.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val favoriteWallpapers by viewModel.favoriteWallpapers.collectAsState()

    val homeWallpapers by viewModel.homeWallpapers.collectAsState()
    val isHomeLoading by viewModel.isHomeLoading.collectAsState()

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val isSearchLoading by viewModel.isSearchLoading.collectAsState()

    val selectedCollection by viewModel.selectedCollection.collectAsState()
    val tabWallpapers by viewModel.tabWallpapers.collectAsState()
    val isTabLoading by viewModel.isTabLoading.collectAsState()

    val selectedWallpaper by viewModel.selectedWallpaper.collectAsState()
    val downloadState by viewModel.downloadState.collectAsState()
    val wallpaperSetState by viewModel.wallpaperSetState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.HOME

    // Screens where floating navigation bar is shown
    val showFloatingNav = currentRoute in listOf(
        Screen.HOME, Screen.SEARCH, Screen.WALLPAPER,
        Screen.ABOUT, Screen.CONTACT, Screen.SOURCE_CODE, Screen.FAVORITES
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (showFloatingNav) {
                FloatingBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { destination ->
                        if (currentRoute != destination.route) {
                            navController.navigate(destination.route) {
                                popUpTo(Screen.HOME) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.INITIAL_LOADING,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.INITIAL_LOADING) {
                InitialLoadingScreen(
                    onLoadingComplete = {
                        navController.navigate(Screen.SPLASH) {
                            popUpTo(Screen.INITIAL_LOADING) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.SPLASH) {
                SplashScreen(
                    onSplashFinished = {
                        val nextScreen = if (isFirstLaunch || userName.isBlank()) {
                            Screen.WELCOME_ONBOARDING
                        } else {
                            Screen.HOME
                        }
                        navController.navigate(nextScreen) {
                            popUpTo(Screen.SPLASH) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.WELCOME_ONBOARDING) {
                WelcomeOnboardingScreen(
                    onSaveNameAndContinue = { name ->
                        viewModel.saveUserName(name)
                        navController.navigate(Screen.HOME) {
                            popUpTo(Screen.WELCOME_ONBOARDING) { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.HOME) {
                HomeScreen(
                    userName = userName,
                    wallpapers = homeWallpapers,
                    isLoading = isHomeLoading,
                    onWallpaperClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        navController.navigate(Screen.PREVIEW)
                    },
                    onFavoriteToggle = { item -> viewModel.toggleFavorite(item) },
                    onDownloadClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        viewModel.downloadWallpaper(item)
                        navController.navigate(Screen.PREVIEW)
                    },
                    onCategoryClick = { category ->
                        viewModel.search(category.keyword)
                        navController.navigate(Screen.SEARCH)
                    },
                    onTrendingChipClick = { keyword ->
                        viewModel.search(keyword)
                        navController.navigate(Screen.SEARCH)
                    },
                    onOpenSearch = { navController.navigate(Screen.SEARCH) },
                    onOpenFavorites = { navController.navigate(Screen.FAVORITES) },
                    onOpenSettings = { navController.navigate(Screen.SETTINGS) }
                )
            }

            composable(Screen.SEARCH) {
                ImageSearchScreen(
                    initialQuery = searchQuery,
                    searchResults = searchResults,
                    isLoading = isSearchLoading,
                    recentSearches = recentSearches,
                    onSearchQuerySubmit = { query -> viewModel.search(query) },
                    onClearRecentSearches = { viewModel.clearRecentSearches() },
                    onWallpaperClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        navController.navigate(Screen.PREVIEW)
                    },
                    onFavoriteToggle = { item -> viewModel.toggleFavorite(item) },
                    onDownloadClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        viewModel.downloadWallpaper(item)
                        navController.navigate(Screen.PREVIEW)
                    }
                )
            }

            composable(Screen.WALLPAPER) {
                WallpaperTabScreen(
                    selectedCollection = selectedCollection,
                    wallpapers = tabWallpapers,
                    isLoading = isTabLoading,
                    onCollectionSelected = { coll -> viewModel.loadTabWallpapers(coll) },
                    onWallpaperClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        navController.navigate(Screen.PREVIEW)
                    },
                    onFavoriteToggle = { item -> viewModel.toggleFavorite(item) },
                    onDownloadClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        viewModel.downloadWallpaper(item)
                        navController.navigate(Screen.PREVIEW)
                    }
                )
            }

            composable(Screen.ABOUT) {
                AboutScreen()
            }

            composable(Screen.CONTACT) {
                ContactScreen()
            }

            composable(Screen.SOURCE_CODE) {
                SourceCodeScreen()
            }

            composable(Screen.FAVORITES) {
                FavouritesScreen(
                    favoriteWallpapers = favoriteWallpapers,
                    onWallpaperClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        navController.navigate(Screen.PREVIEW)
                    },
                    onFavoriteToggle = { item -> viewModel.toggleFavorite(item) },
                    onDownloadClick = { item ->
                        viewModel.selectWallpaperForPreview(item)
                        viewModel.downloadWallpaper(item)
                        navController.navigate(Screen.PREVIEW)
                    }
                )
            }

            composable(Screen.SETTINGS) {
                SettingsScreen(
                    currentName = userName,
                    currentThemeMode = darkThemeMode,
                    onSaveName = { newName -> viewModel.saveUserName(newName) },
                    onSetThemeMode = { mode -> viewModel.setDarkThemeMode(mode) },
                    onClearRecentSearches = { viewModel.clearRecentSearches() },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.PREVIEW) {
                selectedWallpaper?.let { wallpaper ->
                    FullWallpaperPreviewScreen(
                        wallpaper = wallpaper,
                        downloadState = downloadState,
                        wallpaperSetState = wallpaperSetState,
                        onBackClick = { navController.popBackStack() },
                        onFavoriteToggle = { item -> viewModel.toggleFavorite(item) },
                        onDownloadClick = { item -> viewModel.downloadWallpaper(item) },
                        onSetWallpaperTarget = { target -> viewModel.applyWallpaper(wallpaper, target) },
                        onResetWallpaperSetState = { viewModel.resetWallpaperSetState() }
                    )
                } ?: LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
    }
}
