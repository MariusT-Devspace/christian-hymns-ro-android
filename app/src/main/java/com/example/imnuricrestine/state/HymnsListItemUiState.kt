package com.example.imnuricrestine.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import com.example.imnuricrestine.models.Icon

enum class FavoriteIcon(val icon: Icon) {
    SAVED(Icon(Icons.Outlined.Favorite, "Elimina de la favorite")),
    NOT_SAVED(Icon(Icons.Outlined.FavoriteBorder, "Adauga la favorite"));
}

enum class FavoriteIconName(name: String) {
    SAVED(Icons.Outlined.Favorite.name),
    NOT_SAVED(Icons.Outlined.FavoriteBorder.name)
}

enum class FavoriteAction {
    ADD_FAVORITE,
    DELETE_FAVORITE
}

data class HymnsListItemUiState (
    val id: Int,
    val index: String,
    val title: String,
    val isBookMarked: Boolean,
    val onFavoriteAction: FavoriteAction,
    val icon: String
)