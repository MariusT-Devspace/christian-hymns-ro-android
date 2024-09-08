package com.example.imnuricrestine.routes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.R
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.navigation.NavigationActions
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.FavoriteIcon
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle
import com.example.imnuricrestine.utils.ICONS
import java.util.concurrent.CompletableFuture

@Composable
fun Favorites(
    favoritesListItems: List<FavoritesListItem>,
    contentPadding: PaddingValues,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onDeleteFavorite: (Favorite) -> CompletableFuture<Void>,
    updateHymnsListItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp)
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
                                color = MaterialTheme.colorScheme.primaryContainer,
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
                                Log.d("UISTATE", "Update item")
                                updateHymnsListItem(
                                    MainActivity.hymns.value!!.first {
                                        item.hymnId == it.id
                                    }.id.toInt() - 1,
                                    false,
                                    FavoriteAction.ADD_FAVORITE,
                                    FavoriteIcon.NOT_SAVED.name
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
                    mainViewModel.updateTopAppBar(
                        TopAppBar.SMALLTOPAPPBAR, TopAppBarTitle.HYMNDETAILS.title,
                        ICONS.backIcon, { NavigationActions.onGoBack() }
                    )
                },
            )
            //Divider()

        }
    }
}