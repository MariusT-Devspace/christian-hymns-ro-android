package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.imnuricrestine.data.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.data.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.imnuricrestine.components.DrawerSheet
import com.example.imnuricrestine.components.MyTopAppBar
import com.example.imnuricrestine.data.db.entities.Favorites
import com.example.imnuricrestine.navigation.navigationActions
import com.example.imnuricrestine.state.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var hymnsViewModel: HymnsViewModel
    companion object {
        lateinit var hymns : LiveData<ArrayList<Hymn>>
        lateinit var favorites: LiveData<List<Favorites>>
        lateinit var topAppBarState : TopAppBarState
    }
    data class IndexTitle (val index: String, val title: String)
    lateinit var hynmsIndexTitle: List<IndexTitle>
    lateinit var favoritesIndexTitle: List<IndexTitle>
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hymnsViewModel = ViewModelProvider(this)[HymnsViewModel::class.java]
        hymns = hymnsViewModel.hymns
        favorites = hymnsViewModel.favorites

        sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        hynmsIndexTitle = if (!sharedPreferences.contains("hymnsIndexTitle")) {
            hymns.value!!.map { hymn ->
                IndexTitle(index = hymn.index, title = hymn.title)
            }
        } else {
            val gson = Gson()
            val arrayIndexTitleType = object : TypeToken<ArrayList<IndexTitle>>() {}.type
            gson.fromJson(sharedPreferences.getString("hymnsIndexTitle", null), arrayIndexTitleType)
        }

        favoritesIndexTitle = if (!sharedPreferences.contains("favoritesIndexTitle")) {
            favorites.value!!.map { item ->
                IndexTitle(
                    index = hymns.value!![item.hymn_id.toInt()].index,
                    title = hymns.value!![item.hymn_id.toInt()].title
                )
            }
        } else {
            val gson = Gson()
            val arrayIndexTitleType = object : TypeToken<ArrayList<IndexTitle>>() {}.type
            gson.fromJson(sharedPreferences.getString("hymnsIndexAndTitle", null), arrayIndexTitleType)
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
                        Log.d("OPENMENU", "OPENING MENU")
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
                                hynmsIndexTitle, favoritesIndexTitle,
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
            editPreferences.putString("hymnsIndexTitle", gson.toJson(hynmsIndexTitle)).apply()
        }

        if (!sharedPreferences.contains("favoritesIndexTitle")) {
            editPreferences.putString("favoritesIndexTitle", gson.toJson(favoritesIndexTitle)).apply()
        }
    }

}