package com.mtcnextlabs.imnuricrestine.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(
    val route: String,
    val title: String = "",
    val iconSelected: @Contextual ImageVector? = null,
    val iconNotSelected: @Contextual ImageVector? = null
): NavKey {
    @Serializable
    data object Hymns : Screen(
        "hymns",
        "Imnuri",
        Icons.Filled.Home,
        Icons.Outlined.Home
    )
    @Serializable
    data class HymnDetail(
        val id: Int,
        val hymnTitle: String
    ) : Screen("hymn-detail")
    @Serializable
    data object Favorites : Screen(
        "favorites",
        "Favorite",
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder)
}
