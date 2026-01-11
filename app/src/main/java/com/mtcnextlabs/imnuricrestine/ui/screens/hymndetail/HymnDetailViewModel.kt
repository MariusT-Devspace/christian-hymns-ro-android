package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HymnDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    hymnRepository: HymnRepository
) : ViewModel() {
    val hymnId: Int = checkNotNull(savedStateHandle["hymnId"])

    val uiState: StateFlow<HymnDetailUiState> = hymnRepository.getHymnById(hymnId)
        .asFlow()
        .map { hymnEntity ->
            if (hymnEntity != null) {
                HymnDetailUiState.Success(hymnEntity)
            } else {
                HymnDetailUiState.Error
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HymnDetailUiState.Loading
        )
}