package com.example.imnuricrestine.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "Item",
    val icon: ImageVector = Icons.Outlined.Home
) {
    object Index : Route("index", "Index", Icons.Outlined.Home)
    object HymnDetails : Route("hymn-details")
    object Favorites : Route("favorites", "Favorite", Icons.Outlined.FavoriteBorder)
}
