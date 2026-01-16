package com.mtcnextlabs.imnuricrestine.ui.screens.hymns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarExitDirection.Companion.Bottom
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mtcnextlabs.imnuricrestine.ui.HymnsScreenPreviewData
import com.mtcnextlabs.imnuricrestine.ui.components.ObserveFavoriteEvents
import com.mtcnextlabs.imnuricrestine.ui.components.ScreenLoadingIndicator
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.BottomPaginationBar
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination.PaginationAction
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnsUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnListViewModel
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HymnsScreen(
    hymnListViewModel: HymnListViewModel = hiltViewModel(),
    listState: LazyListState,
    snackbarHostState: SnackbarHostState,
    onNavigate: (Int, String) -> Unit
) {
    val hymnsUiState by hymnListViewModel.uiState.collectAsStateWithLifecycle()

    ObserveFavoriteEvents(
        hymnListViewModel.eventFlow,
        snackbarHostState
    ) {
        hymnListViewModel.undoDelete()
    }

    HymnsScreen(
        hymnsUiState,
        listState,
        hymnListViewModel::changePage,
        hymnListViewModel::toggleFavorite,
        onNavigate
    )
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
private fun HymnsScreen(
    uiState: HymnsUiState,
    listState: LazyListState,
    onChangePage: (PaginationAction) -> Unit = {},
    onToggleFavorite: (HymnListItemUiState) -> Unit = {},
    onNavigate: (Int, String) -> Unit = {_, _ ->}
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

    val scrolledToTop = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    LaunchedEffect(scrolledToTop.value) {
        if (scrolledToTop.value)
            topAppBarScrollBehavior.state.heightOffset = 0f
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(floatingAppBarScrollBehavior),
        topBar = {
            HymnListTopAppBar(topAppBarScrollBehavior)
        },
        bottomBar = {
            if (uiState is HymnsUiState.Success) {

                BottomPaginationBar(
                    floatingAppBarScrollBehavior,
                    uiState.pages,
                    uiState.currentPage
                ) { action ->
                    onChangePage(action)
                }
            }
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (uiState) {
                is HymnsUiState.Loading -> ScreenLoadingIndicator()
                is HymnsUiState.Success -> {
                    val isFirstComposition = remember { mutableStateOf(true) }

                    LaunchedEffect(uiState.currentPage) {
                        if (isFirstComposition.value)
                            isFirstComposition.value = false
                        else {
                            listState.scrollToItem(0)
                            topAppBarScrollBehavior.state.heightOffset = 0f
                        }
                    }

                    HymnsContent(
                        padding,
                        uiState.pageItems,
                        listState,
                        onToggleFavorite
                    ) { id, title ->
                        onNavigate(id, title)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HymnsScreenPreview() {
    ChristianHymnsTheme {
        HymnsScreen(
            HymnsScreenPreviewData.hymnListStateSuccess,
            rememberLazyListState()
        )
    }
}
