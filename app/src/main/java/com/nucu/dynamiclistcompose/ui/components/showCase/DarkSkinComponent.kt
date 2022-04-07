package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.nucu.dynamiclistcompose.ui.theme.Purple200
import com.nucu.dynamiclistcompose.ui.theme.Purple700

@Composable
fun DarkSkinComponent(
    layoutCoordinates: LayoutCoordinates,
    radius: Dp,
    message: String
) {
    if (layoutCoordinates.isAttached) {
        val targetRect = layoutCoordinates.boundsInParent()
        val extraSize = 10

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.99f)
        ) {

            val (titleRef, darkScreenRef) = createRefs()

            Box(
                modifier = Modifier
                    .size(
                        LocalDensity.current.run {
                            layoutCoordinates.size.width
                                .toFloat()
                                .toDp()
                        },
                        LocalDensity.current.run {
                            layoutCoordinates.size.height
                                .toFloat()
                                .toDp()
                        }
                    )
                    .drawBehind {
                        withTransform({
                            translate(
                                left = targetRect.left - extraSize / 2,
                                top = targetRect.top - extraSize / 2
                            )
                        }) {
                            drawRoundRect(
                                color = Color.White,
                                size = Size(
                                    layoutCoordinates.size.width.toFloat(),
                                    layoutCoordinates.size.height.toFloat()
                                ),
                                cornerRadius = CornerRadius(
                                    radius
                                        .roundToPx()
                                        .toFloat(),
                                    radius
                                        .roundToPx()
                                        .toFloat()
                                )
                            )
                        }
                    }
                    .constrainAs(darkScreenRef) { }
            )

            Box {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    drawRect(SolidColor(Color.Black.copy(alpha = 0.7f)), blendMode = BlendMode.SrcOut)
                }
            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(titleRef) {
                        top.linkTo(darkScreenRef.bottom, 30.dp)
                        start.linkTo(parent.start, 24.dp)
                        end.linkTo(parent.end, 24.dp)
                    }
            ) {
                TooltipBubble(text = message)
            }
        }
    }
}

@Composable
fun TooltipBubble(
    text: String
){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(
                Brush.sweepGradient(
                    listOf(
                        Purple200,
                        Purple700,
                        Purple700
                    ),
                    center = Offset(0.0f, 0.0f)
                )
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier.padding(5.dp)
        )
    }
}