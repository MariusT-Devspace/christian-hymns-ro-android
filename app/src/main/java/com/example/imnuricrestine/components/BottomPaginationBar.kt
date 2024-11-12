package com.example.imnuricrestine.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingAppBarDefaults
import androidx.compose.material3.FloatingAppBarScrollBehavior
import androidx.compose.material3.HorizontalFloatingAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.example.imnuricrestine.state.OnChangePageAction
import com.example.imnuricrestine.state.PageChangeAction
import com.example.imnuricrestine.state.PaginationAppBarUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomPaginationBar(
    scrollBehavior: FloatingAppBarScrollBehavior,
    pageTitle: String,
    paginationAppBarUiState: State<PaginationAppBarUiState>,
    onChangePageAction: OnChangePageAction,
) {
    val selectedPage = 1

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalFloatingAppBar(
            expanded = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(PaddingValues(bottom = 96.dp)),
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentPadding = FloatingAppBarDefaults.ContentPadding,
            scrollBehavior = scrollBehavior,
            shape = FloatingAppBarDefaults.ContainerShape,
            leadingContent = {},
            trailingContent = { }
        ) {
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    selected = false,
                    onClick = { onChangePageAction(PageChangeAction.PREVIOUS, null) },
                    shape = SegmentedButtonDefaults.itemShape(0, 3),
                    Modifier.width(100.dp),
                    enabled = paginationAppBarUiState.value.isPreviousButtonEnabled
                ) {
                    Icon(
                        Icons.AutoMirrored.Sharp.KeyboardArrowLeft, "",
                        tint = if (paginationAppBarUiState.value.isPreviousButtonEnabled)
                            MaterialTheme.colorScheme.onSurface
                        else
                            Color.DarkGray
                    )
                }

                SegmentedButton(
                    selected = false,
                    onClick = { onChangePageAction(PageChangeAction.SELECT, selectedPage) },
                    shape = SegmentedButtonDefaults.itemShape(1, 3)
                ) { Text(pageTitle) }

                SegmentedButton(
                    selected = false,
                    onClick = { onChangePageAction(PageChangeAction.NEXT, null) },
                    shape = SegmentedButtonDefaults.itemShape(2, 3),
                    enabled = paginationAppBarUiState.value.isNextButtonEnabled
                ) {
                    Icon(
                        Icons.AutoMirrored.Sharp.KeyboardArrowRight, "",
                        tint = if (paginationAppBarUiState.value.isNextButtonEnabled)
                            MaterialTheme.colorScheme.onSurface
                        else
                            Color.DarkGray
                    )
                }
            }
        }
    }
}