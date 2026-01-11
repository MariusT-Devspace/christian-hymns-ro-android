package com.mtcnextlabs.imnuricrestine.ui.screens.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imnuricrestine.R

@Composable
fun EmptyFavorites() {
    Box(
        modifier = Modifier.padding(vertical = 80.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                stringResource(R.string.favorites_empty_heading),
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 28.sp,
                lineHeight = 32.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                stringResource(R.string.favorites_empty_content),
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Card(
                modifier = Modifier.padding(top = 46.dp),
                colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                val favoritesIconId = "favoritesIconId"
                val saveToFavoritesInstructionsText = buildAnnotatedString {
                    append("Apasă pe ")
                    appendInlineContent(favoritesIconId, "[icon]")
                    append(" pentru a adăuga un imn la favorite.")
                }

                val inlineContent = mapOf(
                    Pair(
                        favoritesIconId,
                        InlineTextContent(
                            Placeholder(
                                width = 20.sp,
                                height = 20.sp,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                            )
                        ) {
                            Icon(Icons.Filled.FavoriteBorder, "", modifier = Modifier.fillMaxSize())
                        }
                    )
                )

                Text(
                    saveToFavoritesInstructionsText,
                    inlineContent = inlineContent,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}