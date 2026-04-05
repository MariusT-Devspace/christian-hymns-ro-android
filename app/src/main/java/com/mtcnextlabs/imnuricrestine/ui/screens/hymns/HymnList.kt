package com.mtcnextlabs.imnuricrestine.ui.screens.hymns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtcnextlabs.imnuricrestine.ui.HymnsScreenPreviewData
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItem
import com.mtcnextlabs.imnuricrestine.ui.components.HymnListItemUiState
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.state.HymnsUiState
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@Composable
fun HymnList(
    hymnsByRange:  Map<String, List<HymnListItemUiState>>,
    listState: LazyListState = rememberLazyListState(),
    onToggleFavorite: (HymnListItemUiState) -> Unit = {},
    onNavigate: (Int, String) -> Unit = {_, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()

    val sectionIndices = remember(hymnsByRange) {
        var currentIndex = 1
        val indices = mutableMapOf<String, Int>()
        hymnsByRange.forEach { (range, items) ->
            indices[range] = currentIndex
            currentIndex += items.size + 1 // +1 for the sticky header
        }
        indices
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState
        ) {
            item(key = "top_spacer", contentType = "spacer") {
                Spacer(modifier = Modifier.size(30.dp))
            }

            hymnsByRange.forEach { (range, hymnsInRange) ->
                val headerIndex = sectionIndices[range] ?: 0

                // Range header
                stickyHeader(
                ) {
                    Header(
                        range,
                        headerIndex,
                        listState,
                        isPinned = false
                    )
                }

                // Hymns in range
                itemsIndexed(
                    items = hymnsInRange,
                    key = {_, hymn -> "hymn_${hymn.number}"},
                    contentType = { _, _ -> "hymn_item" }
                ) { index, hymn ->
                    val isLastInRange = index == hymnsInRange.size - 1

                    HymnListItem(
                        hymn,
                        modifier = Modifier.padding(bottom = if(isLastInRange) 24.dp else 0.dp),
                        onToggleFavorite = onToggleFavorite,
                        onNavigate = onNavigate
                    )
                }
            }

            item(key = "bottom_spacer", contentType = "spacer") {
                Spacer(modifier = Modifier.size(120.dp))
            }
        }

        // Pinned header overlay
        val currentPinnedRange by remember(hymnsByRange) {
            derivedStateOf {
                val visibleIndex = listState.firstVisibleItemIndex
                // Find the header index that is closest to, but not greater than, the visible index
                val activeSection = sectionIndices.entries
                    .filter { it.value <= visibleIndex }
                    .maxByOrNull { it.value }

                activeSection?.key
            }
        }

        // Only show the overlay if we've scrolled past the very top padding
        if (currentPinnedRange != null && listState.firstVisibleItemIndex > 0) {
            Header(
                title = currentPinnedRange!!,
                headerIndex = 0,
                listState = listState,
                isPinned = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HymnListPreview() {
    ChristianHymnsTheme {
        HymnList(
            (HymnsScreenPreviewData.hymnListStateSuccess as HymnsUiState.Success)
                .hymnsByRange,
            rememberLazyListState()
        )
    }
}
