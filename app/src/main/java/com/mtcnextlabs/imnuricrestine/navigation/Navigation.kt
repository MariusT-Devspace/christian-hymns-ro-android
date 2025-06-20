package com.mtcnextlabs.imnuricrestine.navigation

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
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.screens.FavoritesScreen
import com.mtcnextlabs.imnuricrestine.screens.HymnDetailScreen
import com.mtcnextlabs.imnuricrestine.screens.IndexScreen
import com.mtcnextlabs.imnuricrestine.state.ShowSnackbar

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
            IndexScreen(
                navController,
                hymns,
                indexListState,
                topAppBarScrollBehavior,
                favoriteActions,
                showSnackbar
            )
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
                )
        }
        composable(Route.Favorites.route) {
            FavoritesScreen(
                padding,
                navController,
                hymns,
                favorites,
                favoritesListState,
                favoriteActions,
                showSnackbar
            )
        }
    }

    // Back handling
    NavigationActions.onGoBack = {
        navController.popBackStack()
        Log.d("GO_BACK", "Index")
    }

    BackHandler {
        val currentDestination = navController.currentBackStackEntry?.destination?.id
        val startDestination = navController.graph.startDestinationId

        if (currentDestination == startDestination)
            activity?.finish()
        else
            NavigationActions.onGoBack()
    }
}