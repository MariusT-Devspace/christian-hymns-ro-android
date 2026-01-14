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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logScreenView
import com.mtcnextlabs.imnuricrestine.ui.navigation.BottomNavBar
import com.mtcnextlabs.imnuricrestine.ui.navigation.Navigation
import com.mtcnextlabs.imnuricrestine.ui.navigation.Screen
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@Composable
fun HymnsApp() {
    val navController = rememberNavController()
    Log.d("RECOMPOSITION", "setContent")

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route?.substringBefore("/")
    var showBottomNavBar by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        when (currentDestination) {
            Screen.HymnDetails.route -> showBottomNavBar = false
            else -> showBottomNavBar = true
        }

        logScreenView(currentDestination ?: "Unknown")
    }

    val hymnListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController,
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
                padding,
                navController,
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