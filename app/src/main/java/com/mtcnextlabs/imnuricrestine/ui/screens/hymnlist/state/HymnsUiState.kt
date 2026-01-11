package com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state

import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.pagination.Page

sealed interface HymnsUiState {
    data object Loading : HymnsUiState
    data class Success(
        val pageItems: List<HymnListItemUiState>,
        val currentPage: Int,
        val pages: List<Page>
    ) : HymnsUiState
}