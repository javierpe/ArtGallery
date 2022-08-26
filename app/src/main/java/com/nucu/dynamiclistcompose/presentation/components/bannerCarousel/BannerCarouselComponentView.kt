package com.nucu.dynamiclistcompose.presentation.components.bannerCarousel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.presentation.components.common.BannerInfoView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.presentation.viewModels.BannerViewModel

const val BANNER_CAROUSEL_IMAGE_TEST_TAG = "banner-carousel-image"
const val BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG = "banner-carousel-image-screen"

@Composable
fun BannerCarouselComponentViewScreen(
    modifier: Modifier,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    viewModel: BannerViewModel = hiltViewModel()
) {
    BannerCarouselComponentView(
        modifier = modifier.testTag(BANNER_CAROUSEL_IMAGE_SCREEN_TEST_TAG),
        images = images,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) {
        viewModel.loadBanner(it)
    }
}

@Composable
fun BannerCarouselComponentView(
    modifier: Modifier,
    images: List<BannerModel>,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onClickAction: (String) -> Unit,
) {
    val context = LocalContext.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        itemsIndexed(items = images) { index, item ->

            val modifierBanner = if (index == 0) {
                Modifier.asShowCaseTarget(
                    index = componentIndex,
                    style = ShowCaseStyle.Default.copy(
                        shapeType = ShapeType.RECTANGLE,
                        cornerRadius = 16.dp,
                        withAnimation = false
                    ),
                    content = {
                        TooltipView(text = stringResource(id = R.string.tooltip_baner_carousel))
                    },
                    strategy = ShowCaseStrategy(firstToHappen = true),
                    key = RenderType.BANNER_CAROUSEL.value,
                    state = showCaseState
                )
            } else Modifier

            Box(
                modifier = Modifier
                    .testTag(BANNER_CAROUSEL_IMAGE_TEST_TAG)
            ) {
                SubcomposeAsyncImage(
                    modifier = modifierBanner
                        .height(300.dp)
                        .width(350.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            onClickAction.invoke(item.imageURL)
                        },
                    model = ImageRequest.Builder(context)
                        .data(item.imageURL)
                        .crossfade(true)
                        .diskCacheKey(item.imageURL)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        is AsyncImagePainter.State.Error -> {

                        }
                        else -> {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                item.bannerInfo?.let {
                    BannerInfoView(
                        modifier = Modifier.height(300.dp).width(350.dp),
                        bannerInfo = it
                    )
                }
            }
        }
    }
}