package com.example.imnuricrestine.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.imnuricrestine.MainActivity
import com.example.imnuricrestine.R
import com.example.imnuricrestine.navigation.Route
import com.example.imnuricrestine.navigation.navigationActions
import com.example.imnuricrestine.state.MainViewModel
import com.example.imnuricrestine.state.TopAppBar
import com.example.imnuricrestine.state.TopAppBarTitle

@Composable
fun HymnsIndex(
    hymnsIndexTitle: List<MainActivity.IndexTitle>,
    contentPadding: PaddingValues,
    navController: NavHostController?,
    mainViewModel: MainViewModel?
) {
    val state = remember {
        mutableStateOf(hymnsIndexTitle)
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

                },
                supportingContent = {
                    Text(
                        item.title,
                        fontSize = 18.sp
                    )
                },
                leadingContent = {
                    Text(
                        item.index,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = MaterialTheme.shapes.extraLarge
                            )
                            .width(53.dp)
                            .padding(horizontal = 5.dp, vertical = 10.dp),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )

                },
                trailingContent = {
                  IconButton(
                    onClick = {

                    }
                  ) {
                      Icon(Icons.Outlined.FavoriteBorder, contentDescription = stringResource(R.string.add_to_favorites_description))
                  }
                },
                modifier = Modifier.clickable {
                    val hymnId = item.index.toInt()-1
                    navController!!.navigate(Route.HymnDetails.route+"/$hymnId")
                    mainViewModel!!.updateTopAppBar(
                        TopAppBar.SMALLTOPAPPBAR, TopAppBarTitle.TITLEHYMNDETAILS.title,
                        Icons.Filled.ArrowBack, { navigationActions.onGoBack() }
                    )
                },
            )
            //Divider()

        }
    }
}

@Preview
@Composable
fun HymnsIndexPreview() {
    val list = listOf(
        MainActivity.IndexTitle(index = "7", title = "Ţi-nalţ, Iehova-n veci cântare!"),
        MainActivity.IndexTitle(index = "13", title = "Domnul e bun"),
        MainActivity.IndexTitle(index = "110", title = "O, ce veste minunată!"),
        MainActivity.IndexTitle(index = "159A", title = "La mari biruinţe ne cheamă Scriptura")
    )

    HymnsIndex(list, PaddingValues(20.dp),  null,  null)
}