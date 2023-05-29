package com.example.imnuricrestine.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.R
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex


@Composable
fun Navigation(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues, ) {
// Navigation routes
    val navController = rememberNavController()
    val topBarTitleIndex = stringResource(R.string.top_bar_title)

    BackHandler(
    ) {
        navController.popBackStack()
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.IndexRoute.route) ) {
            MainActivity.topBarTitleState.value = topBarTitleIndex
            MainActivity.navigationIconState.value = Icons.Filled.Menu
            MainActivity.scrollBehavior.value = MainActivity.exitUntilCollapsedScrollBehavior
        }

    }

    NavHost(navController = navController, startDestination = Route.IndexRoute.route) {
        composable(Route.IndexRoute.route) { HymnsIndex(indexTitleList, contentPadding, navController) }
        composable(
            Route.HymnDetailsRoute.route + "/{hymnId}",
            listOf(
                navArgument("hymnId"){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val argument = navBackStackEntry.arguments!!.getInt("hymnId")
            HymnDetails(hymnId = argument, navController = navController) }
    }
}