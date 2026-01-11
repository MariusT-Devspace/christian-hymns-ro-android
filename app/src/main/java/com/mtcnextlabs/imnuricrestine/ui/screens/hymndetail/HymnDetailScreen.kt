package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.imnuricrestine.R
import com.mtcnextlabs.imnuricrestine.utils.ICONS
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

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

    val isScrolled by remember { derivedStateOf {
        pinnedScrollBehavior.state.contentOffset < -100f
    } }

    val largeTopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(
        containerColor = if (isScrolled)
            MaterialTheme.colorScheme.surfaceContainer
        else
            MaterialTheme.colorScheme.surface,
    )

    Scaffold(
        modifier = Modifier.nestedScroll(
            pinnedScrollBehavior.nestedScrollConnection
        ),
        topBar = {
            LargeTopAppBar(
                title = {
                    val title = when {
                        hymnUiState is HymnDetailUiState.Success ->
                            getFullHymnTitle(
                                (hymnUiState as HymnDetailUiState.Success).hymn.number,
                                (hymnUiState as HymnDetailUiState.Success).hymn.title
                            )
                        initialTitle.isNotEmpty() -> initialTitle
                        else -> stringResource(R.string.top_bar_title)
                    }
                    Text(
                        text = title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = pinnedScrollBehavior,
                colors = largeTopAppBarColors,
                navigationIcon = {
                    IconButton(
                        onClick = { onGoBack() }
                    ) {
                        ICONS.backIcon()
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Localized description"
                        )
                    }
                }
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