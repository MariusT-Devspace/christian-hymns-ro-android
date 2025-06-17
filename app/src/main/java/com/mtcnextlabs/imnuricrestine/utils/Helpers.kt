package com.mtcnextlabs.imnuricrestine.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun <T> onFirstNonNull(value: T?, onReady: (T) -> Unit) {
    var hasTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(value) {
        if (value != null && !hasTriggered) {
            hasTriggered = true
            onReady(value)
        }
    }
}