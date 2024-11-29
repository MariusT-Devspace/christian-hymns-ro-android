package com.example.imnuricrestine.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.FavoriteIcon
import com.example.imnuricrestine.state.FavoriteIconName
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.favorites.FavoritesViewModel
import com.example.imnuricrestine.models.OnFavoriteActions
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.UpdateHymnsListItemUiState

@Composable
fun HymnsIndex(
    contentPadding: PaddingValues,
    navController: NavHostController?,
    hymnsListItems: List<HymnsListItemUiState>,
    onFavoriteActions: OnFavoriteActions,
    updateHymnsListItemUiState: UpdateHymnsListItemUiState
) {
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val favorites: State<List<Favorite>> =
        favoritesViewModel.favorites.observeAsState(emptyList())

    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp),
    ) {
        items(
            items = hymnsListItems
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
                            when (item.onFavoriteAction) {
                                FavoriteAction.ADD_FAVORITE -> onFavoriteActions.addFavorite(
                                    Favorite(
                                        item.id.toShort()
                                    )
                                ).thenRun {
                                    Log.d("UISTATE", "Update item")
                                    updateHymnsListItemUiState(
                                        item.id - 1,
                                        true,
                                        FavoriteAction.DELETE_FAVORITE,
                                        FavoriteIcon.SAVED.name
                                    )
                                }

                                FavoriteAction.DELETE_FAVORITE -> onFavoriteActions.deleteFavorite(
                                    favorites.value.firstOrNull { favorite ->
                                        favorite.hymn_id.toInt() == item.id
                                    }

                                ).thenRun {
                                    updateHymnsListItemUiState(
                                        item.id - 1,
                                        false,
                                        FavoriteAction.ADD_FAVORITE,
                                        FavoriteIcon.NOT_SAVED.name
                                    )
                                }
                            }
                        }
                    ) {
                      when (item.icon) {
                          FavoriteIconName.SAVED.name -> {
                              Icon(
                                  imageVector = Icons.Outlined.Favorite,
                                  contentDescription = FavoriteIcon.SAVED.icon.description
                              )
                          }
                          FavoriteIconName.NOT_SAVED.name -> {
                              Icon(
                                  imageVector = Icons.Outlined.FavoriteBorder,
                                  contentDescription = FavoriteIcon.NOT_SAVED.icon.description
                              )
                          }
                      }
                  }
                },
                modifier = Modifier.clickable {
                    navController!!.navigate(Route.HymnDetails.route+"/${item.id - 1}")
                }
            )
        }
    }
}

//@Preview
//@Composable
//fun HymnsIndexPreview() {
//    val list = listOf(
//        HymnsListItemUiState(id = 0, index = "7", title = "Ţi-nalţ, Iehova-n veci cântare!"),
//        HymnsListItemUiState(id = 1, index = "13", title = "Domnul e bun"),
//        HymnsListItemUiState(id = 2, index = "110", title = "O, ce veste minunată!"),
//        HymnsListItemUiState(id = 3, index = "159A", title = "La mari biruinţe ne cheamă Scriptura")
//    )
//
//    HymnsIndex(list, PaddingValues(20.dp),  null,  null, null)
//}