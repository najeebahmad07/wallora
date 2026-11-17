package com.example.wallora.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = WalloraPrimaryBlue,
    onPrimary = WalloraWhite,
    primaryContainer = WalloraLightBlue.copy(alpha = 0.2f),
    onPrimaryContainer = WalloraNavy,
    secondary = WalloraNavy,
    onSecondary = WalloraWhite,
    tertiary = WalloraCoral,
    onTertiary = WalloraWhite,
    background = WalloraSoftBg,
    onBackground = WalloraTextPrimary,
    surface = WalloraWhite,
    onSurface = WalloraTextPrimary,
    surfaceVariant = WalloraLightBorder,
    onSurfaceVariant = WalloraTextSecondary,
    outline = WalloraLightBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = WalloraPrimaryBlue,
    onPrimary = WalloraWhite,
    primaryContainer = WalloraPrimaryBlue.copy(alpha = 0.3f),
    onPrimaryContainer = WalloraLightBlue,
    secondary = WalloraLightBlue,
    onSecondary = WalloraNavy,
    tertiary = WalloraCoral,
    onTertiary = WalloraWhite,
    background = WalloraDarkBg,
    onBackground = WalloraDarkTextPrimary,
    surface = WalloraDarkSurface,
    onSurface = WalloraDarkTextPrimary,
    surfaceVariant = WalloraDarkBorder,
    onSurfaceVariant = WalloraDarkTextSecondary,
    outline = WalloraDarkBorder
)

@Composable
fun WalloraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
