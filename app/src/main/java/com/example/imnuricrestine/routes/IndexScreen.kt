package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingAppBarDefaults
import androidx.compose.material3.FloatingAppBarExitDirection.Companion.Bottom
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.components.BottomPaginationBar
import com.example.imnuricrestine.components.HymnsIndex
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.state.HymnsListItemUiState
import com.example.imnuricrestine.utils.ICONS
import com.example.imnuricrestine.utils.TopAppBarTitle

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun IndexScreen(
    hymnsListItems: State<List<HymnsListItemUiState>>,
    navController: NavHostController,
    onFavoriteActions: MainActivity.Companion.OnFavoriteAction,
    updateItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )
    val floatingAppBarScrollBehavior = FloatingAppBarDefaults.exitAlwaysScrollBehavior(
        exitDirection = Bottom
    )

    LaunchedEffect(Unit) {
        floatingAppBarScrollBehavior.state.offset = 0f
    }

    val actions : @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
            .nestedScroll(floatingAppBarScrollBehavior),
        topBar = {
            LargeTopAppBar(
                title = { Text(TopAppBarTitle.INDEX.title) },
                scrollBehavior = topAppBarScrollBehavior,
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        ICONS.topAppBarLogo()
                    }
                },
                actions = actions
            )
        },
        bottomBar = { BottomPaginationBar(floatingAppBarScrollBehavior) }
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HymnsIndex(
                hymnsListItems.value,
                padding,
                navController,
                onFavoriteActions,
                updateItem
            )
        }
    }
}