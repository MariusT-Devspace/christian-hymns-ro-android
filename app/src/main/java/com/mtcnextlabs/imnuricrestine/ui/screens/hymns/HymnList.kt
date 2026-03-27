package com.mtcnextlabs.imnuricrestine.ui.screens.hymns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HymnList(
    hymnsByRange:  Map<String, List<HymnListItemUiState>>,
    listState: LazyListState = rememberLazyListState(),
    onToggleFavorite: (HymnListItemUiState) -> Unit = {},
    onNavigate: (Int, String) -> Unit = {_, _ -> }
) {
    val coroutineScope = rememberCoroutineScope()

    val sectionIndices = remember {
        var currentIndex = 0
        val indices = mutableMapOf<String, Int>()
        hymnsByRange.forEach { (range, items) ->
            indices[range] = currentIndex
            currentIndex += items.size + 1 // +1 for the sticky header
        }
        indices
    }

    var isActive: Boolean = false

//    hymnsByRange.keys.forEach { range ->
//        isActive = listState.firstVisibleItemIndex >= indexToJumpTo &&
//                listState.firstVisibleItemIndex < (sectionIndices.values.firstOrNull { it > indexToJumpTo } ?: Int.MAX_VALUE)
//    }

    LazyColumn(
        state = listState,
    ) {
        item {
            Spacer(modifier = Modifier.size(30.dp))
        }

        hymnsByRange.forEach { (range, hymnsInRange) ->
            val indexToJumpTo = sectionIndices[range] ?: 0

            // Range header
            stickyHeader {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 10.dp)
                ) {
                    val size = ButtonDefaults.ExtraSmallContainerHeight

                    ElevatedButton(
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(indexToJumpTo + 1)
                            }
                        },
                        contentPadding =
                            ButtonDefaults.contentPaddingFor(size, hasEndIcon = true),
                        colors = ButtonDefaults.elevatedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        )
                    ) {
                        Text(
                            range,
                            style = ButtonDefaults.textStyleFor(size)
                        )
                        Spacer(Modifier.size(ButtonDefaults.iconSpacingFor(size)))
                        Icon(
                            Icons.Filled.ArrowDropDown,
                            "Show ranges",
                            modifier = Modifier.size(ButtonDefaults.iconSizeFor(size))
                        )
                    }
                }
            }

            // Hymns in range
            items(
                items = hymnsInRange
            ) { hymn ->
                HymnListItem(hymn, onToggleFavorite, onNavigate)
            }
        }

        item {
            Spacer(modifier = Modifier.size(120.dp))
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
