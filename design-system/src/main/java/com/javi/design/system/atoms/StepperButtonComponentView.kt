package com.javi.design.system.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.extensions.withBounceClick
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.design.system.theme.HeaderDark
import com.javi.design.system.theme.HeaderLight
import com.javi.design.system.theme.Typography

private const val ANIMATION_DURATION_TIME = 250
private val MOVEMENT_RANGE = -250..250

enum class StepperUIState {
    /**
     * First state.
     */
    IDLE,

    /**
     * Show stepper view,
     */
    ACTIVE,

    /**
     * Shows quantity or plus button only and stepper is hidden.
     */
    STAND_BY
}

@Composable
fun StepperComponentView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    onAdd: () -> Unit,
    onDecrement: () -> Unit,
) {
    val firstPosition = remember {
        mutableStateOf(0f)
    }

    val uiState = rememberSaveable {
        mutableStateOf(StepperUIState.IDLE)
    }

    StepperView(
        modifier = modifier,
        quantity = quantity,
        onAdd = {
            uiState.value = StepperUIState.ACTIVE
            onAdd()
        },
        onDecrement = {
            uiState.value = StepperUIState.ACTIVE
            onDecrement()
        },
        onVerticalRepositioned = { currentPosition, positionInRoot ->
            if (uiState.value == StepperUIState.ACTIVE) {
                if (firstPosition.value == 0f) {
                    firstPosition.value = currentPosition
                } else {
                    val movement = firstPosition.value - currentPosition
                    uiState.value = if (movement == 0f || (movement.toInt() in MOVEMENT_RANGE)) {
                        StepperUIState.ACTIVE
                    } else if (movement.toInt() > positionInRoot) {
                        StepperUIState.IDLE
                    } else {
                        StepperUIState.ACTIVE
                    }
                }
            }
        },
        uiState = uiState.value
    )
}

@Suppress("LongParameterList")
@Composable
fun StepperView(
    modifier: Modifier = Modifier,
    quantity: Int = 0,
    uiState: StepperUIState = StepperUIState.IDLE,
    onAdd: () -> Unit,
    onDecrement: () -> Unit,
    onVerticalRepositioned: (Float, Float) -> Unit
) {
    val animatedWidth = animateDpAsState(
        targetValue = if (uiState == StepperUIState.ACTIVE) 140.dp else 0.dp,
        tween(ANIMATION_DURATION_TIME)
    )

    val animatedStepperVisibility = animateFloatAsState(
        targetValue = if (uiState == StepperUIState.ACTIVE) 1f else 0f,
        tween(ANIMATION_DURATION_TIME)
    )

    val animatedButtonVisibility = animateFloatAsState(
        targetValue = if (uiState == StepperUIState.IDLE || uiState == StepperUIState.STAND_BY) 1f else 0f,
        tween(ANIMATION_DURATION_TIME)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {
        StepperButtonAction(
            modifier = Modifier
                .size(45.dp)
                .alpha(animatedButtonVisibility.value),
            icon = Icons.Filled.Add,
            quantity = quantity
        ) {
            onAdd()
        }

        Row(
            modifier = Modifier
                .withBounceClick()
                .height(45.dp)
                .onGloballyPositioned {
                    onVerticalRepositioned(it.positionInRoot().y, it.positionInWindow().y)
                }
                .width(animatedWidth.value)
                .alpha(animatedStepperVisibility.value)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colors.surface),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StepperButtonAction(icon = Icons.Filled.Clear) {
                onDecrement()
            }

            Text(
                text = quantity.toString(),
                style = Typography.h6.copy(fontWeight = FontWeight.Black),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )

            StepperButtonAction(icon = Icons.Filled.Add) {
                onAdd()
            }
        }
    }
}

@Composable
fun StepperButtonAction(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    quantity: Int = 0,
    onClick: () -> Unit
) {

    val animateBackgroundColor = animateColorAsState(
        targetValue = if (quantity > 0) HeaderLight else Color.White
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(animateBackgroundColor.value)
            .clickable {
                onClick()
            }
    ) {
        if (quantity == 0 && icon != null) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp),
                imageVector = icon,
                contentDescription = "action",
                tint = Color.Black
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp),
                text = quantity.toString(),
                color = HeaderDark,
                style = Typography.h6.copy(fontWeight = FontWeight.Black),
            )
        }
    }
}

@Composable
@Preview
fun PreviewStepperButtonAction() {
    DynamicListComposeTheme {
        StepperButtonAction(icon = Icons.Default.Add) { }
    }
}

@Composable
@Preview
fun PreviewBuyButtonComponentView() {
    DynamicListComposeTheme {
        StepperComponentView(
            quantity = 1,
            onAdd = { },
            onDecrement = { }
        )
    }
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(): MultipleEventsCutter =
    MultipleEventsCutterImpl()

private class MultipleEventsCutterImpl : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}
