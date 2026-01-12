package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HymnDetailScreen(
    hymnDetailViewModel: HymnDetailViewModel = hiltViewModel(),
    initialTitle: String = "",
    onGoBack: () -> Unit
) {
    val hymnUiState by hymnDetailViewModel.uiState.collectAsStateWithLifecycle()

    val pinnedScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
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
            when (val uiState = hymnUiState) {
                is HymnDetailUiState.Loading -> {}

                is HymnDetailUiState.Error ->
                    HymnNotFoundError()

                is HymnDetailUiState.Success -> {
                    HymnDetailContent(
                        uiState.hymn,
                        padding
                    )
                }
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