package com.example.imnuricrestine.routes

import android.content.res.Resources
import android.provider.Settings.Global.getString
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.R
import com.example.imnuricrestine.navigation.Route
import com.google.gson.Gson

@Composable
fun HymnsIndex(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues, navController: NavHostController) {
    val state = remember {
        mutableStateOf(indexTitleList)
    }
    LazyColumn(
        contentPadding = contentPadding,
        modifier = Modifier.padding(top = 30.dp)
    ) {
        items(
            items = state.value
        ) { item ->
            ListItem(
                headlineContent = {
                    Text(
                        item.title,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp)
                    )
                },
                leadingContent = {
                    Text(
                        item.index.toString(),
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .padding(7.dp)
                            .width(30.dp),
                        fontSize = 20.sp
                    )
                },
                //tonalElevation = Dp(2.0f),
                modifier = Modifier.clickable {
                    val gson = Gson()
                    val hymnId = item.index.toInt()-1
                    navController.navigate(Route.HymnDetailsRoute.route+"/$hymnId")
                }

            )
            //Divider()

        }
    }
}