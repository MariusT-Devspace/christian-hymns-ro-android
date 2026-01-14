package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnListItemUiState

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Success(
        val items: List<HymnListItemUiState>
    ) : FavoritesUiState
}