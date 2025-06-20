package com.mtcnextlabs.imnuricrestine.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mtcnextlabs.imnuricrestine.MainActivity
import com.mtcnextlabs.imnuricrestine.components.BottomPaginationBar
import com.mtcnextlabs.imnuricrestine.components.Index
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
import com.mtcnextlabs.imnuricrestine.utils.onFirstNonNull

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IndexScreen(
    navController: NavHostController,
    hymns: () -> List<Hymn>,
    listState: LazyListState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar,
) {
    val floatingAppBarScrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        exitDirection = Bottom
    )

    LaunchedEffect(Unit) {
        floatingAppBarScrollBehavior.state.offset = 0f
    }

    val actions: @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    onFirstNonNull(hymns()) {
        MainActivity.indexScreenPages.value = hymns().getPages()
    }

    if (MainActivity.indexScreenPages.value.isNotEmpty()) {
        val (currentPage,
            paginationAppBarUiState,
            pageItems,
            onChangePageAction,
            updatePageItems) = rememberSaveable(
                saver = indexScreenUiStateSaver(
                    hymns()
                )
            ) {
                IndexScreenUiState(
                    hymns()
                )
            }

        var isFirstComposition by remember { mutableStateOf(true) }

        LaunchedEffect(currentPage.value) {
            if (isFirstComposition)
                isFirstComposition = false
            else {
                listState.scrollToItem(0)
                topAppBarScrollBehavior.state.heightOffset = 0f
            }
        }

        LaunchedEffect(hymns()) {
            updatePageItems(hymns())
        }

        val scrolledToTop = remember {
            derivedStateOf { listState.firstVisibleItemIndex == 0 }
        }

        LaunchedEffect(scrolledToTop.value) {
            if (scrolledToTop.value)
                topAppBarScrollBehavior.state.heightOffset = 0f
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
                    title = {
                        Text(TopAppBarTitle.INDEX.title)
                    },
                    scrollBehavior = topAppBarScrollBehavior,
                    navigationIcon = {
                        Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
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
                Index(
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
    } else
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoadingIndicator(modifier = Modifier.size(126.dp))
        }
}

