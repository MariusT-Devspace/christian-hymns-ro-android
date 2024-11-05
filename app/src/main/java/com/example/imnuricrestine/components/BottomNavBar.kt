package com.example.imnuricrestine.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingAppBarDefaults
import androidx.compose.material3.FloatingAppBarExitDirection.Companion.Bottom
import androidx.compose.material3.FloatingAppBarScrollBehavior
import androidx.compose.material3.HorizontalFloatingAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.imnuricrestine.navigation.Route

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    NavigationBar {
        val destinations = listOf(
            Route.Index,
            Route.Favorites
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        destinations.forEach { route ->
            NavigationBarItem(
                icon = {
                    when(currentDestination?.route == route.route) {
                        true ->
                            Icon(route.iconSelected!!, contentDescription = route.title)
                        false ->
                            Icon(route.iconNotSelected!!, contentDescription = route.title)
                    }
                },
                label = { Text(route.title) },
                selected = currentDestination?.route == route.route,
                onClick = {
                    navController.navigate(route.route)
                }
            )
        }
    }
}