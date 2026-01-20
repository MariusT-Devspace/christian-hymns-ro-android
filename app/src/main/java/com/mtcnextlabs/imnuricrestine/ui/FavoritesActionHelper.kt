package com.mtcnextlabs.imnuricrestine.ui

import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logAddFavorite
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logRemoveFavorite
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logUndoDeleteFavorite
import com.mtcnextlabs.imnuricrestine.data.db.entities.FavoriteEntity
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnRepository
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state.FavoritesEvent
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle
import kotlinx.coroutines.future.await
import javax.inject.Inject

class FavoritesActionHelper @Inject constructor(
    private val hymnRepository: HymnRepository,
) {
    private var recentlyDeleted: FavoriteEntity? = null

    suspend fun toggleFavorite(
        hymn: HymnListItemUiState,
        onShowSnackbar: suspend (FavoritesEvent) -> Unit
    ) {
        if (hymn.isFavorite)
            removeFavorite(hymn, onShowSnackbar)
        else
            addFavorite(hymn, onShowSnackbar)
    }

    private suspend fun addFavorite(
        hymn: HymnListItemUiState,
        onShowSnackbar: suspend (FavoritesEvent) -> Unit
    ) {
        hymnRepository.addFavorite(hymn.id).await()

        val title = getFullHymnTitle(hymn.number, hymn.title)
        onShowSnackbar(
            FavoritesEvent.ShowSnackbar(
                message = "Imnul \"$title\" salvat la favorite",
                showUndoAction = false
            )
        )

        logAddFavorite(hymn.id)
    }

    private suspend fun removeFavorite(
        hymn: HymnListItemUiState,
        onShowSnackbar: suspend (FavoritesEvent) -> Unit
    ) {
        val backup = hymnRepository.deleteFavorite(hymn.id).await()
        recentlyDeleted = backup

        val title = getFullHymnTitle(hymn.number, hymn.title)
        onShowSnackbar(
            FavoritesEvent.ShowSnackbar(
                message = "Imnul \"$title\" șters de la favorite",
                showUndoAction = true
            )
        )

        logRemoveFavorite(hymn.id)
    }

    suspend fun undoDelete() {
        recentlyDeleted?.let { backup ->
            hymnRepository.restoreFavorite(backup).await()
            recentlyDeleted = null
            logUndoDeleteFavorite(backup.hymn_id)
        }
    }
}