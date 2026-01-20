package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import com.mtcnextlabs.imnuricrestine.models.HymnDetail

sealed interface HymnDetailUiState {
    data object Loading : HymnDetailUiState
    data object Error : HymnDetailUiState
    data class Success(
        val hymnDetail: HymnDetail
    ) : HymnDetailUiState
}