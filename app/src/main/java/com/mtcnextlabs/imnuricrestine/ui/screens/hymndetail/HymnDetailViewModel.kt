package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = HymnDetailViewModel.Factory::class)
class HymnDetailViewModel @AssistedInject constructor(
    hymnRepository: HymnRepository,
    @Assisted id: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int): HymnDetailViewModel
    }

    val uiState: StateFlow<HymnDetailUiState> = hymnRepository.getHymnById(id)
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