package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imnuricrestine.MainActivity

@Composable
fun HymnDetails(hymnId: Int, navController: NavController) {
    val hymn = MainActivity.hymnsList.value!![hymnId]
    MainActivity.topBarTitleState.value = hymn.title
    MainActivity.topAppBarState.heightOffset = 20f
    MainActivity.navigationIconState.value = Icons.Filled.ArrowBack

    Column(
        modifier = Modifier.padding(start = 40.dp, top = 170.dp, end = 40.dp, bottom = 80.dp)
            .verticalScroll(rememberScrollState())
    ) {
        for (verse in hymn.lyrics) {
            ListItem(
                leadingContent = { Text(
                    text = verse.tag,
                    fontSize = 20.sp
                )},
                headlineContent = { Text(
                    text = verse.lyrics,
                    fontSize = 20.sp
                )}
            )

        }
    }

}