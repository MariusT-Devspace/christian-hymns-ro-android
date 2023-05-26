package com.example.imnuricrestine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.*
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.navigation.Navigation
import com.example.imnuricrestine.services.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    lateinit var hymnsModel: HymnsViewModel //= ViewModelProvider(this)[HymnsViewModel::class.java]
    companion object {
        lateinit var hymnsList: MutableLiveData<ArrayList<Hymn>> //= hymnsModel.hymns
        lateinit var topAppBarState: TopAppBarState
        lateinit var topBarTitle: MutableLiveData<String>
    }
    data class IndexTitle (val index: Short, val title: String)
    lateinit var indexTitleList: List<IndexTitle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hymnsModel = ViewModelProvider(this)[HymnsViewModel::class.java]
        //val model by viewModels<HymnsViewModel>()
        hymnsList = hymnsModel.hymns


        val sharedPreferences = getSharedPreferences("hymnsSharedPreferences", Context.MODE_PRIVATE)
        if (!sharedPreferences.contains("hymnsIndexAndTitle")){

            indexTitleList = hymnsList.value!!.map { hymn ->
                IndexTitle(index = hymn.index, title = hymn.title)
            }
        }else{
                val gson = Gson()
                val arrayIndexTitleType = object : TypeToken<ArrayList<IndexTitle>>() {}.type
                indexTitleList = gson.fromJson(sharedPreferences.getString("hymnsIndexAndTitle", null), arrayIndexTitleType)


        }

        setContent {
            // Scaffold
            topAppBarState = rememberTopAppBarState()
            topBarTitle = MutableLiveData(stringResource(R.string.top_bar_title))
            // TopBar title state
            val title = topBarTitle.observeAsState("")

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
            ChristianHymnsTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text(
                                    //stringResource(R.string.top_bar_title),
                                    title.value,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )},
                            navigationIcon = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
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
                            scrollBehavior = scrollBehavior
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        // Navigation composable
                        Navigation(indexTitleList, contentPadding = padding)
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