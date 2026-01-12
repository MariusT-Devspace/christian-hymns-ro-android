package com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.imnuricrestine.R
import com.mtcnextlabs.imnuricrestine.utils.ICONS
import com.mtcnextlabs.imnuricrestine.utils.getFullHymnTitle

@Composable
fun HymnDetailTopAppBar(
    hymnUiState: HymnDetailUiState,
    initialTitle: String,
    pinnedScrollBehavior: TopAppBarScrollBehavior,
    onGoBack: () -> Unit
) {
    val isScrolled by remember { derivedStateOf {
        pinnedScrollBehavior.state.contentOffset < -100f
    } }

    val colors = TopAppBarDefaults.topAppBarColors(
        containerColor = if (isScrolled)
            MaterialTheme.colorScheme.surfaceContainer
        else
            MaterialTheme.colorScheme.surface,
    )

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
        colors = colors,
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
}