package com.mtcnextlabs.imnuricrestine.ui.navigation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logNavigateToHymnDetails
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.ui.navigation.NavigationActions.onGoBack
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoritesScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymndetail.HymnDetailScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.hymns.HymnsScreen
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.ShowSnackbar

object NavigationActions {
    lateinit var onGoBack : () -> Unit
}

@Composable
fun Navigation(
    padding: PaddingValues,
    navController: NavHostController,
    hymns: () -> List<Hymn>,
    favorites: () -> List<Favorite>,
    indexListState: LazyListState,
    favoritesListState: LazyListState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    favoriteActions: FavoriteActions,
    showSnackbar: ShowSnackbar,
) {
    val activity = LocalActivity.current as? ComponentActivity

    // Navigation routes
    NavHost(
        navController = navController,
        startDestination = Route.Index.route,
    ) {
        composable(Route.Index.route) {
            HymnsScreen(
                hymns,
                indexListState,
                topAppBarScrollBehavior,
                favoriteActions,
                showSnackbar
            ) { index ->
                navController.navigate(Route.HymnDetails.route+"/$index")
                logNavigateToHymnDetails(index, "index screen")
            }
        }
        composable(
            Route.HymnDetails.route + "/{hymnId}",
            listOf(
                navArgument("hymnId"){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val argument = navBackStackEntry.arguments!!.getInt("hymnId")
                HymnDetailScreen(
                    index = argument
                ) {
                    onGoBack()
                }
        }
        composable(Route.Favorites.route) {
            FavoritesScreen(
                padding,
                hymns,
                favorites,
                favoritesListState,
                favoriteActions,
                showSnackbar
            ) { hymnId ->
                val index = hymnId - 1
                navController.navigate(Route.HymnDetails.route+"/$index")
                logNavigateToHymnDetails(index, "favorites screen")
            }
        }
    }

    // Back handling
    onGoBack = {
        navController.popBackStack()
        Log.d("GO_BACK", "Index")
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