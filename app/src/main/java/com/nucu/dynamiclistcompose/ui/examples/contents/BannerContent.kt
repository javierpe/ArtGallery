package com.nucu.dynamiclistcompose.ui.examples.contents

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nucu.dynamiclistcompose.models.ContextType
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView
import com.nucu.dynamiclistcompose.ui.theme.Typography

@Composable
fun BannerContent(
    text: String,
    index: String,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            DynamicListHeaderComponentView(
                title = text,
                contextType = ContextType.SCREEN_WITH_IMAGE,
                onBackPressed = onBackPressed
            )
        }
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = index,
            style = Typography.h1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun PreviewBannerContent() {
    BannerContent(
        "Hello",
        "0"
    ) { }
}