package com.mtcnextlabs.imnuricrestine.ui.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logNavigateToHymnDetails
import com.mtcnextlabs.imnuricrestine.ui.navigation.NavigationActions.onGoBack
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail.HymnDetailScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.HymnsScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object NavigationActions {
    lateinit var onGoBack : () -> Unit
}

@Composable
fun Navigation(
    padding: PaddingValues,
    navController: NavHostController,
    indexListState: LazyListState,
    favoritesListState: LazyListState,
    snackbarHostState: SnackbarHostState
) {
    val activity = LocalActivity.current as? ComponentActivity

    NavHost(
        navController = navController,
        startDestination = Screen.Hymns.route,
    ) {
        composable(Screen.Hymns.route) {
            HymnsScreen(
                listState = indexListState,
                snackbarHostState = snackbarHostState
            ) { id, title ->
                val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                navController.navigate("${Screen.HymnDetails.route}/$id?title=$encodedTitle")
                logNavigateToHymnDetails(id, "hymns screen")
            }
        }
        composable(
            Screen.HymnDetails.route + "/{id}?title={title}",
            listOf(
                navArgument("id"){
                    type = NavType.IntType
                },
                navArgument("title"){
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val title = navBackStackEntry.arguments?.getString("title") ?: ""
                HymnDetailScreen(
                    initialTitle = title
                ) {
                    onGoBack()
                }
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                contentPadding = padding,
                listState = favoritesListState,
                snackbarHostState = snackbarHostState
            ) { hymnId, title ->
                val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                navController.navigate("${Screen.HymnDetails.route}/$hymnId?title=$encodedTitle")
                logNavigateToHymnDetails(id, "favorites screen")
            }
        }
    }

    // Back handling
    onGoBack = {
        navController.popBackStack()
    }

    BackHandler {
        val currentDestination = navController.currentBackStackEntry?.destination?.id
        val startDestination = navController.graph.startDestinationId

        if (currentDestination == startDestination)
            activity?.finish()
        else
            onGoBack()
    }
}