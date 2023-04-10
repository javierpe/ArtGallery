package com.javi.dynamic.list.presentation.components.poster

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
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
import com.javi.data.ProductImageModel
import com.javi.design.system.ImageComponentView
import com.javi.design.system.atoms.HorizontalPagerIndicators
import com.javi.design.system.atoms.TitleDecoratedView
import com.javi.design.system.extensions.withBounceClick

const val POSTER_COMPONENT_SCREEN_TAG = "poster_component_screen_tag"

@Composable
fun PosterComponentScreenView(
    model: PosterModel,
    onProductDetail: (String) -> Unit
) {
    PosterComponentViewV2(
        modifier = Modifier.testTag(POSTER_COMPONENT_SCREEN_TAG),
        list = model.elements,
        title = model.title,
    ) {
        onProductDetail(it.imageURL)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PosterComponentViewV2(
    modifier: Modifier = Modifier,
    title: String,
    list: List<PosterModelItem>,
    onClick: (ProductImageModel) -> Unit
) {
    val pagerState = rememberPagerState()

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
            pageCount = list.size,
            state = pagerState
        ) { page ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            ) {
                ImageComponentView(
                    modifier = Modifier
                        .height(450.dp)
                        .align(Alignment.Center)
                        .withBounceClick()
                        .clickable {
                            onClick(list[page].productImage)
                        },
                    imageURL = list[page].productImage.imageURL,
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        HorizontalPagerIndicators(
            modifier = Modifier
                .align(CenterHorizontally)
                .alpha(0.6f),
            pageCount = list.size,
            state = pagerState
        )
    }
}
