package com.mtcnextlabs.imnuricrestine.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.rememberNavBackStack
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logScreenView
import com.mtcnextlabs.imnuricrestine.ui.navigation.BottomNavBar
import com.mtcnextlabs.imnuricrestine.ui.navigation.Navigation
import com.mtcnextlabs.imnuricrestine.ui.navigation.Screen
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@Composable
fun HymnsApp() {
    Log.d("RECOMPOSITION", "setContent")

    val backStack = rememberNavBackStack(Screen.Hymns)
    val currentDestination = backStack.lastOrNull()
    var showBottomNavBar by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        showBottomNavBar = when (currentDestination) {
            is Screen.HymnDetail -> false
            else -> true
        }

        currentDestination?.let {
            logScreenView(it.toString())
            Log.d("SCREEN", it.toString())
        }
    }

    val hymnListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                backStack,
                showBottomNavBar,
                hymnListState,
                favoritesListState
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Navigation(
                backStack,
                padding,
                hymnListState,
                favoritesListState,
                snackbarHostState,
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HymnsAppPreview() {
    ChristianHymnsTheme {
        HymnsApp()
    }
}