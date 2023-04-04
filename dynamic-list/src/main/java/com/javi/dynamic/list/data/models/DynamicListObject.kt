package com.javi.dynamic.list.data.models

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Provide only objects needed to dynamic list components.
 */
data class DynamicListObject(
    val widthSizeClass: WindowWidthSizeClass? = null,
    val destinationsNavigator: DestinationsNavigator? = null
)
