package com.example.fundamentalpertama.Adapter

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DataBottomBar(
    val route: String,
    val icon: ImageVector,
    val title: String? = null
) {
    data object Home : DataBottomBar(
        route = "Home",
        icon = Icons.Default.Home,
        title = "Home"
    )

    data object UpComing : DataBottomBar(
        route = "Up Coming",
        icon = Icons.Default.DateRange,
        title = "Up-Coming"
    )

    data object Finished : DataBottomBar(
        route = "Finished",
        icon = Icons.Default.Check,
        title = "Finished"
    )

    data object Favorite: DataBottomBar(
        route = "Favorite",
        icon = Icons.Default.FavoriteBorder,
        title = "Favorite"
    )

    data object Settings: DataBottomBar(
        route = "Settings",
        icon = Icons.Default.Settings,
        title = "Settings"
    )
}