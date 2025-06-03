package com.mtcnextlabs.imnuricrestine.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mtcnextlabs.imnuricrestine.MainActivity
import com.mtcnextlabs.imnuricrestine.components.BottomPaginationBar
import com.mtcnextlabs.imnuricrestine.components.HymnsIndex
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesViewModel
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.state.IndexScreenUiState
import com.mtcnextlabs.imnuricrestine.state.PaginationConfig.getPages
import com.mtcnextlabs.imnuricrestine.state.ShowSnackbar
import com.mtcnextlabs.imnuricrestine.state.indexScreenUiStateSaver
import com.mtcnextlabs.imnuricrestine.utils.ICONS
import com.mtcnextlabs.imnuricrestine.utils.TopAppBarTitle

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IndexScreen(
    navController: NavHostController,
    hymns: State<List<Hymn>>,
    listState: LazyListState,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )
    val floatingAppBarScrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        exitDirection = Bottom
    )

    LaunchedEffect(Unit) {
        floatingAppBarScrollBehavior.state.offset = 0f
    }

    val actions : @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    MainActivity.indexScreenPages = remember { hymns.value.getPages() }

    val indexScreenUiState = rememberSaveable (
        saver = indexScreenUiStateSaver(
            hymns.value,
            favoriteActions,
            showSnackbar
        )
    ) {
        IndexScreenUiState(
            hymns.value,
            favoriteActions,
            showSnackbar
        )
    }

    val (currentPage,
        paginationAppBarUiState,
        pageItems,
        onChangePageAction,
        updatePageItems) =
        indexScreenUiState

    LaunchedEffect(currentPage.value) {
        updatePageItems(hymns.value)
        listState.scrollToItem(0)
        topAppBarScrollBehavior.state.heightOffset = 0f
    }

    LaunchedEffect(hymns.value) {
        updatePageItems(hymns.value)
    }

    val scrolledToTop = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    LaunchedEffect(scrolledToTop.value) {
        if (scrolledToTop.value)
            topAppBarScrollBehavior.state.heightOffset = 0f
    }

    if (hymns.value.isNotEmpty())
        Box(modifier = Modifier.padding(vertical = 80.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center) {
            Text("List not empty")
        }
     else
        Box(modifier = Modifier.padding(vertical = 80.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center) {
            Text("Empty list")
        }

    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val favorites: State<List<Favorite>> =
        favoritesViewModel.favorites.observeAsState(emptyList())

    Scaffold(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(floatingAppBarScrollBehavior),
        topBar = {
            LargeTopAppBar(
                title = { Text(TopAppBarTitle.INDEX.title)
                },
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
        bottomBar = {
            BottomPaginationBar(
                floatingAppBarScrollBehavior,
                currentPage.value,
                paginationAppBarUiState,
                onChangePageAction
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HymnsIndex(
                padding,
                navController,
                pageItems.value,
                favorites.value,
                listState,
                favoriteActions,
                showSnackbar
            )
        }
    }
}

