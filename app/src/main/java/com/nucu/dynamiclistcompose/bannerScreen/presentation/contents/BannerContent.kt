package com.nucu.dynamiclistcompose.bannerScreen.presentation.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView

@Composable
fun BannerContent(
    imageURL: String,
    onBackPressed: () -> Unit
) {

    Box {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageURL)
                .crossfade(true)
                .diskCacheKey(imageURL)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )
                }
                is AsyncImagePainter.State.Error -> {

                }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        DynamicListHeaderComponentView(
            title = "Esto es un banner",
            contextType = ContextType.BANNER_DETAIL,
            onBackPressed = onBackPressed
        )
    }
}

@Composable
@Preview
fun PreviewBannerContent() {
    BannerContent(
        "Hello",
    ) { }
}