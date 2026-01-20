package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mtcnextlabs.imnuricrestine.ui.HymnDetailScreenPreviewData
import com.mtcnextlabs.imnuricrestine.ui.components.GradientNavbarOverlay
import com.mtcnextlabs.imnuricrestine.ui.isNavBarOverlayVisible
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

@Composable
fun HymnDetailScreen(
    id: Int,
    hymnDetailViewModel: HymnDetailViewModel =
        hiltViewModel<HymnDetailViewModel, HymnDetailViewModel.Factory> { factory ->
            factory.create(id)
        },
    initialTitle: String = "",
    onGoBack: () -> Unit
) {
    val hymnUiState by hymnDetailViewModel.uiState.collectAsStateWithLifecycle()

    HymnDetailScreen(hymnUiState, initialTitle, onGoBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HymnDetailScreen(
    hymnUiState: HymnDetailUiState,
    initialTitle: String,
    onGoBack: () -> Unit = {}
) {
    val pinnedScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(
            pinnedScrollBehavior.nestedScrollConnection
        ),
        topBar = {
            HymnDetailTopAppBar(
                hymnUiState,
                initialTitle,
                pinnedScrollBehavior,
                onGoBack
            )
        },
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
            ) {
                when (hymnUiState) {
                    is HymnDetailUiState.Loading -> {}

                    is HymnDetailUiState.Error ->
                        HymnNotFoundError()

                    is HymnDetailUiState.Success -> {
                        HymnDetailContent(
                            hymnUiState.hymnDetail
                        )
                    }
                }

                if (isNavBarOverlayVisible())
                    GradientNavbarOverlay()
            }
        }
    }
}

@Composable
private fun HymnNotFoundError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Imnul nu a fost găsit",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HymnDetailScreenPreview() {
    ChristianHymnsTheme {
        val index = 0

        HymnDetailScreen(
            HymnDetailUiState.Success(
                hymnDetail = HymnDetailScreenPreviewData.hymnDetails[index]
            ),
            initialTitle = getFullHymnTitle(
                HymnDetailScreenPreviewData.hymnDetails[index].number,
                HymnDetailScreenPreviewData.hymnDetails[index].title
            )
        )
    }
}