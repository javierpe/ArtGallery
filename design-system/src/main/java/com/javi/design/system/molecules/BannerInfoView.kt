package com.javi.design.system.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.Typography

@Composable
fun BannerInfoView(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {

    val mainColor = MaterialTheme.colors.secondary

    val color = remember {
        derivedStateOf {
            mainColor.copy(alpha = 0.35f)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color.value)
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
                color = MaterialTheme.colors.primary
            )
            Text(
                text = description,
                style = Typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}