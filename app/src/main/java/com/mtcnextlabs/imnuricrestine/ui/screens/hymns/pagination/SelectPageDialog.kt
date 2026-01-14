package com.mtcnextlabs.imnuricrestine.ui.screens.hymns.pagination

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectPageDialog(
    openDialog: MutableState<Boolean>,
    pages: List<Page>,
    currentPageRange: String,
    onChangePage: OnChangePage
) {
    BasicAlertDialog(
        onDismissRequest = { openDialog.value = false }
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            ScrollableView(
                Modifier.wrapContentWidth().wrapContentHeight()
            ) {
                DialogContent(
                    openDialog,
                    pages,
                    currentPageRange,
                    onChangePage
                )
            }
        }
    }
}

@Composable
fun DialogContent(
    openDialog: MutableState<Boolean>,
    pages: List<Page>,
    currentPageRange: String,
    onChangePage: OnChangePage,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        pages.forEachIndexed { index, selectionOption ->
            ListItem(
                headlineContent = { Text(text = selectionOption.title) },
                modifier = Modifier.clickable {
                    openDialog.value = false
                    onChangePage(PaginationAction.JumpToPage(
                        index,
                        currentPageRange,
                        selectionOption.title
                    ))
                }
            )
        }
    }
}