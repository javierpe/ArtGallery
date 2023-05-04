package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.javi.design.system.theme.HeaderDark

@Composable
fun DynamicListLayout(
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass,
    contentHeader: @Composable (() -> Unit)? = null,
    contentBody: @Composable (() -> Unit)? = null
) {
    val screenClass = remember {
        mutableStateOf(
            widthSizeClass
        )
    }

    val constraints = when (screenClass.value) {
        WindowWidthSizeClass.Compact -> compactConstraints()
        WindowWidthSizeClass.Medium -> mediumConstraints(
            haveHeader = contentHeader != null,
            haveBody = contentBody != null
        )
        else -> compactConstraints()
    }

    ConstraintLayout(
        modifier = modifier,
        constraintSet = constraints
    ) {
        contentHeader?.let { header ->
            if (
                screenClass.value == WindowWidthSizeClass.Medium ||
                screenClass.value == WindowWidthSizeClass.Expanded
            ) {
                Box(
                    modifier = Modifier
                        .layoutId(HEADER_ID)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSystemInDarkTheme()) {
                                HeaderDark
                            } else {
                                MaterialTheme.colors.primary
                            }
                        )
                ) {
                    header()
                }
            } else {
                Box(
                    modifier = Modifier
                        .layoutId(HEADER_ID)
                ) {
                    header()
                }
            }
        }

        contentBody?.let { body ->
            if (
                screenClass.value == WindowWidthSizeClass.Medium || screenClass.value == WindowWidthSizeClass.Expanded
            ) {
                Box(
                    modifier = Modifier
                        .layoutId(BODY_ID)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSystemInDarkTheme()) {
                                HeaderDark
                            } else {
                                MaterialTheme.colors.primary
                            }
                        )
                ) {
                    body()
                }
            } else {
                Box(
                    modifier = Modifier
                        .layoutId(BODY_ID)
                ) {
                    body()
                }
            }
        }
    }
}
