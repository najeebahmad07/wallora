package com.example.wallora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.wallora.navigation.WalloraNavGraph
import com.example.wallora.ui.theme.WalloraTheme
import com.example.wallora.viewmodel.WalloraViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: WalloraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by viewModel.darkThemeMode.collectAsState()
            val useDarkTheme = when (themeMode) {
                "DARK" -> true
                "LIGHT" -> false
                else -> isSystemInDarkTheme()
            }

            WalloraTheme(darkTheme = useDarkTheme) {
                val navController = rememberNavController()
                WalloraNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
