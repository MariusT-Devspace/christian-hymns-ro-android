package com.mtcnextlabs.imnuricrestine.ui.components

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun ObserveFavoriteEvents(
    eventFlow: Flow<FavoritesEvent>,
    snackbarHostState: SnackbarHostState,
    onUndoDelete: () -> Unit
) {
    LaunchedEffect(true) {
        eventFlow.collect { event ->
            when (event) {
                is FavoritesEvent.ShowSnackbar -> {
                    val actionLabel = if (event.showUndoAction) "UNDO" else null

                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed)
                        onUndoDelete()
                }
            }
        }
    }
}