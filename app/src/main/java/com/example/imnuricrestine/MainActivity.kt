package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.*
import com.example.imnuricrestine.data.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.data.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.imnuricrestine.components.MyTopAppBar
import com.example.imnuricrestine.data.db.entities.Favorites
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.navigation.navigationActions
import com.example.imnuricrestine.state.MainViewModel
import kotlinx.coroutines.launch
import com.example.imnuricrestine.state.TopAppBar

class MainActivity : ComponentActivity() {
    lateinit var hymnsViewModel: HymnsViewModel
    companion object {
        lateinit var hymns : LiveData<ArrayList<Hymn>>
        lateinit var favorites: LiveData<List<Favorites>>
        lateinit var topAppBarState : TopAppBarState
    }
    data class IndexTitle (val index: String, val title: String)
    lateinit var indexTitleList: List<IndexTitle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hymnsViewModel = ViewModelProvider(this)[HymnsViewModel::class.java]
        hymns = hymnsViewModel.hymns
        favorites = hymnsViewModel.favorites

        val sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("hymnsIndexAndTitle")){
            indexTitleList = hymns.value!!.map { hymn ->
                IndexTitle(index = hymn.index, title = hymn.title)
            }
        } else {
                val gson = Gson()
                val arrayIndexTitleType = object : TypeToken<ArrayList<IndexTitle>>() {}.type
                indexTitleList = gson.fromJson(sharedPreferences.getString("hymnsIndexAndTitle", null), arrayIndexTitleType)
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
                        ModalDrawerSheet {
                            val destinations = listOf(
                                Route.Index,
                                Route.Favorites
                            )

                            val selectedItem = remember { mutableStateOf(destinations[0]) }
                            Spacer(Modifier.height(12.dp))
                            for (item in destinations) {
                                NavigationDrawerItem(
                                    label = { Text(text = item.title) },
                                    selected = selectedItem.value == item,
                                    onClick = {
                                        selectedItem.value = item
                                        scope.launch { drawerState.close() }
                                        navController.navigate(item.route)
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
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
                    mainViewModel.updateTopAppBar(
                        TopAppBar.LARGETOPAPPBAR,
                        stringResource(R.string.top_bar_title),
                        Icons.Filled.Menu,
                        navigationActions.onOpenMenu
                    )
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
                            Navigation(indexTitleList, padding, mainViewModel, navController)
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
        val sharedPreferences: SharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        val gson = Gson()
        if (!sharedPreferences.contains("hymnsIndexAndTitle")){
            val editPreferences: SharedPreferences.Editor = sharedPreferences.edit()
            editPreferences.putString("hymnsIndexAndTitle", gson.toJson(indexTitleList)).apply()
        }

    }

}