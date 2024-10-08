package com.example.imnuricrestine.components

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import com.example.imnuricrestine.state.TopAppBarType
import com.example.imnuricrestine.state.TopAppBarUiState

@Composable
fun MyTopAppBar(
    topAppBarUiState : TopAppBarUiState,
    scrollBehavior: TopAppBarScrollBehavior
){
    val navigationIcon : @Composable () -> Unit = {
        IconButton(onClick = topAppBarUiState.onNavigationAction) {
            topAppBarUiState.navigationIcon()
        }
    }
    val actions : @Composable() (RowScope.() -> Unit) = {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    Log.d("TOPAPPBARCOMPOSABLE", "TopAppBar composable is called")


    when(topAppBarUiState.topAppBar) {
        TopAppBarType.LARGE_TOP_APP_BAR -> {
            LargeTopAppBar(
                title = { Text(topAppBarUiState.title) },
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior
            )
        }
        TopAppBarType.SMALL_TOP_APP_BAR -> {
            TopAppBar(
                title = { Text(topAppBarUiState.title) },
                navigationIcon = navigationIcon,
                actions = actions
            )
        }
    }

}