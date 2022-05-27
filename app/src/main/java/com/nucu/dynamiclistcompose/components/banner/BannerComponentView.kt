package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.models.tooltip.ShowCaseStrategy
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.ui.theme.Typography

@Composable
fun BannerComponentView(
    componentIndex: Int,
    showCaseState: ShowCaseState
) {

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.primary)
            .clip(RoundedCornerShape(16.dp))
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 16.dp
                ),
                content = {
                    Text(text = "Mensaje")
                },
                strategy = ShowCaseStrategy(onlyUserInteraction = true),
                key = RenderType.BANNER_IMAGE.value,
                state = showCaseState
            )
    ) {
        Text(
            text = "Esto es un banner",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth(),
            style = Typography.h4,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompactBannerComponentView() {
    val state = rememberShowCaseState()
    BannerComponentView(
        componentIndex = 0,
        showCaseState = state
    )
}
