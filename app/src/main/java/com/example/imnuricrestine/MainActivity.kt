package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.data.hymns.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.imnuricrestine.components.BottomNavBar
import com.example.imnuricrestine.components.MyTopAppBar
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.favorites.FavoritesViewModel
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.FavoriteIcon
import com.example.imnuricrestine.state.HymnsListViewModel
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.utils.asFavoritesListItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.CompletableFuture

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var hymns : LiveData<ArrayList<Hymn>>
        //lateinit var favoritesMutable: MutableLiveData<ArrayList<Favorite>>
        //lateinit var favorites: LiveData<List<Favorite>>
        lateinit var topAppBarState : TopAppBarState
        data class OnFavoriteAction(
            val addFavorite: (Favorite) -> CompletableFuture<Void>,
            val deleteFavorite: (Favorite) -> CompletableFuture<Void>
        )
        lateinit var favoriteActions: OnFavoriteAction
        lateinit var favorites: State<List<Favorite>>
    }

    lateinit var hymnsListItems: List<HymnsListItemUiState>
    lateinit var favoritesListItems: List<FavoritesListItem>
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data
        val hymnsViewModel by viewModels<HymnsViewModel>()
        val favoritesViewModel by viewModels<FavoritesViewModel>()
        val hymnsListViewModel by viewModels<HymnsListViewModel>()
        hymns = hymnsViewModel.hymns


        favoriteActions = OnFavoriteAction(
            addFavorite = favoritesViewModel::addFavorite,
            deleteFavorite = favoritesViewModel::deleteFavorite
        )

        //hymnsListItems = if (!sharedPreferences.contains("hymnsListItems")) {

//            } else {
//                val gson = Gson()
//                gson.fromJson(sharedPreferences.getString("hymnsListItems", null), hymnsListItemsType)
//            }

        setContent {
            // TopBar state
            topAppBarState = rememberTopAppBarState()
            val exitUntilCollapsedScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
            val mainViewModel : MainViewModel = viewModel()
            val topAppBarUiState by mainViewModel.topAppBarUiState.collectAsState()
            favorites = favoritesViewModel.favorites.observeAsState(emptyList())
            Log.d("RECOMPOSITION", "setContent")

            // Shared Preferences
            sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
            val hymnsListItemsType = object : TypeToken<ArrayList<HymnsListItemUiState>>() {}.type

            favoritesListItems = if (!sharedPreferences.contains("favoritesListItems")) {
                favorites.value.map { favorite -> favorite.asFavoritesListItem() }
            } else {
                val gson = Gson()
                gson.fromJson(sharedPreferences.getString("favoritesListItems", null), hymnsListItemsType)
            }

            LaunchedEffect(favorites.value.isNotEmpty()) {
                    for (favorite in favorites.value) {
                        Log.d("SET_CONTENT", "update favorite ${favorite.hymn_id}")
                        hymnsListViewModel.updateItem(
                            favorite.hymn_id - 1,
                            true,
                            FavoriteAction.DELETE_FAVORITE,
                            FavoriteIcon.SAVED.name
                        )
                    }
            }

            val hymnsListUiState = hymnsListViewModel.hymnUiStateListFlow.collectAsState()

            ChristianHymnsTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.nestedScroll(exitUntilCollapsedScrollBehavior.nestedScrollConnection),
                    topBar = {
                        MyTopAppBar(
                            topAppBarUiState,
                            exitUntilCollapsedScrollBehavior
                        )
                    },
                    bottomBar = {
                        BottomNavBar(navController, mainViewModel)
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Navigation composable
                        Navigation(
                            hymnsListUiState, favoritesListItems,
                            padding, mainViewModel, navController,
                            favoriteActions, hymnsListViewModel::updateItem
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

    override fun onPause() {
        super.onPause()
//        sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
//        val gson = Gson()
//        val editPreferences: SharedPreferences.Editor = sharedPreferences.edit()
//
//        if (!sharedPreferences.contains("hymnsListItems")) {
//            editPreferences.putString("hymnsListItems", gson.toJson(hymnsListItems)).apply()
//        }
//
//        if (!sharedPreferences.contains("hymnsListItems")) {
//            editPreferences.putString("hymnsListItems", gson.toJson(hymnsListItems)).apply()
//        }
    }

}