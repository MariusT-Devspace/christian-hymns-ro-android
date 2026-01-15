package com.mtcnextlabs.imnuricrestine.ui.navigation

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logBottomBarNavigation
import kotlinx.coroutines.launch

@Composable
fun BottomNavBar(
    backStack: NavBackStack<NavKey>,
    showBottomNavBar: Boolean,
    hymnListState: LazyListState,
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
                Screen.Hymns,
                Screen.Favorites
            )

            val currentDestination = backStack.lastOrNull()

            destinations.forEach { route ->
                NavigationBarItem(
                    icon = {
                        if (currentDestination == route)
                            Icon(route.iconSelected!!, contentDescription = route.title)
                        else
                            Icon(route.iconNotSelected!!, contentDescription = route.title)

                    },
                    label = { Text(route.title) },
                    selected = currentDestination == route,
                    onClick = {
                        when (route.route) {
                            Screen.Hymns.route ->
                                if (currentDestination == route &&
                                    hymnListState.firstVisibleItemIndex > 0)
                                    coroutineScope.launch {
                                        hymnListState.animateScrollToItem(0)
                                    }
                                else
                                    backStack.add(route)

                            Screen.Favorites.route ->
                                if (currentDestination == route &&
                                    favoritesListState.firstVisibleItemIndex > 0)
                                    coroutineScope.launch {
                                        favoritesListState.animateScrollToItem(0)
                                    }
                                else
                                    backStack.add(route)
                        }

                        logBottomBarNavigation(route.title)
                    }
                )
            }
        }
    }
}