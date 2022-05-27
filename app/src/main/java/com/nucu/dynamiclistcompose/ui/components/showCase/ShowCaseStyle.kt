package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType

class ShowCaseStyle(
    val backgroundColor: Color = Color.Black,
    val backgroundAlpha: Float = DEFAULT_BACKGROUND_RADIUS,
    val targetCircleColor: Color = Color.White,
    val shapeType: ShapeType = ShapeType.CIRCLE,
    val withAnimation: Boolean = true,
    val cornerRadius: Dp = 10.dp
) {

    fun copy(
        backgroundColor: Color = this.backgroundColor,
        backgroundAlpha: Float = this.backgroundAlpha,
        targetCircleColor: Color = this.targetCircleColor,
        shapeType: ShapeType = this.shapeType,
        withAnimation: Boolean = this.withAnimation,
        cornerRadius: Dp = this.cornerRadius
    ): ShowCaseStyle {

        return ShowCaseStyle(
            backgroundColor = backgroundColor,
            backgroundAlpha = backgroundAlpha,
            targetCircleColor = targetCircleColor,
            shapeType = shapeType,
            cornerRadius = cornerRadius,
            withAnimation = withAnimation
        )
    }

    companion object {
        private const val DEFAULT_BACKGROUND_RADIUS = 0.9f

        /**
         * Constant for default text style.
         */
        @Stable
        val Default = ShowCaseStyle()
    }
}