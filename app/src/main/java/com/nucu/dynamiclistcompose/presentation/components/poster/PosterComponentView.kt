@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.nucu.dynamiclistcompose.presentation.components.poster

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.nucu.dynamiclistcompose.presentation.components.common.ImageComponentView
import com.nucu.dynamiclistcompose.presentation.components.common.TitleDecoratedView
import kotlin.math.absoluteValue

const val POSTER_COMPONENT_SCREEN_TAG = "poster_component_screen_tag"

@Composable
fun PosterComponentScreenView(
    model: PosterModel,
    isExpandedScreen: Boolean = false
) {
    PosterComponentView(
        modifier = Modifier.testTag(POSTER_COMPONENT_SCREEN_TAG),
        list = model.elements,
        title = model.title,
        isExpandedScreen = isExpandedScreen
    )
}

@Composable
fun PosterComponentView(
    modifier: Modifier = Modifier,
    title: String,
    list: List<PosterModelItem>,
    isExpandedScreen: Boolean = false
) {
    val pagerState = rememberPagerState()

    val height = if (isExpandedScreen) {
        200.dp
    } else 450.dp

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        TitleDecoratedView(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = title
        )

        HorizontalPager(
            modifier = Modifier
                .height(height),
            count = list.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.3.dp,
                            stop = 0.35.dp,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale.toPx()
                            scaleY = scale.toPx()
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.dp,
                            stop = 1.dp,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).toPx()
                    }

                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                ImageComponentView(
                    modifier = Modifier
                        .fillMaxSize()
                        // We add an offset lambda, to apply a light parallax effect
                        .offset {
                            // Calculate the offset for the current page from the
                            // scroll position
                            val pageOffset = this@HorizontalPager.calculateCurrentOffsetForPage(page)
                            // Then use it as a multiplier to apply an offset
                            IntOffset(
                                x = (100.dp * pageOffset).roundToPx(),
                                y = 0
                            )
                        },
                    imageURL = list[page].url,
                    contentScale = ContentScale.Fit
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        )
    }
}