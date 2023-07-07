package com.javi.design.system.atoms

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.design.system.theme.DynamicListComposeTheme

private const val ANIMATION_DURATION = 550

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicators(
    modifier: Modifier = Modifier,
    pageCount: Int,
    state: PagerState
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->

            val animateColor = animateColorAsState(
                targetValue = if (state.currentPage == iteration) {
                    // Selected
                    MaterialTheme.colors.secondary
                } else {
                    // Unselected
                    MaterialTheme.colors.onSecondary
                },
                tween(ANIMATION_DURATION)
            )

            Box(
                modifier = Modifier
                    .size(15.dp)
                    .padding(3.dp)
                    .clip(CircleShape)
                    .background(animateColor.value)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(
    name = "HorizontalPagerIndicators | Night Mode On",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF383838
)
fun PreviewNightModeHorizontalPagerIndicators() {
    DynamicListComposeTheme {
        HorizontalPagerIndicators(
            pageCount = 5,
            state = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 1f
            ) {
                5
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(
    name = "HorizontalPagerIndicators | Night Mode Off",
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
fun PreviewNoNightModeHorizontalPagerIndicators() {
    DynamicListComposeTheme {
        HorizontalPagerIndicators(
            pageCount = 5,
            state = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 1f
            ) {
                5
            }
        )
    }
}
