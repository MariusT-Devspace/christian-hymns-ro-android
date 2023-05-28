package com.example.imnuricrestine.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.routes.HymnDetails
import com.example.imnuricrestine.routes.HymnsIndex
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Composable
fun Navigation(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues, ) {
// Navigation routes
    val navController = rememberNavController()
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