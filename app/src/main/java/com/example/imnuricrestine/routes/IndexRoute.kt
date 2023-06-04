package com.example.imnuricrestine.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.SurfaceTopPadding
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.navigation.goBackCallback
import com.example.imnuricrestine.navigation.updateTopAppBar

@Composable
fun HymnsIndex(indexTitleList: List<MainActivity.IndexTitle>, contentPadding: PaddingValues, navController: NavHostController?) {
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
                            .width(45.dp),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                },
                //tonalElevation = Dp(2.0f),
                modifier = Modifier.clickable {
                    val hymnId = item.index.toInt()-1
                    navController!!.navigate(Route.HymnDetailsRoute.route+"/$hymnId")
                    MainActivity.surfaceTopPaddingState.value = SurfaceTopPadding.SURFACE_TOP_PADDING_HYMN_DETAILS.padding
                    MainActivity.topAppBarZIndexState.value = 1f
                    MainActivity.surfaceZIndexState.value = 2f
                    Navigation.updateTopAppBar("", Icons.Filled.ArrowBack,
                        MainActivity.pinnedScrollBehavior, { goBackCallback.goBack(navController!!) })
                }

            )
            //Divider()

        }
    }
}

@Preview
@Composable
fun HymnsIndexPreview() {
    val list = listOf(
        MainActivity.IndexTitle(index = 7, title = "Ţi-nalţ, Iehova-n veci cântare!"),
        MainActivity.IndexTitle(index = 13, title = "Domnul e bun"),
        MainActivity.IndexTitle(index = 110, title = "O, ce veste minunată!")
    )

    HymnsIndex(indexTitleList = list, contentPadding = PaddingValues(20.dp), navController = null)
}