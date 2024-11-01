package com.example.imnuricrestine.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
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
    hymnsListItems: State<List<HymnsListItemUiState>>,
    favoritesListItems: List<FavoritesListItem>,
    navController: NavHostController,
    onFavoriteActions: OnFavoriteAction,
    updateHymnsListItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    // Navigation routes
    NavHost(
        navController = navController,
        startDestination = Route.Index.route,
    ) {
        composable(Route.Index.route) {
            IndexScreen(
                hymnsListItems,
                navController,
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
                    hymnId = argument,
                    navController
                )
        }
        composable(Route.Favorites.route) {
            FavoritesScreen(
                favoritesListItems,
                navController,
                onFavoriteActions,
                updateHymnsListItem
            )
        }
    }

    NavigationActions.onGoBack = {
        navController.popBackStack()
        Log.d("GO_BACK", "Index")
    }

    BackHandler {
        NavigationActions.onGoBack()
    }
}