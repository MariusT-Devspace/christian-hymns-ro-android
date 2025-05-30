package com.mtcnextlabs.imnuricrestine

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mtcnextlabs.imnuricrestine.navigation.Navigation
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnsViewModel
import com.mtcnextlabs.imnuricrestine.theme.ChristianHymnsTheme
import androidx.navigation.compose.rememberNavController
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logScreenView
import com.mtcnextlabs.imnuricrestine.components.BottomNavBar
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesViewModel
import com.mtcnextlabs.imnuricrestine.models.FavoritesListItem
import com.mtcnextlabs.imnuricrestine.models.OnFavoriteActions
import com.mtcnextlabs.imnuricrestine.navigation.Route
import com.mtcnextlabs.imnuricrestine.state.FavoriteSnackbarViewModel
import com.mtcnextlabs.imnuricrestine.state.Page
import com.mtcnextlabs.imnuricrestine.utils.asFavoritesListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        var indexScreenPages: List<Page> = emptyList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data
        val hymnsViewModel by viewModels<HymnsViewModel>()
        val favoritesViewModel by viewModels<FavoritesViewModel>()

        val favoriteActions = OnFavoriteActions(
            addFavorite = favoritesViewModel::addFavorite,
            deleteFavorite = favoritesViewModel::deleteFavorite
        )

        setContent {
            val hymns = remember { mutableStateOf(hymnsViewModel.hymns) }

            // Observe updates from LiveData
            val liveHymns by hymnsViewModel.hymnsAsync.observeAsState()

            LaunchedEffect(liveHymns) {
                liveHymns?.let {
                    if (it.isNotEmpty())
                        hymns.value = it
                }
            }

            val navController = rememberNavController()
            val favorites: State<List<Favorite>> = favoritesViewModel.favorites.observeAsState(emptyList())

            Log.d("RECOMPOSITION", "setContent")

            val favoritesListItems: List<FavoritesListItem> =
                favorites.value.map { favorite ->
                    val hymnIndex = hymns.value.indexOf(hymns.value.find {
                        it.id == favorite.hymn_id
                    })
                    favorite.asFavoritesListItem(
                        hymns.value[hymnIndex].index,
                        hymns.value[hymnIndex].title
                    )
                }

            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination?.route?.substringBefore("/")
            val showBottomNavBar = rememberSaveable { mutableStateOf(true) }

            LaunchedEffect(currentDestination) {
                when (currentDestination) {
                    Route.HymnDetails.route -> showBottomNavBar.value = false
                    else -> showBottomNavBar.value = true
                }

                logScreenView(currentDestination ?: "Unknown")
            }

            val indexListState = rememberLazyListState()
            val favoritesListState = rememberLazyListState()

            val snackbarHostState = remember { SnackbarHostState() }

            ChristianHymnsTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            navController,
                            showBottomNavBar.value,
                            indexListState,
                            favoritesListState
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(
                            padding,
                            navController,
                            hymns,
                            favoritesListItems,
                            indexListState,
                            favoritesListState,
                            snackbarHostState,
                            favoriteActions
                        )
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ChristianHymnsTheme {
        }
    }
}