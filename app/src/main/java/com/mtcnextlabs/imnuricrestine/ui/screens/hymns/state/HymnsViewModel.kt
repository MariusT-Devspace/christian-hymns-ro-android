package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import com.mtcnextlabs.imnuricrestine.ui.FavoritesActionHelper
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state.FavoritesEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HymnsViewModel @Inject constructor(
    hymnRepository: HymnRepository,
    private val favoritesHelper: FavoritesActionHelper
) : ViewModel() {
    // Favorite snackbar events
    private val _eventFlow = Channel<FavoritesEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val rangeSize = 100

    val uiState: StateFlow<HymnsUiState> = hymnRepository.hymns.asFlow().map { hymns ->
        // Group hymns by range
        val groupedHymns = hymns.groupBy { hymn ->
            val rawHymnNumber = hymn.number.filter { it.isDigit() }.toIntOrNull() ?: 0
            if (rawHymnNumber == 0) 0 else (rawHymnNumber / rangeSize)
        }

        // Sort grouped hymns
        val hymnsByRange = groupedHymns.entries
            .sortedBy { it.key }
            .associate { (bucketIndex, hymnsInBucket) ->
                val start = if (bucketIndex == 0) 1 else bucketIndex * rangeSize
                val end = (bucketIndex * rangeSize) + (rangeSize - 1)
                val rangeLabel = "$start - $end"

                rangeLabel to hymnsInBucket.map { hymn ->
                    HymnListItemUiState(
                        hymn.id,
                        hymn.number,
                        hymn.title,
                        hymn.isFavorite
                    )
                }
            }

        // Return state
        HymnsUiState.Success(
            hymnsByRange = hymnsByRange,
        )
    }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HymnsUiState.Loading
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