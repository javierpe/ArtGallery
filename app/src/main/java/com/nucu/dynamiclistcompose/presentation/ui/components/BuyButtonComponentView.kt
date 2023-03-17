package com.nucu.dynamiclistcompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography

@Composable
fun BuyButtonComponentView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Black)
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Add to cart",
            style = Typography.h6,
            color = Color.White
        )
    }
}

@Composable
@Preview
fun PreviewBuyButtonComponentView() {
    DynamicListComposeTheme {
        BuyButtonComponentView { }
    }
}