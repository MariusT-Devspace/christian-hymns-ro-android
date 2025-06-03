package com.mtcnextlabs.imnuricrestine.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import com.mtcnextlabs.imnuricrestine.models.Icon

enum class FavoriteIcon(val icon: Icon) {
    SAVED(Icon(Icons.Outlined.Favorite, "Elimina de la favorite")),
    NOT_SAVED(Icon(Icons.Outlined.FavoriteBorder, "Adauga la favorite"));
}

data class HymnsListItemUiState (
    val id: Int,
    val index: String,
    val title: String,
    val isFavorite: Boolean
)