package com.nucu.dynamiclistcompose.presentation.components.common

import android.content.Context
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.size.Size

@Composable
fun CardImageItem(
    imageURL: String
) {

    val size = 46.dp
    val context = LocalContext.current

    ImageComponentView(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(5.dp)),
        imageURL = imageURL,
        overrideSize = Size(
            size.toPx(context).toInt(),
            size.toPx(context).toInt()
        )
    )
}

fun Dp.toPx(context: Context): Float = value * Density(context).density