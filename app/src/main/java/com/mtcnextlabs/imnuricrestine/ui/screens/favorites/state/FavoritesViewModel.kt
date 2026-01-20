package com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import com.mtcnextlabs.imnuricrestine.ui.FavoritesActionHelper
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FavoritesEvent {
    data class ShowSnackbar(
        val message: String,
        val showUndoAction: Boolean
    ) : FavoritesEvent
}

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    hymnRepository: HymnRepository,
    private val favoritesHelper: FavoritesActionHelper
) : ViewModel() {
    private val _eventFlow = Channel<FavoritesEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    val uiState: StateFlow<FavoritesUiState> =
        hymnRepository.favoriteHymns.asFlow().map { favorites ->
            if (favorites.isEmpty())
                FavoritesUiState.Empty
            else {
                val items = favorites.map { hymn ->
                    HymnListItemUiState(
                        hymn.id,
                        hymn.number,
                        hymn.title,
                        isFavorite = true
                    )
                }
                FavoritesUiState.Success(items)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )

    fun toggleFavorite(hymn: HymnListItemUiState) {
        viewModelScope.launch {
            favoritesHelper.toggleFavorite(hymn) { event ->
                _eventFlow.send(event)
            }
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            favoritesHelper.undoDelete()
        }
    }
}