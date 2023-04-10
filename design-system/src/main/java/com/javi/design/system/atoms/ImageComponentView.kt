package com.javi.design.system

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers

const val FADE_DURATION = 800

@Composable
fun ImageComponentView(
    modifier: Modifier = Modifier,
    imageURL: String,
    overrideSize: Size? = null,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    var requestBuilder = ImageRequest.Builder(LocalContext.current)
        .data(imageURL)
        .diskCacheKey(imageURL)
        .crossfade(FADE_DURATION)
        .allowHardware(true)
        .dispatcher(Dispatchers.IO)
        .transformationDispatcher(Dispatchers.Default)
        .fetcherDispatcher(Dispatchers.IO)

    overrideSize?.let {
        requestBuilder = requestBuilder.size(
            Size(
                it.width,
                it.height
            )
        )
    }

    ImageComponent(
        modifier = modifier,
        imageRequest = requestBuilder.build(),
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    imageRequest: ImageRequest
) {
    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}
