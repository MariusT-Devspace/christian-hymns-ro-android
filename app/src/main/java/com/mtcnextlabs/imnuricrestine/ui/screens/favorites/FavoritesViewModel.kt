package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logAddFavorite
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logRemoveFavorite
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logUndoDeleteFavorite
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesUiState
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state.HymnListItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.future.await
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
    private val hymnRepository: HymnRepository
) : ViewModel() {
    private var recentlyDeleted: Favorite? = null

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
        if (hymn.isFavorite)
            removeFavorite(hymn.id, "${hymn.number}. ${hymn.title}")
        else
            addFavorite(hymn.id, "${hymn.number}. ${hymn.title}")
    }

    private fun addFavorite(hymnId: Int, title: String) {
        viewModelScope.launch {
            hymnRepository.addFavorite(hymnId).await()

            _eventFlow.send(
                FavoritesEvent.ShowSnackbar(
                    message = "Imnul \"$title\" salvat la favorite",
                    showUndoAction = false
                )
            )

            logAddFavorite(hymnId)
        }
    }

    private fun removeFavorite(hymnId: Int, title: String) {
        viewModelScope.launch {
            val backup = hymnRepository.deleteFavorite(hymnId).await()
            recentlyDeleted = backup

            _eventFlow.send(
                FavoritesEvent.ShowSnackbar(
                    message = "Imnul \"$title\" șters de la favorite",
                    showUndoAction = true
                )
            )

            logRemoveFavorite(hymnId)
        }
    }

    fun undoDelete() {
        recentlyDeleted?.let { backup ->
            viewModelScope.launch {
                hymnRepository.restoreFavorite(backup).await()
                recentlyDeleted = null
                logUndoDeleteFavorite(backup.hymn_id)
            }
        }
    }
}