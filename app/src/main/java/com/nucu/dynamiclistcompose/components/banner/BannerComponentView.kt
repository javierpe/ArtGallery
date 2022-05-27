package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
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
fun BannerComponentView(
    widthSizeClass: WindowWidthSizeClass,
    componentIndex: Int,
    showCaseState: ShowCaseState
) {

    /* var modifier = Modifier


     var style = Typography.h1

     when (widthSizeClass) {

         WindowWidthSizeClass.Compact -> {
             modifier = modifier
                 .height(120.dp)
                 .fillMaxWidth()
             style = Typography.h6
         }

         WindowWidthSizeClass.Expanded -> {
             modifier = modifier
                 .height(140.dp)
                 .wrapContentWidth()
             style = Typography.h5
         }

         WindowWidthSizeClass.Medium -> {
             modifier = modifier
                 .height(130.dp)
                 .fillMaxWidth()
             style = Typography.h5
         }
     }*/

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
                strategy = TooltipShowStrategy(untilUserInteraction = true),
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
        widthSizeClass = WindowWidthSizeClass.Compact,
        componentIndex = 0,
        showCaseState = state
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewExpandedBannerComponentView() {
    val state = rememberShowCaseState()
    BannerComponentView(
        widthSizeClass = WindowWidthSizeClass.Expanded,
        componentIndex = 0,
        showCaseState = state
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewMediumBannerComponentView() {
    val state = rememberShowCaseState()
    BannerComponentView(
        widthSizeClass = WindowWidthSizeClass.Medium,
        componentIndex = 0,
        showCaseState = state
    )
}