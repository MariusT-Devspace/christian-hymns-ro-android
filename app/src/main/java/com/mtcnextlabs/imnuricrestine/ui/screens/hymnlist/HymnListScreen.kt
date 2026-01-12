package com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mtcnextlabs.imnuricrestine.ui.components.ObserveFavoriteEvents
import com.mtcnextlabs.imnuricrestine.ui.components.ScreenLoadingIndicator
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesViewModel
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.pagination.BottomPaginationBar
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state.HymnsUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymnlist.state.HymnListViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HymnsScreen(
    hymnListViewModel: HymnListViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    listState: LazyListState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    onNavigate: (Int, String) -> Unit
) {
    val hymnsUiState by hymnListViewModel.uiState.collectAsStateWithLifecycle()

    val floatingAppBarScrollBehavior = FloatingToolbarDefaults.exitAlwaysScrollBehavior(
        exitDirection = Bottom
    )

    LaunchedEffect(Unit) {
        floatingAppBarScrollBehavior.state.offset = 0f
    }

    var isFirstComposition by remember { mutableStateOf(true) }

    LaunchedEffect(hymnsUiState) {
        if (isFirstComposition)
            isFirstComposition = false
        else {
            listState.scrollToItem(0)
            topAppBarScrollBehavior.state.heightOffset = 0f
        }
    }

    val scrolledToTop = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    LaunchedEffect(scrolledToTop.value) {
        if (scrolledToTop.value)
            topAppBarScrollBehavior.state.heightOffset = 0f
    }

    ObserveFavoriteEvents(
        favoritesViewModel.eventFlow,
        snackbarHostState
    ) {
        favoritesViewModel.undoDelete()
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(floatingAppBarScrollBehavior),
        topBar = {
            HymnListTopAppBar(topAppBarScrollBehavior)
        },
        bottomBar = {
            if (hymnsUiState is HymnsUiState.Success) {
                val successState = hymnsUiState as HymnsUiState.Success

                BottomPaginationBar(
                    floatingAppBarScrollBehavior,
                    successState.pages,
                    successState.currentPage
                ) { action ->
                    hymnListViewModel.onPaginationAction(action)
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val uiState = hymnsUiState) {
                is HymnsUiState.Loading -> ScreenLoadingIndicator()
                is HymnsUiState.Success -> {
                    HymnsListContent(
                        padding,
                        uiState.pageItems,
                        listState,
                        onToggleFavorite = { hymn ->
                            favoritesViewModel.toggleFavorite(hymn)
                        }
                    ) { id, title ->
                        onNavigate(id, title)
                    }
                }
            }
        }
    }
}

