package com.mtcnextlabs.imnuricrestine.ui.screens.hymns

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItem
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState

@Composable
fun HymnsList(
    hymnsListItems: List<HymnListItemUiState>,
    listState: LazyListState,
    onToggleFavorite: (HymnListItemUiState) -> Unit,
    onNavigate: (Int, String) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        item {
            Spacer(modifier = Modifier.size(30.dp))
        }

        items(
            items = hymnsListItems
        ) { hymn ->
            HymnListItem(hymn, onToggleFavorite, onNavigate)
        }

        item {
            Spacer(modifier = Modifier.size(120.dp))
        }
    }
}

