package com.example.imnuricrestine.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.R
import com.example.imnuricrestine.SurfaceTopPadding
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex

object goBackCallback {
    lateinit var goBack : (NavController) -> Unit
}

@Composable
fun Navigation(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues, ) {
// Navigation routes
    val navController = rememberNavController()
    val topBarTitleIndex = stringResource(R.string.top_bar_title)


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

    goBackCallback.goBack = { navController
        navController.popBackStack()
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.IndexRoute.route) ) {
            MainActivity.surfaceTopPaddingState.value = SurfaceTopPadding.SURFACE_TOP_PADDING_INDEX.padding
            MainActivity.topAppBarZIndexState.value = 2f
            MainActivity.surfaceZIndexState.value = 1f
            Navigation.updateTopAppBar(topBarTitleIndex, Icons.Filled.Menu,
                MainActivity.exitUntilCollapsedScrollBehavior, MainActivity.openMenu)
        }
        Log.d("GOBACKCALLBACK", "GOING BACK!!!")
    }

    BackHandler(
    ) {
        navController.popBackStack()
        Log.d("BACKHANDLER", "BackHandler")
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.IndexRoute.route) ) {
            MainActivity.surfaceTopPaddingState.value = SurfaceTopPadding.SURFACE_TOP_PADDING_INDEX.padding
            MainActivity.topAppBarZIndexState.value = 2f
            MainActivity.surfaceZIndexState.value = 1f
            Navigation.updateTopAppBar(topBarTitleIndex, Icons.Filled.Menu,
                MainActivity.exitUntilCollapsedScrollBehavior, MainActivity.openMenu)
        }
    }
}

fun Navigation.updateTopAppBar(topBarTitle : String, topBarActionIcon : ImageVector,
                               topAppBarScrollBehavior: TopAppBarScrollBehavior?, navigationAction : () -> Unit) {
    MainActivity.topBarTitleState.value = topBarTitle
    MainActivity.navigationIconState.value = topBarActionIcon
    MainActivity.scrollBehavior.value = topAppBarScrollBehavior!!
    MainActivity.navigationAction.value = navigationAction
}