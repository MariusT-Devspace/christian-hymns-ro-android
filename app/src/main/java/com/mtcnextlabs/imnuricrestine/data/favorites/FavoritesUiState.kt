package com.mtcnextlabs.imnuricrestine.data.favorites

import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnListItemUiState

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data object Empty : FavoritesUiState
    data class Success(
        val items: List<HymnListItemUiState>
    ) : FavoritesUiState
}