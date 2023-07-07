package com.javi.dynamic.list.presentation.components.poster

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.design.system.atoms.HorizontalPagerIndicators
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.atoms.TitleDecoratedView
import com.javi.design.system.extensions.withBounceClick

const val POSTER_COMPONENT_SCREEN_TAG = "poster_component_screen_tag"

@Composable
fun PosterComponentScreenView(
    modifier: Modifier = Modifier,
    model: PosterModel,
    onProductDetail: (String) -> Unit
) {
    PosterComponentViewV2(
        modifier = modifier.testTag(POSTER_COMPONENT_SCREEN_TAG),
        list = model.elements,
        title = model.title,
    ) {
        onProductDetail(it)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PosterComponentViewV2(
    modifier: Modifier = Modifier,
    title: String,
    list: List<PosterModelItem>,
    onClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        list.size
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TitleDecoratedView(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 25.dp),
            text = title
        )

        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    ImageComponentView(
                        modifier = Modifier
                            .height(250.dp)
                            .align(Alignment.Center)
                            .withBounceClick()
                            .clickable {
                                onClick(list[it].productImage.imageURL)
                            },
                        imageURL = list[it].productImage.imageURL,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        )

        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        HorizontalPagerIndicators(
            modifier = Modifier
                .align(CenterHorizontally)
                .alpha(0.6f),
            pageCount = list.size,
            state = pagerState
        )
    }
}
