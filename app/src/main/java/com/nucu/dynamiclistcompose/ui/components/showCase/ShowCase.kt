package com.nucu.dynamiclistcompose.ui.components.showCase

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShowCaseTargets
import com.nucu.dynamiclistcompose.viewModels.ShowCaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

private const val DEFAULT_EXTRA_DURATION = 100

@Composable
fun ShowCase(
    state: ShowCaseState,
    viewModel: ShowCaseViewModel = hiltViewModel()
) {
    val current by state.current.collectAsState()

    current?.let {
        StartShowCase(target = it) {
            viewModel.setShowed(it.key)
            state.send(null)
            it.onNext.invoke()
        }
    }
}

@Composable
fun StartShowCase(
    target: ShowCaseTargets,
    onShowCaseCompleted: () -> Unit
) {
    val strategy = target.tooltipShowStrategy

    if (strategy.firstToHappen == true) {
        ShowCaseFirstToHappen(
            target = target,
            onShowCaseCompleted = onShowCaseCompleted
        )
    } else {
        if (strategy.onlyUserInteraction == true) {
            ShowCaseUntilUserInteraction(
                target = target,
                onShowCaseCompleted = onShowCaseCompleted
            )
        } else {
            ShowCaseUntilExpirationTime(
                target = target,
                onShowCaseCompleted = onShowCaseCompleted
            )
        }
    }
}

@Composable
fun ShowCaseUntilUserInteraction(
    target: ShowCaseTargets,
    onShowCaseCompleted: () -> Unit
) {
    // Show tooltip
    TargetContent(target) {
        // Callback to finish
        onShowCaseCompleted()
    }
}

@Composable
fun ShowCaseFirstToHappen(
    target: ShowCaseTargets,
    onShowCaseCompleted: () -> Unit
) {
    val scope = rememberCoroutineScope()

    // Show tooltip
    TargetContent(target) {
        // Callback to finish
        onShowCaseCompleted()
    }

    LaunchedEffect(target) {
        scope.launch {
            // Delay time to finish
            delay((target.tooltipShowStrategy.expirationTime + DEFAULT_EXTRA_DURATION).toLong())
            onShowCaseCompleted()
        }
    }
}

@Composable
fun ShowCaseUntilExpirationTime(
    target: ShowCaseTargets,
    onShowCaseCompleted: () -> Unit
) {

    val scope = rememberCoroutineScope()

    // Show tooltip
    TargetContent(target) { /* no-op */ }

    LaunchedEffect(target) {
        scope.launch {
            // Delay time to finish
            delay((target.tooltipShowStrategy.expirationTime + DEFAULT_EXTRA_DURATION).toLong())

            // Hide tooltip
            onShowCaseCompleted()
        }
    }
}

