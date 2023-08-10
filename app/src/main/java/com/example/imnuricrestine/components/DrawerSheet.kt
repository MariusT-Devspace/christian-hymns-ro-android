package com.example.imnuricrestine.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController
    ) {
    ModalDrawerSheet {
        val destinations = listOf(
            Route.Index,
            Route.Favorites
        )

        val selectedItem = remember { mutableStateOf(destinations[0]) }

        Spacer(Modifier.height(12.dp))

        for (item in destinations) {
            NavigationDrawerItem(
                icon = { Icon(
                    imageVector = item.icon,
                    contentDescription = item.title
                ) },
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
}