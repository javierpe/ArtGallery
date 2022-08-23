package com.nucu.dynamiclistcompose.presentation.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography

@Composable
fun TextComponentView(
    modifier: Modifier,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    text: String
) {
    Text(
        text = text,
        style = Typography.h6,
        color = MaterialTheme.colors.secondary,
        modifier = modifier
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
                strategy = ShowCaseStrategy(onlyUserInteraction = true),
                key = RenderType.TEXT.value,
                state = showCaseState
            )
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewHeaderComponentView() {
    val showCaseState = rememberShowCaseState()
    TextComponentView(
        modifier = Modifier,
        0,
        showCaseState,
        "Hola"
    )
}