package com.javi.design.system.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.HeaderLight
import com.javi.design.system.theme.Typography

@Composable
fun TooltipView(
    text: String,
    alignment: Alignment = Alignment.Center
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .align(alignment)
                .background(HeaderLight)
                .padding(16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = Typography.h6,
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun PreviewTooltip() {
    TooltipView(
        "Esto es un tooltip"
    )
}
