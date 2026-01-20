package com.mtcnextlabs.imnuricrestine.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun isNavBarOverlayVisible(): Boolean {
    val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    return bottomPadding > 30.dp
}