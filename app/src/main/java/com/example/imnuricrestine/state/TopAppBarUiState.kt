package com.example.imnuricrestine.state

import androidx.compose.ui.graphics.vector.ImageVector

enum class TopAppBar() {
    LARGETOPAPPBAR(),
    SMALLTOPAPPBAR()
}

enum class TopAppBarTitle(val title: String) {
    INDEX("920 Imnuri Crestine"),
    HYMNDETAILS(""),
    FAVORITES("Favorite")
}

data class TopAppBarUiState(
    val topAppBar: TopAppBar,
    val title: String,
    val navigationIcon: ImageVector,
    val onNavigationAction: () -> Unit
)