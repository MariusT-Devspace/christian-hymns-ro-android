package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imnuricrestine.MainActivity

@Composable
fun HymnDetails(hymnId: Int) {
    val hymn = MainActivity.hymns.value!![hymnId]

    Column(
        modifier = Modifier.padding(top = 80.dp, bottom = 10.dp)
    ) {
        Text(
            hymn.title,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            modifier = Modifier.padding(start = 16.dp, end = 40.dp, bottom = 28.dp),
            style = TextStyle(lineHeight = 35.sp),
            fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            for (verse in hymn.lyrics) {
                Row(verticalAlignment = Alignment.Top) {
                    Text(
                        text = verse.tag + ".",
                        fontSize = 26.sp
                    )
                    Text(
                        text = verse.lyrics,
                        fontSize = 24.sp,
                        style = TextStyle(lineHeight = 35.sp),
                    )
                }
            }
        }
    }
}