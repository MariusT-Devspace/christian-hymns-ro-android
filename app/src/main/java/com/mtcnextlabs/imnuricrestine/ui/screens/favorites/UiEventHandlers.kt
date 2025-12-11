package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.models.HymnWithFavorite
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.models.SnackbarData

object FavoriteUiEventHandler {
    fun toggleFavorite(
        hymnWithFavorite: HymnWithFavorite,
        undoAction: Boolean,
        favoriteActions: FavoriteActions,
        showSnackbar: ShowSnackbar
    ) {
        if(hymnWithFavorite.hymnListItemUiState.isFavorite) {
            deleteFavorite(hymnWithFavorite, undoAction, favoriteActions, showSnackbar)
        } else {
            addFavorite(hymnWithFavorite, favoriteActions, showSnackbar)
        }
    }

    fun deleteFavorite(
        favoritesListItem: FavoritesListItem,
        undoAction: Boolean,
        favoriteActions: FavoriteActions,
        showSnackbar: ShowSnackbar
    ) {
        favoriteActions.deleteFavorite(Favorite(favoritesListItem.id, favoritesListItem.hymnId))
            .thenRun {
                showSnackbar(SnackbarData(
                    favoritesListItem,
                    undoAction,
                    "Imnul \"${favoritesListItem.index}. ${favoritesListItem.title}\" șters de la favorite"
                ))
            }
    }

    fun undoDelete(
        favorite: Favorite,
        favoriteActions: FavoriteActions,
    ) {
        favoriteActions.undoDelete(favorite)
    }
}

private fun addFavorite(
    hymnWithFavorite: HymnWithFavorite,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar
) {
    favoriteActions.addFavorite(Favorite(hymnWithFavorite.hymnListItemUiState.id))
        .thenRun{
            showSnackbar(SnackbarData(
                "Imnul \"${hymnWithFavorite.hymnListItemUiState.index}. ${hymnWithFavorite.hymnListItemUiState.title}\" salvat la favorite"
            ))
        }
}

private fun deleteFavorite(
    hymnWithFavorite: HymnWithFavorite,
    undoAction: Boolean,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar
) {
    favoriteActions.deleteFavorite(Favorite(hymnWithFavorite.favorite!!.id, hymnWithFavorite.favorite.hymn_id))
        .thenRun {
            showSnackbar(SnackbarData(
                hymnWithFavorite,
                undoAction,
                "Imnul \"${hymnWithFavorite.hymnListItemUiState.index}. ${hymnWithFavorite.hymnListItemUiState.title}\" șters de la favorite"
            ))
        }
}
