package com.example.imnuricrestine.utils

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.imnuricrestine.R

const val DATABASE_NAME = "Hymnsdb"
const val CHORUS_TAG = "Ref"
object ICONS {
    val topAppBarLogo: @Composable () -> Unit = {
        Image(
            painter = painterResource(R.drawable.logo_zoomed_in),
            contentDescription = "App logo"
        )
    }

    val backIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back to previous screen"
        )
    }
}

