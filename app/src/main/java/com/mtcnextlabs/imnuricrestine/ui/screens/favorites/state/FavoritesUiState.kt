package com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state

import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Success(
        val items: List<HymnListItemUiState>
    ) : FavoritesUiState
}