package com.example.imnuricrestine.state

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    val onOpenMenu : () -> Unit = {
        Log.d("OPENMENU", "Opening menu")
    }

    val _topAppBarUiState = MutableStateFlow(TopAppBarUiState(
        topAppBar = TopAppBar.LARGETOPAPPBAR,
        title = TopAppBarTitle.TITLEINDEX.title,
        navigationIcon = Icons.Filled.Menu,
        onNavigationAction = {}
    ))
    val topAppBarUiState : StateFlow<TopAppBarUiState> = _topAppBarUiState.asStateFlow()
    fun updateTopAppBar(topAppBar : TopAppBar, title : String, navigationIcon : ImageVector,
                        onNavigationAction : () -> Unit) {
            _topAppBarUiState.update { currentState ->
                currentState.copy(
                    topAppBar = topAppBar,
                    title = title,
                    navigationIcon = navigationIcon,
                    onNavigationAction = onNavigationAction
                )
            }
    }
}

