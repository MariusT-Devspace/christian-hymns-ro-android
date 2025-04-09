package com.example.imnuricrestine.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.imnuricrestine.R

const val DATABASE_NAME = "Hymnsdb"
const val CHORUS_TAG = "Ref"

enum class TopAppBarTitle(val title: String) {
    INDEX("920 Imnuri Crestine"),
    FAVORITES("Favorite")
}

object ICONS {
    val topAppBarLogo: @Composable () -> Unit = {
                Image(
                painter = painterResource(R.drawable.imnuri_crestine_icon),
                contentDescription = "App logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = 1.4f
                        scaleY = 1.4f
                        translationX = 0f
                        translationY = 0f
                        clip = true
                        shape = CircleShape
                    }
            )
    }

    val backIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back to previous screen"
        )
    }
}

