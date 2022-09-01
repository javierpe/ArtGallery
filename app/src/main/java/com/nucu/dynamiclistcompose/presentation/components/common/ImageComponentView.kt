package com.nucu.dynamiclistcompose.presentation.components.common

import android.util.Size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageComponentView(
    modifier: Modifier = Modifier,
    imageURL: String,
    overrideSize: Size? = null,
    colorFilter: ColorFilter? = null
) {

    var requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()

    overrideSize?.let {
        requestOptions = requestOptions.override(
            it.width,
            it.height
        )
    }

    GlideImage(
        modifier = modifier,
        imageModel = imageURL,
        contentScale = ContentScale.Crop,
        requestOptions = { requestOptions },
        colorFilter = colorFilter
    )
}