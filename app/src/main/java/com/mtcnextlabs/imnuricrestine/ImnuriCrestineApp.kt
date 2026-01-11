package com.mtcnextlabs.imnuricrestine

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logScreenView
import com.mtcnextlabs.imnuricrestine.ui.navigation.BottomNavBar
import com.mtcnextlabs.imnuricrestine.ui.navigation.Navigation
import com.mtcnextlabs.imnuricrestine.ui.navigation.Route
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme

@Composable
fun ImnuriCrestineApp() {
    val navController = rememberNavController()
    Log.d("RECOMPOSITION", "setContent")

    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route?.substringBefore("/")
    val showBottomNavBar = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        when (currentDestination) {
            Route.HymnDetails.route -> showBottomNavBar.value = false
            else -> showBottomNavBar.value = true
        }

        logScreenView(currentDestination ?: "Unknown")
    }

    val indexListState = rememberLazyListState()
    val favoritesListState = rememberLazyListState()

    val indexScreenTopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController,
                showBottomNavBar.value,
                indexListState,
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
                indexListState,
                favoritesListState,
                snackbarHostState,
                indexScreenTopAppBarScrollBehavior
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ImnuriCrestineAppPreview() {
    ChristianHymnsTheme {
        ImnuriCrestineApp()
    }
}