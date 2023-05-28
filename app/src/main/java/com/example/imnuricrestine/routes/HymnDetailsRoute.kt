package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imnuricrestine.MainActivity

@Composable
fun HymnDetails(hymnId: Int, navController: NavController) {
    val hymn = MainActivity.hymnsList.value!![hymnId]
    MainActivity.topBarTitleState.value = hymn.title
    MainActivity.topAppBarState.heightOffset = 20f
    MainActivity.navigationIconState.value = Icons.Filled.ArrowBack

    Column(
        modifier = Modifier.padding(top = 170.dp)
    ) {
        for (verse in hymn.lyrics) {
            Row {
                Text(text = verse.tag)
                Text(text = verse.lyrics)
            }
        }
    }

}