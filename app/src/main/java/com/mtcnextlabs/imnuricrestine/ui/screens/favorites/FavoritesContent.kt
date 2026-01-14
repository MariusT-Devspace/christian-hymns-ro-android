package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun FavoritesContent(
    contentPadding: PaddingValues,
    favorites: List<HymnListItemUiState>,
    listState: LazyListState,
    onRemoveFavorite: (HymnListItemUiState) -> Unit,
    onNavigate: (Int, String) -> Unit
) {
    LaunchedEffect(Unit) {
        listState.scrollToItem(0)
    }

    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp),
    ) {
        items(
            items = favorites
        ) { hymn ->
            HymnListItem(hymn, onRemoveFavorite, onNavigate)
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}