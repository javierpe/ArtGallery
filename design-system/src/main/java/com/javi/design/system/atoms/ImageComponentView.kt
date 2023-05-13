package com.javi.design.system.atoms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers

const val FADE_DURATION = 300

@Suppress("LongParameterList")
@Composable
fun ImageComponentView(
    modifier: Modifier = Modifier,
    imageURL: String,
    overrideSize: Size? = null,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
) {

    val context = LocalContext.current
    val request = remember {
        derivedStateOf {
            ImageRequest.Builder(context)
                .data(imageURL)
                .diskCacheKey(imageURL)
                .crossfade(FADE_DURATION)
                .allowHardware(true)
                .dispatcher(Dispatchers.IO)
                .transformationDispatcher(Dispatchers.Default)
                .size(overrideSize ?: Size.ORIGINAL)
                .fetcherDispatcher(Dispatchers.IO)
                .build()
        }
    }

    ImageComponent(
        modifier = modifier,
        imageRequest = request.value,
        contentScale = contentScale,
        colorFilter = colorFilter,
        onState = onState
    )
}

@Composable
fun ImageComponent(
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Crop,
    imageRequest: ImageRequest
) {
    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter,
        onState = onState
    )
}
