package com.mtcnextlabs.imnuricrestine.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mtcnextlabs.imnuricrestine.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun BottomNavBar(
    navController: NavHostController,
    showBottomNavBar: Boolean,
    indexListState: LazyListState,
    favoritesListState: LazyListState
) {
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = showBottomNavBar,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(200))
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
                        when (currentDestination?.route == route.route) {
                            true ->
                                Icon(route.iconSelected!!, contentDescription = route.title)

                            false ->
                                Icon(route.iconNotSelected!!, contentDescription = route.title)
                        }
                    },
                    label = { Text(route.title) },
                    selected = currentDestination?.route == route.route,
                    onClick = {
                        when (route.route) {
                            Route.Index.route ->
                                if (currentDestination?.route == Route.Index.route)
                                    coroutineScope.launch {
                                        indexListState.animateScrollToItem(0)
                                    }
                                else
                                    navController.navigate(route.route)

                            Route.Favorites.route ->
                                if (currentDestination?.route == Route.Favorites.route)
                                    coroutineScope.launch {
                                        favoritesListState.animateScrollToItem(0)
                                    }
                                else
                                    navController.navigate(route.route)
                        }
                    }
                )
            }
        }
    }
}