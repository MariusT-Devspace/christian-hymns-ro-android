package com.example.imnuricrestine.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle

object onGoBackCompanion {
    lateinit var onGoBack : () -> Unit
}

@Composable
fun Navigation(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues,
               mainViewModel: MainViewModel) {
// Navigation routes
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.IndexRoute.route) {
        composable(Route.IndexRoute.route) { HymnsIndex(indexTitleList, contentPadding, navController, mainViewModel) }
        composable(
            Route.HymnDetailsRoute.route + "/{hymnId}",
            listOf(
                navArgument("hymnId"){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val argument = navBackStackEntry.arguments!!.getInt("hymnId")
            HymnDetails(hymnId = argument) }
    }

    onGoBackCompanion.onGoBack = {
        navController.popBackStack()
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.IndexRoute.route) ) {
            //MainActivity.surfaceZIndexState.value = 1f
            mainViewModel.updateTopAppBar(TopAppBar.LARGETOPAPPBAR, TopAppBarTitle.TITLEINDEX.title, Icons.Filled.Menu, { MainViewModel::onOpenMenu })
        }
        Log.d("GOBACKCALLBACK", "GOING BACK!!!")
    }

    BackHandler(
    ) {
        navController.popBackStack()
        Log.d("BACKHANDLER", "BackHandler")
        if (navController.currentBackStackEntry!!.destination.route.equals(Route.IndexRoute.route) ) {
            //MainActivity.surfaceZIndexState.value = 1f
            mainViewModel.updateTopAppBar(TopAppBar.LARGETOPAPPBAR, TopAppBarTitle.TITLEINDEX.title, Icons.Filled.Menu, { MainViewModel::onOpenMenu })
        }
    }
}