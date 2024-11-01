package com.example.imnuricrestine.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.FavoriteIcon
import com.example.imnuricrestine.state.FavoriteIconName
import com.example.imnuricrestine.MainActivity.Companion.OnFavoriteAction
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.state.FavoriteAction

@Composable
fun HymnsIndex(
    hymnsListItems: List<HymnsListItemUiState>,
    contentPadding: PaddingValues,
    navController: NavHostController?,
    onFavoriteActions: OnFavoriteAction,
    updateItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        itemsIndexed(
            items = hymnsListItems.take(100)
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
                            when (item.onFavoriteAction) {
                                FavoriteAction.ADD_FAVORITE -> onFavoriteActions.addFavorite(
                                    Favorite(
                                        MainActivity.hymns.value!![index].id
                                    )
                                ).thenRun {
                                    Log.d("UISTATE", "Update item")
                                    updateItem(
                                        index,
                                        true,
                                        FavoriteAction.DELETE_FAVORITE,
                                        FavoriteIcon.SAVED.name
                                    )
                                }

                                FavoriteAction.DELETE_FAVORITE -> onFavoriteActions.deleteFavorite(
                                    MainActivity.favorites.value.firstOrNull { favorite ->
                                        favorite.hymn_id == MainActivity.hymns.value?.get(index)!!.id
                                    }
                                ).thenRun {
                                    updateItem(
                                        index,
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
                              Log.d("HYMNS_LIST_UI_STATE", "SAVED")
                              Icon(
                                  imageVector = Icons.Outlined.Favorite,
                                  contentDescription = FavoriteIcon.SAVED.icon.description
                              )
                          }
                          FavoriteIconName.NOT_SAVED.name -> {
                              Log.d("HYMNS_LIST_UI_STATE", "NOT SAVED")
                              Icon(
                                  imageVector = Icons.Outlined.FavoriteBorder,
                                  contentDescription = FavoriteIcon.NOT_SAVED.icon.description
                              )
                          }
                      }
                  }
                },
                modifier = Modifier.clickable {
                    navController!!.navigate(Route.HymnDetails.route+"/$index")
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