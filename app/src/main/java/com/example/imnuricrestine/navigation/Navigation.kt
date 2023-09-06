package com.example.imnuricrestine.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imnuricrestine.models.HymnsListItem
import com.example.imnuricrestine.routes.Favorites
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle

object navigationActions {
    lateinit var onGoBack : () -> Unit
    lateinit var onOpenMenu : () -> Unit
}

@Composable
fun Navigation(
    hymnsListItems: List<HymnsListItem>,
    favoritesListItems: List<HymnsListItem>,
    contentPadding: PaddingValues,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    // Navigation routes
    NavHost(navController = navController, startDestination = Route.Index.route) {
        composable(Route.Index.route) {
            HymnsIndex(
                hymnsListItems, contentPadding,
                navController, mainViewModel
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
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.Index.route) ) {
            //MainActivity.surfaceZIndexState.value = 1f
            mainViewModel.updateTopAppBar(
                TopAppBar.LARGETOPAPPBAR, TopAppBarTitle.TITLEINDEX.title,
                Icons.Filled.Menu, { navigationActions.onOpenMenu() }
            )
        }
    }

    BackHandler(
    ) {
        navController.popBackStack()
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.Index.route) ) {
            //MainActivity.surfaceZIndexState.value = 1f
            mainViewModel.updateTopAppBar(
                TopAppBar.LARGETOPAPPBAR, TopAppBarTitle.TITLEINDEX.title,
                Icons.Filled.Menu, { navigationActions.onOpenMenu() }
            )
        }
    }
}