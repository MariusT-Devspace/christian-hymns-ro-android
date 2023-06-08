package com.example.imnuricrestine.components

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.example.imnuricrestine.state.TopAppBar

@Composable
fun MyTopAppBar(
    topAppBar : TopAppBar,
    title : @Composable () -> Unit,
    navigationIcon :  @Composable () -> Unit,
    actions : @Composable() (RowScope.() -> Unit),
    scrollBehavior : TopAppBarScrollBehavior?
){
    Log.d("TOPAPPBARCOMPOSABLE", "TopAppBar composable is called")
    when(topAppBar){
        TopAppBar.LARGETOPAPPBAR -> {
            LargeTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions,
                scrollBehavior = scrollBehavior
            )
        }
        TopAppBar.SMALLTOPAPPBAR -> {
            TopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions
            )
        }
    }

}