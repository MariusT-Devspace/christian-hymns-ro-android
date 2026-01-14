package com.mtcnextlabs.imnuricrestine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mtcnextlabs.imnuricrestine.ui.HymnsApp
import com.mtcnextlabs.imnuricrestine.ui.theme.ChristianHymnsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChristianHymnsTheme {
                HymnsApp()
            }
        }
    }
}