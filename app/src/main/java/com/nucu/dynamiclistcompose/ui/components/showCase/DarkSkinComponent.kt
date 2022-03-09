package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.unit.Dp

@Composable
fun DarkSkinComponent(
    layoutCoordinates: LayoutCoordinates,
    radius: Dp
) {
    val targetRect = layoutCoordinates.boundsInRoot()
    val extraSize = 40

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        withTransform({
            translate(
                left = targetRect.left - extraSize / 2,
                top = targetRect.top - extraSize / 2
            )
        }) {
            drawRoundRect(
                color = Color.White,
                size = Size(
                    layoutCoordinates.size.width.toFloat() + extraSize,
                    layoutCoordinates.size.height.toFloat() + extraSize
                ),
                cornerRadius = CornerRadius(
                    radius.roundToPx().toFloat(),
                    radius.roundToPx().toFloat()
                )
            )
        }
    }
}
