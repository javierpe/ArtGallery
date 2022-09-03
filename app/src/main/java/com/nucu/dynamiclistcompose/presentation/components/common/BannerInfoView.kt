package com.nucu.dynamiclistcompose.presentation.components.common

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerInfo
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography

@Composable
fun BannerInfoView(
    modifier: Modifier = Modifier,
    bannerInfo: BannerInfo
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.secondary.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .wrapContentHeight()
                .padding(bottom = 16.dp, start = 16.dp)
        ) {
            Text(
                text = bannerInfo.title,
                style = Typography.h5,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = bannerInfo.description,
                style = Typography.body2,
                color = MaterialTheme.colors.primary
            )
        }
    }
}