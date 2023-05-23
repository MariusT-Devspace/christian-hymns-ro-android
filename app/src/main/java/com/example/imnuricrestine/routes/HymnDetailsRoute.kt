package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imnuricrestine.models.Hymn

@Composable
fun HymnDetails(hymn: Hymn, navController: NavController) {
    Column() {
        Text(
            text = hymn.title,
            fontSize = 20.sp
        )
        for (verse in hymn.lyrics) {
            Row {
                Text(text = verse.tag)
                Text(text = verse.lyrics)
            }
        }
    }

}