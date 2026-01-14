package com.mtcnextlabs.imnuricrestine.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

@Composable
fun HymnListItem(
    state: HymnListItemUiState,
    onToggleFavorite: (HymnListItemUiState) -> Unit,
    onNavigate: (Int, String) -> Unit
) {
    ListItem(
        headlineContent = {},
        supportingContent = {
            Text(
                state.title,
                fontSize = 18.sp
            )
        },
        leadingContent = {
            Text(
                state.number,
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
                onClick = {
                    onToggleFavorite(state)
                }
            ) {
                if (state.isFavorite)
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(R.string.remove_from_favorites_description)
                    )
                else
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.add_to_favorites_description)
                    )
            }
        },
        modifier = Modifier.clickable {
            onNavigate(state.id, getFullHymnTitle(state.number, state.title))
        }
    )
}