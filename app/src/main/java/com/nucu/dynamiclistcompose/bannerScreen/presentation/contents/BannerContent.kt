package com.nucu.dynamiclistcompose.bannerScreen.presentation.contents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.presentation.components.common.ImageComponentView
import com.nucu.dynamiclistcompose.presentation.ui.components.headers.DynamicListHeaderComponentView

@Composable
fun BannerContent(
    imageURL: String
) {
    Box {

        ImageComponentView(
            modifier = Modifier.fillMaxSize(),
            imageURL = imageURL
        )

        DynamicListHeaderComponentView(
            title = "Esto es un banner",
            contextType = ContextType.BANNER_DETAIL
        )
    }
}

@Composable
@Preview
fun PreviewBannerContent() {
    BannerContent(
        "Hello",
    )
}