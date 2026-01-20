package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtcnextlabs.imnuricrestine.utils.ICONS
import com.mtcnextlabs.imnuricrestine.utils.TopAppBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3ExpressiveApi
@Composable
fun FavoritesTopAppBar(topAppBarScrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        title = { Text(TopAppBarTitle.FAVORITES.title) },
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
            Row(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.size(36.dp)
                ) {
                    ICONS.topAppBarLogo()
                }
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Localized description"
                )
            }
        },
        subtitle = {}
    )
}
