package com.nucu.dynamiclistcompose.presentation.components.banner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.nucu.dynamiclistcompose.presentation.components.common.BannerInfoView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.viewModels.BannerViewModel

const val BANNER_IMAGE_TEST_TAG = "banner-image"
const val BANNER_IMAGE_SCREEN_TEST_TAG = "banner-image-screen"

@Composable
fun BannerComponentViewScreen(
    modifier: Modifier,
    model: BannerModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    viewModel: BannerViewModel = hiltViewModel()
) {
    BannerComponentView(
        modifier = modifier.testTag(BANNER_IMAGE_SCREEN_TEST_TAG),
        model = model,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) {
        viewModel.loadBanner(it)
    }
}

@Composable
fun BannerComponentView(
    modifier: Modifier,
    model: BannerModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onClickAction: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .testTag(BANNER_IMAGE_TEST_TAG)
            .padding(start = 16.dp, end = 16.dp)
            .clickable {
                onClickAction.invoke(model.imageURL)
            }
    ) {
        SubcomposeAsyncImage(
            modifier = modifier
                .height(150.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .asShowCaseTarget(
                    index = componentIndex,
                    style = ShowCaseStyle.Default.copy(
                        shapeType = ShapeType.RECTANGLE,
                        cornerRadius = 16.dp,
                        withAnimation = false
                    ),
                    content = {
                        TooltipView(text = stringResource(R.string.tooltip_banner))
                    },
                    strategy = ShowCaseStrategy(firstToHappen = true),
                    key = RenderType.BANNER.value,
                    state = showCaseState
                ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(model.imageURL)
                .crossfade(true)
                .diskCacheKey(model.imageURL)
                .build(),
            contentDescription = componentIndex.toString(),
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp)
                    )
                }
                is AsyncImagePainter.State.Error -> {  }
                else -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        model.bannerInfo?.let {
            BannerInfoView(
                modifier = Modifier.height(150.dp),
                bannerInfo = it
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCompactBannerComponentView() {
    BannerComponentView(
        modifier = Modifier,
        model = BannerModel(""),
        componentIndex = 0,
        showCaseState = rememberShowCaseState()
    ) { }
}
