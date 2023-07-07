package com.javi.design.system.atoms

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.Dispatchers

@Suppress("LongParameterList")
@Composable
fun ImageComponentView(
    modifier: Modifier = Modifier,
    imageURL: String,
    colorFilter: ColorFilter? = null,
    customSize: Size? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .diskCacheKey(imageURL)
            .diskCachePolicy(CachePolicy.ENABLED)
            .dispatcher(Dispatchers.IO)
            .allowHardware(true)
            .allowConversionToBitmap(true)
            .lifecycle(LocalLifecycleOwner.current)
            .apply {
                customSize?.let {
                    size(
                        Size(
                            width = customSize.width,
                            height = customSize.height
                        )
                    )
                } ?: Size.ORIGINAL
            }
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = contentScale,
        colorFilter = colorFilter,
        filterQuality = FilterQuality.None
    )
}