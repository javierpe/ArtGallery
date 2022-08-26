package com.nucu.dynamiclistcompose.presentation.components.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState

@Composable
fun MessageComponentView(
    modifier: Modifier,
    message: String,
    componentIndex: Int,
    showCaseState: ShowCaseState
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.secondary)
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 12.dp
                ),
                content = {
                    TooltipView(text = stringResource(R.string.tooltip_message))
                },
                strategy = ShowCaseStrategy(firstToHappen = true),
                key = RenderType.MESSAGE.value,
                state = showCaseState
            )
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            textAlign = TextAlign.Justify,
            text = message,
            color = MaterialTheme.colors.primary
        )
    }
}

@Composable
@Preview
fun PreviewTobaccoComponentView() {
    MessageComponentView(
        modifier = Modifier,
        message = "Hola a todos",
        0,
        rememberShowCaseState()
    )
}