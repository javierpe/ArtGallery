package com.javi.dynamic.list.presentation.ui.base

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

private const val BG_ANIMATION_DURATION = 250

@Composable
fun DynamicListLayout(
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass,
    contentHeader: @Composable () -> Unit,
    contentBody: @Composable () -> Unit
) {
    val constraints = when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> compactConstraints()
        WindowWidthSizeClass.Medium -> mediumConstraints()
        else -> compactConstraints()
    }

    val animatedVisibilityOfClip = animateFloatAsState(
        targetValue = if (widthSizeClass == WindowWidthSizeClass.Compact) 0f else 1f,
        tween(BG_ANIMATION_DURATION)
    )

    ConstraintLayout(
        modifier = modifier,
        constraintSet = constraints
    ) {
        Box(
            modifier = Modifier
                .layoutId(BG_HEADER_ID)
                .clip(RoundedCornerShape(16.dp))
                .alpha(animatedVisibilityOfClip.value)
                .background(MaterialTheme.colors.primary)
        )

        contentHeader()

        Box(
            modifier = Modifier
                .layoutId(BG_BODY_ID)
                .clip(RoundedCornerShape(16.dp))
                .alpha(animatedVisibilityOfClip.value)
                .background(MaterialTheme.colors.primary)
        )

        contentBody()
    }
}
