package com.mtcnextlabs.imnuricrestine.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtcnextlabs.imnuricrestine.models.Hymn

@Composable
fun HymnDetails(
    hymn: Hymn,
    contentPadding: PaddingValues
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .padding(top = 40.dp)
    ) {
        for (verse in hymn.lyrics) {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 46.dp),
                horizontalAlignment = Alignment.Start
            )  {
                Text(
                    text = verse.tag + ".",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
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