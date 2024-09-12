package com.example.imnuricrestine.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.imnuricrestine.components.TextWithRoundedRectangle

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
                val verseModifier = if (verse.isChorus) {
                    Modifier.padding(start = 36.dp, end = 16.dp, top = 20.dp, bottom = 66.dp)
                } else {
                    Modifier.padding(start = 16.dp, end = 16.dp, bottom = 46.dp)
                }

                Column(
                    modifier = verseModifier,
                    horizontalAlignment = Alignment.Start
                )  {
                    var tagModifier = Modifier.padding(bottom = 10.dp)
                    if (verse.isChorus) {
                        tagModifier = tagModifier.background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                            .padding(10.dp)
                    }

                    when(verse.isChorus) {
                        false ->  {
                            Text(
                                text = verse.tag + ".",
                                fontSize = 30.sp,
                                modifier = tagModifier
                            )
                        }
                        true -> {
                            TextWithRoundedRectangle(
                                text = verse.tag,
                                modifier = Modifier
                                    .padding(bottom = 16.dp),
                                fontSize = 30.sp,
                                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                                textColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

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