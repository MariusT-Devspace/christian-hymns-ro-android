package com.mtcnextlabs.imnuricrestine.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val title: String = "Item",
    val iconSelected: ImageVector? = null,
    val iconNotSelected: ImageVector? = null
) {
    data object Index : Route(
        "index",
        "Index",
        Icons.Filled.Home,
        Icons.Outlined.Home
    )
    data object HymnDetails : Route("hymn-details")
    data object Favorites : Route(
        "favorites",
        "Favorite",
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder)
}
