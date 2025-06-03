package com.mtcnextlabs.imnuricrestine.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarColors
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarScrollBehavior
import androidx.compose.material3.HorizontalFloatingToolbar
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
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import com.mtcnextlabs.imnuricrestine.MainActivity.Companion.indexScreenPages
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logIndexNavigation
import com.mtcnextlabs.imnuricrestine.state.OnChangePageAction
import com.mtcnextlabs.imnuricrestine.state.Page
import com.mtcnextlabs.imnuricrestine.state.PageChangeAction
import com.mtcnextlabs.imnuricrestine.state.PaginationAppBarUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomPaginationBar(
    scrollBehavior: FloatingToolbarScrollBehavior,
    page: Page,
    paginationAppBarUiState: State<PaginationAppBarUiState>,
    onChangePageAction: OnChangePageAction,
) {
    val openDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalFloatingToolbar(
            expanded = false,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(PaddingValues(bottom = WindowInsets.navigationBars
                    .asPaddingValues()
                    .calculateBottomPadding() + 96.dp)),
            colors = FloatingToolbarColors(
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimary,
                MaterialTheme.colorScheme.primaryContainer,
                MaterialTheme.colorScheme.onPrimary
            ),
            contentPadding = PaddingValues(0.dp),
            scrollBehavior = scrollBehavior,
            shape = FloatingToolbarDefaults.ContainerShape
        ) {
            SegmentedButtons(
                openDialog,
                onChangePageAction,
                paginationAppBarUiState,
                page
            )

            if (openDialog.value)
                SelectPageDialog(
                    openDialog,
                    onChangePageAction,
                    page.title
                )
        }
    }
}

@Composable
fun SegmentedButtons(
    openDialog: MutableState<Boolean>,
    onChangePageAction: OnChangePageAction,
    paginationAppBarUiState: State<PaginationAppBarUiState>,
    page: Page
) {
    SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(horizontal = 1.5.dp)) {
        val verticalPadding = 5.dp
        val contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        val disabledContentColor = MaterialTheme.colorScheme.onPrimary
        val borderColor = MaterialTheme.colorScheme.primaryContainer
        val buttonShape = shapes.extraLarge
        val buttonHorizontalPadding = 10.dp

        SegmentedButton(
            selected = false,
            onClick = {
                onChangePageAction(PageChangeAction.PREVIOUS, null)
                logIndexNavigation("previous button", page.title, indexScreenPages[page.number-2].title)
            },
            shape = buttonShape,
            Modifier.width(110.dp).padding(horizontal = buttonHorizontalPadding),
            enabled = paginationAppBarUiState.value.isPreviousButtonEnabled,
            colors = SegmentedButtonColors(
                activeContainerColor = SegmentedButtonDefaults.colors().activeContainerColor,
                activeContentColor = SegmentedButtonDefaults.colors().activeContentColor,
                activeBorderColor = borderColor,
                inactiveContainerColor = SegmentedButtonDefaults.colors().inactiveContainerColor,
                inactiveContentColor = SegmentedButtonDefaults.colors().inactiveContentColor,
                inactiveBorderColor = borderColor,
                disabledActiveContainerColor = SegmentedButtonDefaults.colors().disabledActiveContainerColor,
                disabledActiveContentColor = SegmentedButtonDefaults.colors().disabledActiveContentColor,
                disabledActiveBorderColor = borderColor,
                disabledInactiveContainerColor = SegmentedButtonDefaults.colors().disabledInactiveContainerColor,
                disabledInactiveContentColor = SegmentedButtonDefaults.colors().disabledInactiveContentColor,
                disabledInactiveBorderColor = borderColor
            )
        ) {
            Box(
            modifier = Modifier
                .padding(vertical = verticalPadding)
                .defaultMinSize(minHeight = 24.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Sharp.KeyboardArrowLeft, "",
                    tint = if (paginationAppBarUiState.value.isPreviousButtonEnabled)
                        contentColor
                    else
                        disabledContentColor
                )
            }
        }

        SegmentedButton(
            selected = false,
            onClick = { openDialog.value = true },
            shape = shapes.large,
            colors = SegmentedButtonColors(
                activeContainerColor = MaterialTheme.colorScheme.primary,
                activeContentColor = SegmentedButtonDefaults.colors().activeContentColor,
                activeBorderColor = borderColor,
                inactiveContainerColor = MaterialTheme.colorScheme.primary,
                inactiveContentColor = SegmentedButtonDefaults.colors().inactiveContentColor,
                inactiveBorderColor = borderColor,
                disabledActiveContainerColor = SegmentedButtonDefaults.colors().disabledActiveContainerColor,
                disabledActiveContentColor = SegmentedButtonDefaults.colors().disabledActiveContentColor,
                disabledActiveBorderColor = borderColor,
                disabledInactiveContainerColor = SegmentedButtonDefaults.colors().disabledInactiveContainerColor,
                disabledInactiveContentColor = SegmentedButtonDefaults.colors().disabledInactiveContentColor,
                disabledInactiveBorderColor = borderColor
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = verticalPadding)
                    .defaultMinSize(minHeight = 24.dp)
                    .height(24.dp)
            ) {
                Text(
                    text = page.title,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        SegmentedButton(
            selected = false,
            onClick = {
                onChangePageAction(PageChangeAction.NEXT, null)
                logIndexNavigation("next button", page.title, indexScreenPages[page.number].title)
            },
            shape = buttonShape,
            modifier = Modifier.padding(horizontal = buttonHorizontalPadding),
            enabled = paginationAppBarUiState.value.isNextButtonEnabled,
            colors = SegmentedButtonColors(
                activeContainerColor = SegmentedButtonDefaults.colors().activeContainerColor,
                activeContentColor = SegmentedButtonDefaults.colors().activeContentColor,
                activeBorderColor = borderColor,
                inactiveContainerColor = SegmentedButtonDefaults.colors().inactiveContainerColor,
                inactiveContentColor = SegmentedButtonDefaults.colors().inactiveContentColor,
                inactiveBorderColor = borderColor,
                disabledActiveContainerColor = SegmentedButtonDefaults.colors().disabledActiveContainerColor,
                disabledActiveContentColor = SegmentedButtonDefaults.colors().disabledActiveContentColor,
                disabledActiveBorderColor = borderColor,
                disabledInactiveContainerColor = SegmentedButtonDefaults.colors().disabledInactiveContainerColor,
                disabledInactiveContentColor = SegmentedButtonDefaults.colors().disabledInactiveContentColor,
                disabledInactiveBorderColor = borderColor
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = verticalPadding)
                    .defaultMinSize(minHeight = 24.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Sharp.KeyboardArrowRight, "",
                    tint = if (paginationAppBarUiState.value.isNextButtonEnabled)
                        contentColor
                    else
                        disabledContentColor
                )
            }
        }
    }
}