@Composable
fun TargetContent(
    target: ShowCaseTargets,
    onShowcaseCompleted: () -> Unit
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val targetCords = target.coordinates
    val gutterArea = 88.dp
    val targetRect = targetCords.boundsInRoot()

    val yOffset = with(LocalDensity.current) {
        targetCords.positionInRoot().y.toDp()
    }

    val isTargetInGutter = gutterArea > yOffset || yOffset > screenHeight.dp.minus(gutterArea)

    val maxDimension =
        max(targetCords.size.width.absoluteValue, targetCords.size.height.absoluteValue)
    val targetRadius = maxDimension / 2f + 40f

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(2000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )

    var outerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    var outerRadius by remember {
        mutableStateOf(0f)
    }

    val outerAnimatable = remember { Animatable(0.6f) }

    val animatables = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    LaunchedEffect(target) {
        outerAnimatable.snapTo(0.6f)

        outerAnimatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing,
            ),
        )
    }

    animatables.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    val dys = animatables.map { it.value }

    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(target) {
                    detectTapGestures {
                        onShowcaseCompleted()
                    }
                }
                .graphicsLayer(alpha = 0.99f)
        ) {

            if (target.style.shapeType == ShapeType.RECTANGLE) {
                drawRect(color = Color.Black, alpha = 0.9f)
            } else {
                drawCircle(
                    color = target.style.backgroundColor,
                    center = outerOffset,
                    radius = outerRadius * outerAnimatable.value,
                    alpha = target.style.backgroundAlpha
                )
            }

            if (target.style.withAnimation) {
                dys.forEach { dy ->
                    drawCircle(
                        color = target.style.targetCircleColor,
                        radius = maxDimension * dy * 2f,
                        center = targetRect.center,
                        alpha = 1 - dy
                    )
                }
            }

            if (target.style.shapeType == ShapeType.RECTANGLE) {
                withTransform({
                    translate(
                        left = targetRect.center.x - (target.coordinates.size.width / 2),
                        top = targetRect.center.y - (target.coordinates.size.height / 2)
                    )
                }) {
                    drawRoundRect(
                        color = Color.White,
                        size = Size(
                            target.coordinates.size.width.toFloat(),
                            target.coordinates.size.height.toFloat()
                        ),
                        cornerRadius = CornerRadius(
                            target.style.cornerRadius.toPx(),
                            target.style.cornerRadius.toPx()
                        ),
                        blendMode = BlendMode.Xor
                    )
                }
            } else {
                drawCircle(
                    color = target.style.targetCircleColor,
                    radius = targetRadius,
                    center = targetRect.center,
                    blendMode = BlendMode.Xor
                )
            }
        }

        ShowCaseContent(target, targetRect, targetRadius) { coordinates ->
            val contentRect = coordinates.boundsInParent()
            val outerRect = getOuterRect(contentRect, targetRect, isTargetInGutter)
            val possibleOffset = getOuterCircleCenter(targetRect, contentRect, targetRadius)

            outerOffset = if (isTargetInGutter) {
                outerRect.center
            } else {
                possibleOffset
            }

            outerRadius = getOuterRadius(outerRect) + targetRadius
        }
    }
}

@Composable
fun ShowCaseContent(
    currentTarget: ShowCaseTargets,
    boundsInParent: Rect,
    targetRadius: Float,
    updateContentCoordinates: (LayoutCoordinates) -> Unit
) {

    var contentOffsetY by remember {
        mutableStateOf(0f)
    }

    Box(
        content = currentTarget.content,
        modifier = Modifier
            .offset(y = with(LocalDensity.current) {
                contentOffsetY.toDp()
            })
            .onGloballyPositioned {
                updateContentCoordinates(it)
                val contentHeight = it.size.height

                val possibleTop =
                    boundsInParent.center.y - targetRadius - contentHeight

                contentOffsetY = if (possibleTop > 0) {
                    possibleTop
                } else {
                    boundsInParent.center.y + (currentTarget.coordinates.size.height / 2)
                }
            }
            .padding(start = 16.dp, end = 16.dp, top = 10.dp)
    )

}

fun getOuterCircleCenter(
    targetRect: Rect,
    contentRect: Rect,
    targetRadius: Float
): Offset {
    val outerCenterX: Float
    val outerCenterY: Float

    val contentHeight = contentRect.height
    val onTop =
        targetRect.center.y - targetRadius - contentHeight > 0

    val left = min(
        contentRect.left,
        targetRect.left - targetRadius
    )
    val right = max(
        contentRect.right,
        targetRect.right + targetRadius
    )

    val centerY =
        if (onTop) targetRect.center.y - targetRadius - contentHeight
        else targetRect.center.y + targetRadius + contentHeight

    outerCenterY = centerY
    outerCenterX = (left + right) / 2

    return Offset(outerCenterX, outerCenterY)
}

fun getOuterRect(contentRect: Rect, targetRect: Rect, isTargetInGutter: Boolean): Rect {

    val topLeftX = min(contentRect.topLeft.x, targetRect.topLeft.x)
    val topLeftY = min(contentRect.topLeft.y, targetRect.topLeft.y)
    val bottomRightX = max(contentRect.bottomRight.x, targetRect.bottomRight.x)
    val bottomRightY = max(contentRect.bottomRight.y, targetRect.bottomRight.y)

    return Rect(topLeftX, topLeftY, bottomRightX, bottomRightY)
}

fun getOuterRadius(outerRect: Rect): Float {
    val d = sqrt(
        outerRect.height.toDouble().pow(2.0)
                + outerRect.width.toDouble().pow(2.0)
    ).toFloat()

    return (d / 2f)
}