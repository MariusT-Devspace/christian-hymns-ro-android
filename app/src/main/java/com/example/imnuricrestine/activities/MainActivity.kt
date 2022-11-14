package com.example.imnuricrestine.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.imnuricrestine.R
import com.example.imnuricrestine.models.Hymn
import com.example.imnuricrestine.services.HymnsViewModel
import com.example.imnuricrestine.ui.theme.ChristianHymnsTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hymnsModel = ViewModelProvider(this)[HymnsViewModel::class.java]


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
                                    overflow = TextOverflow.Ellipsis
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
                        HymnList(hymnsModel.hymns, contentPadding = padding)
                    }

                }
            }
        }
    }

    @Composable
    fun HymnList(hymnsListLiveData: MutableLiveData<ArrayList<Hymn>>, contentPadding: PaddingValues) {
        val hymnsList by hymnsListLiveData.observeAsState(initial = emptyList())

        LazyColumn(
            contentPadding = contentPadding
        ) {
                items(items = hymnsList) { hymn ->
                    ListItem(
                        headlineText = { Text(hymn.title) },
                        leadingContent = {
                            Text(hymn.index.toString())
                        }
                    )
                    Divider()

                }

        }
    }

    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ChristianHymnsTheme {
            Greeting("Android")
        }
    }
}