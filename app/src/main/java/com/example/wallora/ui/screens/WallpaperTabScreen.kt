package com.example.wallora.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wallora.data.model.WallpaperItem
import com.example.wallora.ui.components.KeywordChip
import com.example.wallora.ui.components.ShimmerWallpaperCard
import com.example.wallora.ui.components.WallpaperCard

@Composable
fun WallpaperTabScreen(
    selectedCollection: String,
    wallpapers: List<WallpaperItem>,
    isLoading: Boolean,
    onCollectionSelected: (String) -> Unit,
    onWallpaperClick: (WallpaperItem) -> Unit,
    onFavoriteToggle: (WallpaperItem) -> Unit,
    onDownloadClick: (WallpaperItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val collections = listOf(
        "Featured", "Latest", "Trending", "Nature",
        "4K", "AMOLED", "Abstract", "Minimal", "Technology"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Screen Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Wallpaper Studio",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Premium handpicked wallpaper collections",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Scrollable Collections Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                collections.forEach { name ->
                    KeywordChip(
                        text = name,
                        isSelected = name.equals(selectedCollection, ignoreCase = true),
                        onClick = { onCollectionSelected(name) }
                    )
                }
            }
        }

        // Wallpapers Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                items(6) { index ->
                    ShimmerWallpaperCard(height = if (index % 2 == 0) 250 else 210)
                }
            } else {
                items(wallpapers, key = { it.id }) { wallpaper ->
                    val index = wallpapers.indexOf(wallpaper)
                    val cardHeight = if (index % 3 == 0) 250 else 210
                    WallpaperCard(
                        wallpaper = wallpaper,
                        onWallpaperClick = onWallpaperClick,
                        onFavoriteToggle = onFavoriteToggle,
                        onDownloadClick = onDownloadClick,
                        cardHeight = cardHeight
                    )
                }
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
