package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BannerComponentView() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp).height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Magenta)
    )
}

@Composable
@Preview
fun PreviewBannerComponentView() {
    BannerComponentView()
}