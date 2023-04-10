package com.javi.design.system.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.Typography

@Composable
fun BannerInfoView(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .wrapContentHeight()
                .padding(bottom = 16.dp, start = 16.dp)
        ) {
            Text(
                text = title,
                style = Typography.h5,
                color = Color.White
            )
            Text(
                text = description,
                style = Typography.body2,
                color = Color.White
            )
        }
    }
}
