package com.example.imnuricrestine.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.imnuricrestine.MainActivity

//import com.example.imnuricrestine.navigation.goBackCallback

@Composable
fun HymnDetails(hymnId: Int, navController: NavController) {
    val hymn = MainActivity.hymnsList.value!![hymnId]
    MainActivity.topAppBarState.heightOffset = 20f
    //MainActivity.scrollBehavior.value = MainActivity.pinnedScrollBehavior

    Column(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Text(
            hymn.title,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 30.dp, bottom = 40.dp),
            style = TextStyle(lineHeight = 35.sp)
        )
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            for (verse in hymn.lyrics) {
                ListItem(
                    leadingContent = { Text(
                        text = verse.tag+".",
                        fontSize = 26.sp
                    )},
                    headlineContent = { Text(
                        text = verse.lyrics,
                        fontSize = 24.sp,
                        style = TextStyle(lineHeight = 35.sp)
                    )},
                    supportingContent = { },
                    overlineContent = {  }
                )
            }
        }
    }


}