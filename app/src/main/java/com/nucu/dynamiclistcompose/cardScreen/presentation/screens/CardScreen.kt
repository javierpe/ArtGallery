package com.nucu.dynamiclistcompose.cardScreen.presentation.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.javier.api.models.Route
import com.nucu.dynamiclistcompose.cardScreen.presentation.contents.CardContent
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    route =  "card_screen/{${Route.CardScreen.CARD_TEXT}}/{${Route.CardScreen.IMAGE_URL}}"
)
@Composable
fun CardScreen(
    title: String,
    images: Array<String>,
    widthSizeClass: WindowWidthSizeClass
) {
    CardContent(
        title = title,
        images = images.toList(),
        widthSizeClass = widthSizeClass
    )
}