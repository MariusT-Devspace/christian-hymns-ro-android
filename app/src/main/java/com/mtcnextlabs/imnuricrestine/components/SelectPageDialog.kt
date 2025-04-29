package com.mtcnextlabs.imnuricrestine.components

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
import com.mtcnextlabs.imnuricrestine.MainActivity.Companion.indexScreenPages
import com.mtcnextlabs.imnuricrestine.state.OnChangePageAction
import com.mtcnextlabs.imnuricrestine.state.PageChangeAction

@Composable
fun SelectPageDialog(
    openDialog: MutableState<Boolean>,
    onChangePageAction: OnChangePageAction,
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
                    onChangePageAction
                )
            }
        }
    }
}

@Composable
fun DialogContent(
    openDialog: MutableState<Boolean>,
    onChangePageAction: OnChangePageAction
) {
    Column(modifier = Modifier.padding(16.dp)) {
        indexScreenPages.forEach { selectionOption ->
            ListItem(
                headlineContent = { Text(text = selectionOption.title) },
                modifier = Modifier.clickable {
                    openDialog.value = false
                    onChangePageAction(PageChangeAction.SELECT, selectionOption.index)
                }
            )
        }
    }
}