package com.javi.design.system.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationBarItem(
    val name: String = "",
    val icon: ImageVector? = null,
    val key: String = "",
    val onClick: (() -> Unit)? = null
)