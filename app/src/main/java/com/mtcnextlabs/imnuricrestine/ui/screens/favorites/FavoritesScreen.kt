package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mtcnextlabs.imnuricrestine.ui.FavoritesScreenPreviewData
import com.mtcnextlabs.imnuricrestine.ui.components.ObserveFavoriteEvents
import com.mtcnextlabs.imnuricrestine.ui.components.ScreenLoadingIndicator
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state.FavoritesUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.state.FavoritesViewModel
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    contentPadding: PaddingValues,
    listState: LazyListState,
    snackbarHostState: SnackbarHostState,
    onNavigate: (Int, String) -> Unit
) {
    val favoritesUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveFavoriteEvents(
        viewModel.eventFlow,
        snackbarHostState
    ) {
        viewModel.undoDelete()
    }

    FavoritesScreen(
        favoritesUiState,
        listState,
        contentPadding,
        viewModel::toggleFavorite
    ) { hymnId,  title ->
        onNavigate(hymnId, title)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreen(
    favoritesUiState: FavoritesUiState,
    listState: LazyListState,
    contentPadding: PaddingValues,
    onRemoveFavorite: (HymnListItemUiState) -> Unit = {},
    onNavigate: (Int, String) -> Unit = {_, _ ->}
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val scrolledToTop = remember {
        derivedStateOf { listState.firstVisibleItemIndex == 0 }
    }

    LaunchedEffect(scrolledToTop.value) {
        if (scrolledToTop.value) {
            topAppBarScrollBehavior.state.contentOffset = 0f
        }
    }

    Scaffold(
        modifier = Modifier
            .nestedScroll(
                topAppBarScrollBehavior.nestedScrollConnection
            )
            .padding(contentPadding)
            .consumeWindowInsets(contentPadding),
        topBar = {
            FavoritesTopAppBar(topAppBarScrollBehavior)
        }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (val uiState = favoritesUiState) {
                is FavoritesUiState.Loading ->
                    ScreenLoadingIndicator()

                FavoritesUiState.Empty -> EmptyFavorites()
                is FavoritesUiState.Success -> {
                    FavoritesContent(
                        padding,
                        uiState.items,
                        listState,
                        onRemoveFavorite
                    ) {
                        hymnId, title ->
                        onNavigate(hymnId, title)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    ChristianHymnsTheme {
        FavoritesScreen(
            FavoritesScreenPreviewData.favoritesStateSuccess,
            rememberLazyListState(),
            PaddingValues(0.dp)
        )
    }
}