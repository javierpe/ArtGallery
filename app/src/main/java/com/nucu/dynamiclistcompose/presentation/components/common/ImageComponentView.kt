package com.nucu.dynamiclistcompose.presentation.components.common

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
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

    val isLoaded = rememberSaveable {
        mutableStateOf(false)
    }

    val animatedAlpha = animateFloatAsState(
        targetValue = if (isLoaded.value) 1f else 0f,
        spring(stiffness = Spring.StiffnessLow)
    )

    var requestBuilder = ImageRequest.Builder(LocalContext.current)
        .data(imageURL)
        .diskCacheKey(imageURL)
        .crossfade(FADE_DURATION)
        .allowHardware(true)
        .dispatcher(Dispatchers.Main)
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

    AsyncImage(
        modifier = modifier,
        model = requestBuilder.build(),
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter,
        onState = {
            isLoaded.value = it is AsyncImagePainter.State.Success
        },
        alpha = animatedAlpha.value
    )
}