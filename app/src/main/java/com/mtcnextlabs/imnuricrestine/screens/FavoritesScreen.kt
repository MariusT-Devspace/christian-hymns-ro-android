package com.mtcnextlabs.imnuricrestine.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mtcnextlabs.imnuricrestine.components.Favorites
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.models.FavoritesListItem
import com.mtcnextlabs.imnuricrestine.state.ShowSnackbar
import com.mtcnextlabs.imnuricrestine.utils.ICONS
import com.mtcnextlabs.imnuricrestine.utils.TopAppBarTitle

@Composable
fun FavoritesScreen(
    contentPadding: PaddingValues,
    navController: NavHostController,
    favoritesListItems: List<FavoritesListItem>,
    listState: LazyListState,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val actions : @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    val scrolledToTop = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    LaunchedEffect(scrolledToTop.value) {
        if (scrolledToTop.value) {
            topAppBarScrollBehavior.state.contentOffset = 0f
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(
            topAppBarScrollBehavior.nestedScrollConnection
        ).padding(contentPadding).consumeWindowInsets(contentPadding),
        topBar = {
            TopAppBar(
                title = { Text(TopAppBarTitle.FAVORITES.title) },
                scrollBehavior = topAppBarScrollBehavior,
                navigationIcon = {
                    Row(modifier = Modifier.padding(start = 10.dp, end =  10.dp)) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(36.dp)
                        ) {
                            ICONS.topAppBarLogo()
                        }
                    }
                },
                actions = actions
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Favorites(
                padding,
                navController,
                favoritesListItems,
                listState,
                favoriteActions,
                showSnackbar
            )
        }
    }
}