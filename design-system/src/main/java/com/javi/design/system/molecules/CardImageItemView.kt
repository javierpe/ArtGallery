package com.javi.design.system.molecules

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.size.Size
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.extensions.toPx

@Composable
fun CardImageItem(
    imageURL: String
) {
    val context = LocalContext.current
    ImageComponentView(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(5.dp)),
        imageURL = imageURL,
        customSize = Size(
            48.dp.toPx(context).toInt(),
            48.dp.toPx(context).toInt()
        )
    )
}
