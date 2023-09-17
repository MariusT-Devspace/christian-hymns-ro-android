package com.example.imnuricrestine.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import com.example.imnuricrestine.models.Icon

enum class FavoritesIconDescription(val description: String) {
    SAVED("Elimina de la favorite"),
    NOT_SAVED("Adauga la favorite")
}

enum class FavoriteIcon(val icon: Icon) {
    SAVED(Icon(Icons.Outlined.Favorite, FavoritesIconDescription.SAVED.description)),
    NOT_SAVED(Icon(Icons.Outlined.FavoriteBorder, FavoritesIconDescription.NOT_SAVED.description));
}

enum class FavoriteIconName(name: String) {
    SAVED(Icons.Outlined.Favorite.name),
    NOT_SAVED(Icons.Outlined.FavoriteBorder.name)
}

enum class FavoriteAction(methodName: String) {
    ADD_FAVORITE("addFavorite"),
    DELETE_FAVORITE("deleteFavorite")
}

data class HymnsListItemUiState (
    //val id: Short,
    val index: String,
    val title: String,
    val isBookMarked: Boolean,
    val onFavoriteAction: FavoriteAction,
    val icon: String
)