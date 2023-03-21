package com.nucu.dynamiclistcompose.data.models

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class DynamicListObject(
    val widthSizeClass: WindowWidthSizeClass,
    val navigator: DestinationsNavigator
)
