package com.example.imnuricrestine.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun BottomPaginationBar(scrollBehavior: FloatingAppBarScrollBehavior) {
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
                    onClick = {},
                    shape = SegmentedButtonDefaults.itemShape(0, 3),
                ) {
                    Icon(Icons.AutoMirrored.Sharp.KeyboardArrowLeft, "")
                }
                SegmentedButton(
                    selected = false,
                    shape = SegmentedButtonDefaults.itemShape(1, 3),
                    onClick = {}
                ) { Text("1 - 100") }
                SegmentedButton(
                    selected = false,
                    shape = SegmentedButtonDefaults.itemShape(2, 3),
                    onClick = {}
                ) { Icon(Icons.AutoMirrored.Sharp.KeyboardArrowRight, "") }
            }
        }
    }
}