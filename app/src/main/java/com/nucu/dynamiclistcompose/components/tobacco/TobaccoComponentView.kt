package com.nucu.dynamiclistcompose.components.tobacco

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.nucu.dynamiclistcompose.models.tooltip.ShowCaseStrategy
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState

@Composable
fun TobaccoComponentView(
    componentIndex: Int,
    showCaseState: ShowCaseState
) {

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.primaryVariant)
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 12.dp
                ),
                content = {
                    Text(text = "Mensaje del sistema")
                },
                strategy = ShowCaseStrategy(onlyUserInteraction = true),
                key = RenderType.TOBACCO_PREFERENCE.value,
                state = showCaseState
            )
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            textAlign = TextAlign.Justify,
            text = stringResource(R.string.label_lorem_ipsum),
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
@Preview
fun PreviewTobaccoComponentView() {
    val showCaseState = rememberShowCaseState()
    TobaccoComponentView(
        0,
        showCaseState
    )
}