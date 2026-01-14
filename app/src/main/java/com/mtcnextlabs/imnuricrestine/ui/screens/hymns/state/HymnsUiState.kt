package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state

import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.Page

sealed interface HymnsUiState {
    data object Loading : HymnsUiState
    data class Success(
        val pageItems: List<HymnListItemUiState>,
        val currentPage: Int,
        val pages: List<Page>
    ) : HymnsUiState
}