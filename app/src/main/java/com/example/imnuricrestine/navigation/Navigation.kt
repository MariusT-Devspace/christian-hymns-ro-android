package com.example.imnuricrestine.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.routes.Favorites
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle
import com.example.imnuricrestine.MainActivity.Companion.OnFavoriteAction
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.state.FavoriteAction
import java.util.concurrent.CompletableFuture

object navigationActions {
    lateinit var onGoBack : () -> Unit
    lateinit var onOpenMenu : () -> Unit
}

@Composable
fun Navigation(
    hymnsListItems: State<List<HymnsListItemUiState>>,
    favoritesListItems: List<FavoritesListItem>,
    contentPadding: PaddingValues,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    onFavoriteActions: OnFavoriteAction,
    updateItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    // Navigation routes
    NavHost(navController = navController, startDestination = Route.Index.route) {
        composable(Route.Index.route) {
            HymnsIndex(
                hymnsListItems, contentPadding,
                navController, mainViewModel,
                onFavoriteActions, updateItem
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
            HymnDetails(hymnId = argument)
        }
        composable(Route.Favorites.route) {
            Favorites(favoritesListItems, contentPadding, navController, mainViewModel)
        }
    }

    navigationActions.onGoBack = {
        navController.popBackStack()
        val currentRoute = navController.currentBackStackEntry!!.destination.route

        if (currentRoute.equals(Route.Index.route) ||
            currentRoute.equals(Route.Favorites.route)
            ) {
            mainViewModel.updateTopAppBar(
                navigationIcon = Icons.Filled.Menu,
                onNavigationAction = { navigationActions.onOpenMenu() }
            )

            when (currentRoute) {
                Route.Index.route -> mainViewModel.updateTopAppBar(
                    title = TopAppBarTitle.INDEX.title,
                    topAppBar = TopAppBar.LARGETOPAPPBAR,
                )
                Route.Favorites.route ->
                    mainViewModel.updateTopAppBar(
                        title = TopAppBarTitle.FAVORITES.title,
                        topAppBar = TopAppBar.SMALLTOPAPPBAR,
                    )
            }
        }

    }

    BackHandler(
    ) {
        navigationActions.onGoBack()
    }
}