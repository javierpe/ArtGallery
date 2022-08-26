package com.nucu.dynamiclistcompose.cardScreen.presentation.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.cardScreen.presentation.contents.CardContent

@Composable
fun CardScreen(
    title: String,
    images: List<String>,
    widthSizeClass: WindowWidthSizeClass
) {
    CardContent(
        title = title,
        images = images,
        widthSizeClass = widthSizeClass
    )
}