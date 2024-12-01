package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.example.imnuricrestine.components.Favorites
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.models.OnFavoriteAction
import com.example.imnuricrestine.state.UpdateHymnsListItemUiState
import com.example.imnuricrestine.utils.ICONS
import com.example.imnuricrestine.utils.TopAppBarTitle

@Composable
fun FavoritesScreen(
    contentPadding: PaddingValues,
    navController: NavHostController,
    favoritesListItems: List<FavoritesListItem>,
    listState: LazyListState,
    onDeleteFavorite: OnFavoriteAction,
    updateHymnsListItemUiState: UpdateHymnsListItemUiState
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val actions : @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { /* doSomething() */ }) {
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
                    IconButton(
                        onClick = {}
                    ) {
                        ICONS.topAppBarLogo()
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
                onDeleteFavorite,
                updateHymnsListItemUiState
            )
        }
    }
}