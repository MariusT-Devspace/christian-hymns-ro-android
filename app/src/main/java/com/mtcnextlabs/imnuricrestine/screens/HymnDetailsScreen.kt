package com.mtcnextlabs.imnuricrestine.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import com.mtcnextlabs.imnuricrestine.components.HymnDetails
import com.mtcnextlabs.imnuricrestine.data.hymns.HymnsViewModel
import com.mtcnextlabs.imnuricrestine.navigation.NavigationActions
import com.mtcnextlabs.imnuricrestine.utils.ICONS

@Composable
fun HymnDetailsScreen(
    index: Int
) {
    val hymnsViewModel: HymnsViewModel = hiltViewModel()
    val hymn = hymnsViewModel.hymns.value!![index]

    val pinnedScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState(),
        { true }
    )

    val isScrolled by remember { derivedStateOf {
        pinnedScrollBehavior.state.contentOffset < -100f
    } }

    val largeTopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(
        containerColor = if (isScrolled)
            MaterialTheme.colorScheme.surfaceContainer
        else
            MaterialTheme.colorScheme.surface,
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
            LargeTopAppBar(
                title = { Text("${hymn.index}. ${hymn.title}") },
                scrollBehavior = pinnedScrollBehavior,
                colors = largeTopAppBarColors,
                navigationIcon = {
                    IconButton(
                        onClick = { NavigationActions.onGoBack() }
                    ) {
                        ICONS.backIcon()
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
            HymnDetails(
                hymn,
                padding
            )
        }
    }
}