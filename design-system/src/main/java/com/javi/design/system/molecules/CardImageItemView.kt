package com.javi.design.system.molecules

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.size.Size
import com.javi.design.system.atoms.ImageComponentView

@Composable
fun CardImageItem(
    imageURL: String
) {
    ImageComponentView(
        modifier = Modifier
            .size(46.dp)
            .clip(RoundedCornerShape(5.dp)),
        imageURL = imageURL,
        overrideSize = Size(
            width = 80,
            height = 80
        )
    )
}
