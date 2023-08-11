package com.example.imnuricrestine.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.imnuricrestine.navigation.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {


    private val _topAppBarUiState = MutableStateFlow(TopAppBarUiState(
        topAppBar = TopAppBar.LARGETOPAPPBAR,
        title = TopAppBarTitle.TITLEINDEX.title,
        navigationIcon = Icons.Filled.Menu,
        onNavigationAction = {}
    ))
    val topAppBarUiState: StateFlow<TopAppBarUiState> = _topAppBarUiState.asStateFlow()

    private val _navigationDrawerUiState = MutableStateFlow(NavigationDrawerUiState(
        selectedItem = Route.Index
    ))
    val navigationDrawerUiState: StateFlow<NavigationDrawerUiState> = _navigationDrawerUiState.asStateFlow()

    fun updateTopAppBar(topAppBar: TopAppBar = topAppBarUiState.value.topAppBar,
                        title: String = topAppBarUiState.value.title,
                        navigationIcon: ImageVector = topAppBarUiState.value.navigationIcon,
                        onNavigationAction: () -> Unit = topAppBarUiState.value.onNavigationAction) {
            _topAppBarUiState.update { currentState ->
                currentState.copy(
                    topAppBar = topAppBar,
                    title = title,
                    navigationIcon = navigationIcon,
                    onNavigationAction = onNavigationAction
                )
            }
    }

    fun updateNavigationDrawer(selectedItem: Route) {
        _navigationDrawerUiState.update { currentState ->
            currentState.copy(
                selectedItem = selectedItem
            )
        }
    }
}

