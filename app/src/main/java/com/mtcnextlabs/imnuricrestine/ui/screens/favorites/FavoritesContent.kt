package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.imnuricrestine.R
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

@Composable
fun FavoritesContent(
    contentPadding: PaddingValues,
    favorites: List<HymnListItemUiState>,
    listState: LazyListState,
    onNavigate: (Int, String) -> Unit,
    onRemoveFavorite: (HymnListItemUiState) -> Unit
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
            ListItem(
                headlineContent = {

                },
                supportingContent = {
                    Text(
                        hymn.title,
                        fontSize = 18.sp
                    )
                },
                leadingContent = {
                    Text(
                        hymn.number,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .width(53.dp)
                            .padding(horizontal = 5.dp, vertical = 10.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                },
                trailingContent = {
                    IconButton(
                        onClick = { onRemoveFavorite(hymn) }
                    ) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = stringResource(R.string.remove_from_favorites_description)
                        )
                    }
                },
                modifier = Modifier.clickable {
                    onNavigate(hymn.id, getFullHymnTitle(hymn.number, hymn.title))
                },
            )
        }
    }


}