package com.nucu.dynamiclistcompose.components.header

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.models.tooltip.TooltipShowStrategy
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.ui.theme.Typography

@Composable
fun HeaderComponentView(
    componentIndex: Int,
    showCaseState: ShowCaseState,
    text: String
) {
    Text(
        text = text,
        style = Typography.h5,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 16.dp
                ),
                content = {
                    Text(text = "Un header!")
                },
                strategy = TooltipShowStrategy(untilUserInteraction = true),
                key = RenderType.HEADER.value,
                state = showCaseState
            )
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderComponentView() {
    val showCaseState = rememberShowCaseState()
    HeaderComponentView(
        0,
        showCaseState,
        "Hola"
    )
}