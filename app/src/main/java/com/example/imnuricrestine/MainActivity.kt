package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.imnuricrestine.components.DrawerSheet
import com.example.imnuricrestine.components.MyTopAppBar
import com.example.imnuricrestine.data.favorites.FavoritesViewModel
import com.example.imnuricrestine.data.db.entities.Favorite
import com.example.imnuricrestine.models.HymnsListItem
import com.example.imnuricrestine.navigation.navigationActions
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.utils.asHymnsListItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        lateinit var hymns : LiveData<ArrayList<Hymn>>
        lateinit var favorites: LiveData<ArrayList<Favorite>>
        lateinit var topAppBarState : TopAppBarState
    }

    lateinit var hymnsListItems: List<HymnsListItem>
    lateinit var favoritesListItems: List<HymnsListItem>
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load data
        val hymnsViewModel by viewModels<HymnsViewModel>()
        val favoritesViewModel by viewModels<FavoritesViewModel>()
        hymns = hymnsViewModel.hymns
        favorites = favoritesViewModel.favorites

        // Shared Preferences
        sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        val hymnsListItemsType = object : TypeToken<ArrayList<HymnsListItem>>() {}.type

        hymnsListItems = if (!sharedPreferences.contains("hymnsListItems")) {
            hymns.value!!.map { hymn -> hymn.asHymnsListItem() }
        } else {
            val gson = Gson()
            gson.fromJson(sharedPreferences.getString("hymnsListItems", null), hymnsListItemsType)
        }

        favoritesListItems = if (!sharedPreferences.contains("favoritesListItems")) {
            favorites.value!!.map { favorite -> favorite.asHymnsListItem() }
        } else {
            val gson = Gson()
            gson.fromJson(sharedPreferences.getString("favoritesListItems", null), hymnsListItemsType)
        }


        setContent {
            // TopBar state
            topAppBarState = rememberTopAppBarState()
            val exitUntilCollapsedScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
            val mainViewModel : MainViewModel = viewModel()
            val topAppBarUiState by mainViewModel.topAppBarUiState.collectAsState()

            ChristianHymnsTheme {
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerSheet(drawerState, scope, navController, mainViewModel)
                    },
                ) {
                    navigationActions.onOpenMenu = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                    mainViewModel.updateTopAppBar(onNavigationAction = { navigationActions.onOpenMenu() })
                    // A surface container using the 'background' color from the theme
                    Scaffold(
                        modifier = Modifier.nestedScroll(exitUntilCollapsedScrollBehavior.nestedScrollConnection),
                        topBar = {
                            MyTopAppBar(
                                topAppBarUiState,
                                exitUntilCollapsedScrollBehavior
                            )
                        },
                    ) { padding ->
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            // Navigation composable
                            Navigation(
                                hymnsListItems, favoritesListItems,
                                padding, mainViewModel, navController
                            )
                        }

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
        sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val editPreferences: SharedPreferences.Editor = sharedPreferences.edit()

        if (!sharedPreferences.contains("hymnsIndexTitle")) {
            editPreferences.putString("hymnsIndexTitle", gson.toJson(hymnsListItems)).apply()
        }

        if (!sharedPreferences.contains("favoritesIndexTitle")) {
            editPreferences.putString("favoritesIndexTitle", gson.toJson(favoritesListItems)).apply()
        }
    }

}