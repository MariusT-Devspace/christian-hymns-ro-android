package com.example.imnuricrestine.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.R
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController,
    mainViewModel: MainViewModel
    ) {
    ModalDrawerSheet {
        val destinations = listOf(
            Route.Index,
            Route.Favorites
        )

        val navigationDrawerUiState by mainViewModel.navigationDrawerUiState.collectAsState()

        Text(
            stringResource(R.string.navigation_drawer_header),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
            color = MaterialTheme.typography.titleMedium.color,
            modifier = Modifier.padding(25.dp)
        )

        for (item in destinations) {
            NavigationDrawerItem(
                icon = { Icon(
                    imageVector = item.icon,
                    contentDescription = item.title
                ) },
                label = { Text(text = item.title) },
                selected = navigationDrawerUiState.selectedItem == item,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigate(item.route)
                    mainViewModel.updateNavigationDrawer(item)
                    when (item) {
                        Route.Index -> mainViewModel.updateTopAppBar(
                            TopAppBar.LARGETOPAPPBAR,
                            TopAppBarTitle.TITLEINDEX.title
                        )
                        Route.Favorites -> mainViewModel.updateTopAppBar(
                            TopAppBar.SMALLTOPAPPBAR,
                            Route.Favorites.title
                        )
                        else -> {}
                    }
                },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}