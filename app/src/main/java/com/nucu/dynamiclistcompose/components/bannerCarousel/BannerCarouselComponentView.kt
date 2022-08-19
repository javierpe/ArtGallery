package com.nucu.dynamiclistcompose.components.bannerCarousel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.components.banner.BannerModel
import com.nucu.dynamiclistcompose.viewModels.BannerViewModel

@Composable
fun BannerCarouselComponentView(
    images: List<BannerModel>,
    viewModel: BannerViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        items(items = images, key = { it.hashCode() }) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .height(300.dp)
                    .width(350.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        viewModel.loadBanner(it.imageURL)
                    },
                model = ImageRequest.Builder(context)
                    .data(it.imageURL)
                    .crossfade(true)
                    .diskCacheKey(it.imageURL)
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
        }
    }
}