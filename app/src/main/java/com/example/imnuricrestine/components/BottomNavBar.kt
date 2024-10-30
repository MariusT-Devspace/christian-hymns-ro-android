package com.example.imnuricrestine.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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