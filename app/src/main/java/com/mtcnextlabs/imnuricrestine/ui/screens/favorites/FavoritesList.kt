package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItem
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState

@Composable
fun FavoritesList(
    favorites: List<HymnListItemUiState>,
    listState: LazyListState,
    onRemoveFavorite: (HymnListItemUiState) -> Unit,
    onNavigate: (Int, String) -> Unit
) {
    LaunchedEffect(Unit) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.height(30.dp))
        }

        items(
            items = favorites
        ) { hymn ->
            HymnListItem(
                hymn,
                onToggleFavorite = onRemoveFavorite,
                onNavigate = onNavigate
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}