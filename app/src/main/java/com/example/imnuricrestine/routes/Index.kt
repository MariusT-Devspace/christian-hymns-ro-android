package com.example.imnuricrestine.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.navigation.navigationActions
import com.example.imnuricrestine.state.FavoriteIcon
import com.example.imnuricrestine.state.FavoriteIconName
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle
import com.example.imnuricrestine.MainActivity.Companion.OnFavoriteAction
import com.example.imnuricrestine.state.FavoriteAction

@Composable
fun HymnsIndex(
    hymnsListItems: State<List<HymnsListItemUiState>>,
    contentPadding: PaddingValues,
    navController: NavHostController?,
    mainViewModel: MainViewModel?,
    onFavoriteActions: OnFavoriteAction
) {
    val state = remember {
        mutableStateOf(hymnsListItems)
    }

    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        itemsIndexed(
            items = hymnsListItems.value
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
                        if (item.onFavoriteAction == FavoriteAction.ADD_FAVORITE)
                            onFavoriteActions.addFavorite((index + 1).toShort())
                        else
                            onFavoriteActions.deleteFavorite((index + 1).toShort())
                    }
                  ) {
                      if (item.icon == FavoriteIconName.SAVED.name)
                          Icon(imageVector = Icons.Outlined.Favorite, contentDescription = FavoriteIcon.SAVED.icon.description)
                      else
                          Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = FavoriteIcon.NOT_SAVED.icon.description)
                  }
                },
                modifier = Modifier.clickable {
                    val hymnId = item.index.toInt()-1
                    navController!!.navigate(Route.HymnDetails.route+"/$hymnId")
                    mainViewModel!!.updateTopAppBar(
                        TopAppBar.SMALLTOPAPPBAR, TopAppBarTitle.HYMNDETAILS.title,
                        Icons.Filled.ArrowBack, { navigationActions.onGoBack() }
                    )
                },
            )
            //Divider()

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