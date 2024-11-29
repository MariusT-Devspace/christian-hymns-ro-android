package com.example.imnuricrestine.navigation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imnuricrestine.MainActivity.Companion.OnFavoriteAction
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.routes.FavoritesScreen
import com.example.imnuricrestine.routes.HymnDetailsScreen
import com.example.imnuricrestine.routes.IndexScreen
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.HymnsListItemUiState

object NavigationActions {
    lateinit var onGoBack : () -> Unit
    lateinit var onOpenMenu : () -> Unit
}

@Composable
fun Navigation(
    padding: PaddingValues,
    navController: NavHostController,
    hymnsListItems: State<List<HymnsListItemUiState>>,
    favoritesListItems: List<FavoritesListItem>,
    onFavoriteActions: OnFavoriteAction,
    updateHymnsListItem: (Int, Boolean, FavoriteAction, String) -> Unit
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
                hymnsListItems,
                onFavoriteActions,
                updateHymnsListItem
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
                HymnDetailsScreen(
                    index = argument
                )
        }
        composable(Route.Favorites.route) {
            FavoritesScreen(
                padding,
                navController,
                favoritesListItems,
                onFavoriteActions,
                updateHymnsListItem
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