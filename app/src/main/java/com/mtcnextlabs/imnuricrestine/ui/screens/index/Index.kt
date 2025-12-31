package com.mtcnextlabs.imnuricrestine.ui.screens.index

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.navigation.NavHostController
import com.example.imnuricrestine.R
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logNavigateToHymnDetails
import com.mtcnextlabs.imnuricrestine.ui.screens.index.state.HymnsListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.navigation.Route

@Composable
fun Index(
    contentPadding: PaddingValues,
    navController: NavHostController?,
    hymnsListItems: List<HymnsListItemUiState>,
    listState: LazyListState,
    onToggleFavorite: (Int) -> Unit,
) {
    LazyColumn(
        state = listState,
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp),
    ) {
        itemsIndexed(
            items = hymnsListItems
        ) { index, item ->
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
                            onToggleFavorite(index)
                        }
                    ) {
                      if (item.isFavorite)
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
                    navController!!.navigate(Route.HymnDetails.route+"/${item.id - 1}")
                    logNavigateToHymnDetails(item.id, "index screen")
                }
            )
        }
    }
}