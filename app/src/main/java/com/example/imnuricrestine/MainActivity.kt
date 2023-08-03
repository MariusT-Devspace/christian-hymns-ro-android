package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import com.example.imnuricrestine.data.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.data.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imnuricrestine.components.MyTopAppBar
import com.example.imnuricrestine.data.db.entities.Favorites
import com.example.imnuricrestine.state.MainViewModel

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
        }else{
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
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.nestedScroll(exitUntilCollapsedScrollBehavior.nestedScrollConnection),
                    topBar = {
                        MyTopAppBar(
                            topAppBar = topAppBarUiState.topAppBar,
                            title = {
                                    Text(
                                        topAppBarUiState.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                            navigationIcon = {
                                IconButton(onClick =  topAppBarUiState.onNavigationAction ) {
                                    Icon(
                                        imageVector = topAppBarUiState.navigationIcon,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },
                            scrollBehavior = exitUntilCollapsedScrollBehavior
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Navigation composable
                        Navigation(indexTitleList, padding, mainViewModel)
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