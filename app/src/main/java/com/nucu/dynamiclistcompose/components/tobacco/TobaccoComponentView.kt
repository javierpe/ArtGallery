package com.nucu.dynamiclistcompose.components.tobacco

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.R

@Composable
fun TobaccoComponentView(
    isEnabled: Boolean,
    coordinates: ((LayoutCoordinates) -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .onGloballyPositioned { coordinates?.invoke(it)  }
    ) {
        Text(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(10.dp),
            textAlign = TextAlign.Justify,
            text = stringResource(R.string.label_lorem_ipsum)
        )
    }
}

@Composable
@Preview
fun PreviewTobaccoComponentView() {
    TobaccoComponentView(false)
}