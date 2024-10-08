package com.example.imnuricrestine.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopAppBarType {
    LARGE_TOP_APP_BAR,
    SMALL_TOP_APP_BAR
}

enum class TopAppBarTitle(val title: String) {
    INDEX("920 Imnuri Crestine"),
    FAVORITES("Favorite")
}

data class TopAppBarUiState(
    val topAppBar: TopAppBarType,
    val title: String,
    val navigationIcon: @Composable () -> Unit,
    val onNavigationAction: () -> Unit
)