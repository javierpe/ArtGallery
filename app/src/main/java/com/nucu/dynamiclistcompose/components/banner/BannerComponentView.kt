package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.ui.theme.Typography

@Composable
fun BannerComponentView(
    widthSizeClass: WindowWidthSizeClass,
    coordinates: ((LayoutCoordinates) -> Unit)? = null
) {

    var modifier = Modifier
        .padding(start = 16.dp, end = 16.dp)
        .clip(RoundedCornerShape(16.dp))

    var style = Typography.h1

    when (widthSizeClass) {

        WindowWidthSizeClass.Compact -> {
            modifier = modifier.height(120.dp).fillMaxWidth()
            style = Typography.h6
        }

        WindowWidthSizeClass.Expanded -> {
            modifier = modifier.height(140.dp).wrapContentWidth()
            style = Typography.h5
        }

        WindowWidthSizeClass.Medium -> {
            modifier = modifier.height(130.dp).fillMaxWidth()
            style = Typography.h5
        }
    }


    Box(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .clip(RoundedCornerShape(16.dp))
            .onGloballyPositioned { coordinates?.invoke(it) }
    ) {
        Text(
            text = "Esto es un banner",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center).padding(16.dp).fillMaxWidth(),
            style = style,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompactBannerComponentView() {
    BannerComponentView(
        widthSizeClass = WindowWidthSizeClass.Compact,
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewExpandedBannerComponentView() {
    BannerComponentView(
        widthSizeClass = WindowWidthSizeClass.Expanded,
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewMediumBannerComponentView() {
    BannerComponentView(
        widthSizeClass = WindowWidthSizeClass.Medium,
    )
}