package com.nucu.dynamiclistcompose.components.banner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.data.models.tooltip.ShowCaseStrategy
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.ui.components.showCase.models.ShapeType
import com.nucu.dynamiclistcompose.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.viewModels.BannerViewModel

@Composable
fun BannerComponentView(
    imageURL: String,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    viewModel: BannerViewModel = hiltViewModel()
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(150.dp)
            .fillMaxSize()
            .background(Color.White)
            .clip(RoundedCornerShape(16.dp))
            .asShowCaseTarget(
                index = componentIndex,
                style = ShowCaseStyle.Default.copy(
                    shapeType = ShapeType.RECTANGLE,
                    cornerRadius = 16.dp,
                    withAnimation = false
                ),
                content = {
                    TooltipView(text = "Esto es un componente Banner de Dynamic List")
                },
                strategy = ShowCaseStrategy(firstToHappen = true),
                key = RenderType.BANNER.value,
                state = showCaseState
            )
            .clickable {
                viewModel.loadBanner(imageURL)
            },
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageURL)
            .crossfade(true)
            .diskCacheKey(imageURL)
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
}

@Composable
@Preview(showBackground = true)
fun PreviewCompactBannerComponentView() {
    val state = rememberShowCaseState()
    BannerComponentView(
        imageURL = "",
        componentIndex = 0,
        showCaseState = state
    )
}
