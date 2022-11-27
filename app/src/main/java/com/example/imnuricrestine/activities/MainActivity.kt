package com.example.imnuricrestine.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import com.example.imnuricrestine.R
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.services.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    lateinit var hymnsModel: HymnsViewModel //= ViewModelProvider(this)[HymnsViewModel::class.java]
    lateinit var hymnsList: MutableLiveData<ArrayList<Hymn>> //= hymnsModel.hymns
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
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            ChristianHymnsTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text(
                                    stringResource(R.string.top_bar_title),
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
                        HymnList(indexTitleList, contentPadding = padding)
                    }

                }
            }
        }
    }

    @Composable
    fun HymnList(indexTitleList: List<IndexTitle>, contentPadding: PaddingValues) {
        //val hymnsList by hymnsModel.hymns.observeAsState(initial = emptyList())
        val state = remember {
            mutableStateOf(indexTitleList)
        }

        LazyColumn(
            contentPadding = contentPadding
        ) {
                items(
                    items = state.value
                ) { item ->
                    ListItem(
                        headlineText = {
                            Text(
                                item.title,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)
                            )
                        },
                        leadingContent = {
                            Text(
                                item.index.toString(),
                                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(25.dp)
                                ).padding(7.dp).width(30.dp),
                                fontSize = 20.sp
                            )
                        }
                    )
                    //Divider()

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