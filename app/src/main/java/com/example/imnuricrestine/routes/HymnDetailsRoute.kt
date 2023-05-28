package com.example.imnuricrestine.routes

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.imnuricrestine.MainActivity
import com.google.gson.Gson

@Composable
fun HymnDetails(hymnId: Int, navController: NavController) {
    val hymn = MainActivity.hymnsList.value!![hymnId]
    MainActivity.titleState.value = hymn.title
    MainActivity.topAppBarState.heightOffset = 20f
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