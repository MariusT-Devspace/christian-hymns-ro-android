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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.mtcnextlabs.imnuricrestine.ui.navigation.Navigation
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnsViewModel
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme
import androidx.navigation.compose.rememberNavController
import com.mtcnextlabs.imnuricrestine.analytics.AppAnalytics.logScreenView
import com.mtcnextlabs.imnuricrestine.ui.components.BottomNavBar
import com.mtcnextlabs.imnuricrestine.data.db.entities.Favorite
import com.mtcnextlabs.imnuricrestine.data.favorites.FavoritesViewModel
import com.mtcnextlabs.imnuricrestine.models.FavoriteActions
import com.mtcnextlabs.imnuricrestine.ui.navigation.Route
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoriteSnackbarViewModel
import com.mtcnextlabs.imnuricrestine.ui.screens.favorites.FavoriteUiEventHandler.undoDelete
import com.mtcnextlabs.imnuricrestine.ui.screens.index.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        var indexScreenPages: MutableState<List<Page>> = mutableStateOf(emptyList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data
        val hymnsViewModel by viewModels<HymnsViewModel>()
        val favoritesViewModel by viewModels<FavoritesViewModel>()

        val favoriteActions = FavoriteActions(
            addFavorite = favoritesViewModel::addFavorite,
            deleteFavorite = favoritesViewModel::deleteFavorite,
            undoDelete = favoritesViewModel::undoDelete
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

            // Snackbar
            val snackbarViewModel: FavoriteSnackbarViewModel = hiltViewModel()
            val snackbarHostState = remember { SnackbarHostState() }
            var snackbarJob by remember { mutableStateOf<Job?>(null) }

            LaunchedEffect(Unit) {
                snackbarViewModel.snackBarEvent.collect { data ->
                    snackbarJob?.cancel()
                    snackbarJob = launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            data.message,
                            if (data.undoAction) "Anulează" else null,
                            duration = SnackbarDuration.Short
                        )
                        if (snackbarResult == SnackbarResult.ActionPerformed)
                            undoDelete(
                                Favorite(
                                    data.favoritesListItem!!.id,
                                    data.favoritesListItem.hymnId
                                ),
                                favoriteActions
                            )
                    }
                }
            }

            val indexListState = rememberLazyListState()
            val favoritesListState = rememberLazyListState()

            val indexScreenTopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                rememberTopAppBarState(),
                { true }
            )

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
                            { hymns.value },
                            { favorites.value },
                            indexListState,
                            favoritesListState,
                            indexScreenTopAppBarScrollBehavior,
                            favoriteActions,
                            snackbarViewModel::showSnackbar
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