package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity.Companion.OnFavoriteAction
import com.example.imnuricrestine.components.Favorites
import com.example.imnuricrestine.models.FavoritesListItem
import com.example.imnuricrestine.state.FavoriteAction
import com.example.imnuricrestine.utils.ICONS
import com.example.imnuricrestine.utils.TopAppBarTitle

@Composable
fun FavoritesScreen(
    navController: NavHostController,
    favoritesListItems: List<FavoritesListItem>,
    onFavoriteActions: OnFavoriteAction,
    updateHymnsListItem: (Int, Boolean, FavoriteAction, String) -> Unit
) {
    val pinnedScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val actions : @Composable (RowScope.() -> Unit) = {
        IconButton(onClick = { /* doSomething() */ }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description"
            )
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(
            pinnedScrollBehavior.nestedScrollConnection
        ),
        topBar = {
            TopAppBar(
                title = { Text(TopAppBarTitle.FAVORITES.title) },
                scrollBehavior = pinnedScrollBehavior,
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
    ) { padding ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Favorites(
                padding,
                navController,
                favoritesListItems,
                onFavoriteActions.deleteFavorite,
                updateHymnsListItem
            )
        }
    }
}