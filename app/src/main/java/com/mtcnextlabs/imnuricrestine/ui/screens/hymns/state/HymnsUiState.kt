package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state

import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState

sealed interface HymnsUiState {
    data object Loading : HymnsUiState
    data class Success(
        val hymnsByRange:  Map<String, List<HymnListItemUiState>>
    ) : HymnsUiState
}