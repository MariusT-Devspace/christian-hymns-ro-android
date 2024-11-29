package com.example.imnuricrestine

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.data.hymns.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.navigation.compose.rememberNavController
import com.example.imnuricrestine.components.BottomNavBar
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.data.favorites.FavoritesViewModel
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.models.OnFavoriteActions
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.state.HymnsListViewModel
import com.example.imnuricrestine.state.Page
import com.example.imnuricrestine.utils.asFavoritesListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var favorites: State<List<Favorite>>
        lateinit var indexScreenPages: List<Page>
    }

    lateinit var favoritesListItems: List<FavoritesListItem>
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data
        val hymnsViewModel by viewModels<HymnsViewModel>()
        val favoritesViewModel by viewModels<FavoritesViewModel>()
        val hymns: LiveData<ArrayList<Hymn>> = hymnsViewModel.hymns

        val favoriteActions = OnFavoriteActions(
            addFavorite = favoritesViewModel::addFavorite,
            deleteFavorite = favoritesViewModel::deleteFavorite
        )

        setContent {
            val navController = rememberNavController()
            val hymnsListViewModel: HymnsListViewModel = viewModel { HymnsListViewModel(hymns) }
            val hymnsListItems = hymnsListViewModel.hymnUiStateListFlow.collectAsState()

            favorites = favoritesViewModel.favorites.observeAsState(emptyList())
            Log.d("RECOMPOSITION", "setContent")

            // Shared Preferences
            sharedPreferences = getSharedPreferences("hymnsSharedPreferences", MODE_PRIVATE)
            val hymnsListItemsType = object : TypeToken<ArrayList<HymnsListItemUiState>>() {}.type

            favoritesListItems = if (!sharedPreferences.contains("favoritesListItems")) {
                favorites.value.map { favorite ->
                    val hymnIndex = hymns.value!!.indexOf(hymns.value!!.find {
                        it.id == favorite.hymn_id
                    })
                    favorite.asFavoritesListItem(
                        hymns.value!![hymnIndex].index,
                        hymns.value!![hymnIndex].title
                    ) }
            } else {
                val gson = Gson()
                gson.fromJson(sharedPreferences.getString("favoritesListItems", null), hymnsListItemsType)
            }

            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStack?.destination?.route?.substringBefore("/")
            val showBottomNavBar = rememberSaveable { mutableStateOf(true) }

            LaunchedEffect(currentDestination) {
                when (currentDestination) {
                    Route.HymnDetails.route -> showBottomNavBar.value = false
                    else -> showBottomNavBar.value = true
                }
            }

            ChristianHymnsTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController, showBottomNavBar.value)
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Navigation(
                            padding,
                            navController,
                            hymnsListItems,
                            favoritesListItems,
                            favoriteActions,
                            hymnsListViewModel ::updateItem
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
    }

}