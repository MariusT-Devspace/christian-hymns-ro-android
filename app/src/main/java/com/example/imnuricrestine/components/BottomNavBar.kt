package com.example.imnuricrestine.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle

@Composable
fun BottomNavBar(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavigationBar {
        val destinations = listOf(
            Route.Index,
            Route.Favorites
        )

        NavigationBar() {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            destinations.forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, contentDescription = route.title) },
                    label = { Text(route.title) },
                    selected = currentDestination?.route == route.route,
                    onClick = {
                        navController.navigate(route.route)
                        when (route) {
                            Route.Index -> mainViewModel.updateTopAppBar(
                                TopAppBar.LARGETOPAPPBAR,
                                TopAppBarTitle.INDEX.title,
                                Icons.Filled.Menu,
                                { }
                            )

                            Route.Favorites -> mainViewModel.updateTopAppBar(
                                TopAppBar.SMALLTOPAPPBAR,
                                TopAppBarTitle.FAVORITES.title,
                                Icons.Filled.Menu,
                                { }
                            )

                            else -> {}
                        }
                    }
                )
            }
        }
    }
}