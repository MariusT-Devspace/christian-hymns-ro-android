package com.example.imnuricrestine.state

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.imnuricrestine.utils.ICONS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _topAppBarUiState = MutableStateFlow(TopAppBarUiState(
        topAppBar = TopAppBar.LARGETOPAPPBAR,
        title = TopAppBarTitle.INDEX.title,
        navigationIcon = ICONS.topAppBarLogo,
        onNavigationAction = {}
    ))
    val topAppBarUiState: StateFlow<TopAppBarUiState> = _topAppBarUiState.asStateFlow()

    fun updateTopAppBar(
        topAppBar: TopAppBar = topAppBarUiState.value.topAppBar,
        title: String = topAppBarUiState.value.title,
        navigationIcon: @Composable () -> Unit = topAppBarUiState.value.navigationIcon,
        onNavigationAction: () -> Unit = topAppBarUiState.value.onNavigationAction
    ) {
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

