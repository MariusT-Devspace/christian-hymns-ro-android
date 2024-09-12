package com.example.imnuricrestine.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun TextWithRoundedRectangle(
    text: String,
    modifier: Modifier,
    fontSize: TextUnit,
    backgroundColor: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color
) {
    // Initialize width as it is not exist
    val textWidthState: MutableState<Dp?> = remember { mutableStateOf(null) }
    val modifierWithCalculatedSize: State<Modifier> =
        // You must provide new Modifier whenever width of Text is changed
        remember(textWidthState.value) {
            // Modifier for parent which draw the Circle
            val mod = modifier
            // Provide new Modifier only when calculation produces new value
            derivedStateOf {
                val currentWidth = textWidthState.value
                if (currentWidth != null) mod.size(currentWidth, ((75f / 100f) * currentWidth)) else mod
            }
        }

    // Do not use Modifier with size(especially width) for Box.
    Box(
        modifier = modifierWithCalculatedSize.value
            .clip(RoundedCornerShape(26.dp))
            .background(backgroundColor),
        // Center your text inside Circle
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        Text(
            text = text,
            color = textColor,
            modifier = Modifier
                // Obtain width of Text after position
                .onGloballyPositioned {
                    textWidthState.value =  with(density) {
                        it.size.width.toDp()
                    }
                }
                // Adjust Circle size
                .padding(
                    start = 12.dp, end = 12.dp
                ),
            fontSize = fontSize
        )
    }
}