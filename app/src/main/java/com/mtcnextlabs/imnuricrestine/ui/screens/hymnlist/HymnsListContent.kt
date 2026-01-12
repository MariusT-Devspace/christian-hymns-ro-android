package com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imnuricrestine.R
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItem
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

@Composable
fun HymnsListContent(
    contentPadding: PaddingValues,
    hymnsListItems: List<HymnListItemUiState>,
    listState: LazyListState,
    onToggleFavorite: (HymnListItemUiState) -> Unit,
    onNavigate: (Int, String) -> Unit
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp),
    ) {
        items(
            items = hymnsListItems
        ) { hymn ->
            HymnListItem(hymn, onToggleFavorite, onNavigate)
        }
    }
}

