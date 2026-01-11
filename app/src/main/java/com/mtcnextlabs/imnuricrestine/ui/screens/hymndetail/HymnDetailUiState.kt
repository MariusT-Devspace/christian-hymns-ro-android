package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import com.mtcnextlabs.imnuricrestine.models.Hymn

sealed interface HymnDetailUiState {
    data object Loading : HymnDetailUiState
    data object Error : HymnDetailUiState
    data class Success(
        val hymn: Hymn
    ) : HymnDetailUiState
}