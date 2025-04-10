package com.example.imnuricrestine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.R
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.models.OnFavoriteAction
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.FavoriteIconName
import com.example.imnuricrestine.state.UpdateHymnsListItemUiState
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.font.FontStyle

@Composable
fun Favorites(
    contentPadding: PaddingValues,
    navController: NavHostController,
    favoritesListItems: List<FavoritesListItem>,
    listState: LazyListState,
    onDeleteFavorite: OnFavoriteAction,
    updateHymnsListItem: UpdateHymnsListItemUiState
) {
    LaunchedEffect(Unit) {
        listState.scrollToItem(0)
    }

    if (favoritesListItems.isNotEmpty()) {
        LazyColumn(
            state = listState,
            contentPadding = contentPadding,
            modifier = Modifier.padding(top = 30.dp),
        ) {
            items(
                items = favoritesListItems
            ) { item ->
                ListItem(
                    headlineContent = {

                    },
                    supportingContent = {
                        Text(
                            item.title,
                            fontSize = 18.sp
                        )
                    },
                    leadingContent = {
                        Text(
                            item.index,
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
                                onDeleteFavorite(
                                    Favorite(
                                        item.id,
                                        item.hymnId
                                    )
                                ).thenRun {
                                    updateHymnsListItem(
                                        item.hymnId.toInt() - 1,
                                        false,
                                        FavoriteAction.ADD_FAVORITE,
                                        FavoriteIconName.NOT_SAVED.name
                                    )
                                }
                            }
                        ) {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = stringResource(R.string.remove_from_favorites_description)
                            )
                        }
                    },
                    modifier = Modifier.clickable {
                        val hymnId = item.hymnId.toInt() - 1
                        navController.navigate(Route.HymnDetails.route+"/${hymnId}")
                    },
                )
            }
        }
    } else {
        Box(modifier = Modifier.padding(vertical = 80.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center) {
            Column {
                Text(
                    stringResource(R.string.favorites_empty_heading),
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 28.sp,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    stringResource(R.string.favorites_empty_content),
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Card(
                    modifier = Modifier.padding(top = 46.dp),
                    colors = CardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    val favoritesIconId = "favoritesIconId"
                    val saveToFavoritesInstructionsText = buildAnnotatedString {
                        append("Apasă pe ")
                        appendInlineContent(favoritesIconId, "[icon]")
                        append(" pentru a adăuga un imn la favorite.")
                    }

                    val inlineContent = mapOf(
                        Pair(
                            favoritesIconId,
                            InlineTextContent(
                                Placeholder(
                                    width = 20.sp,
                                    height = 20.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                )
                            ) {
                                Icon(Icons.Filled.Favorite,"", modifier = Modifier.fillMaxSize())
                            }
                        )
                    )

                    Text(
                        saveToFavoritesInstructionsText,
                        inlineContent = inlineContent,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    }

}