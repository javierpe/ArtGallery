package com.nucu.dynamiclistcompose.cardScreen.presentation.contents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.presentation.ui.components.headers.DynamicListHeaderComponentView

@Composable
fun CardContent(
    title: String,
    images: List<String>,
    widthSizeClass: WindowWidthSizeClass
) {

    val listState = rememberLazyGridState()

    val size = when(widthSizeClass) {
        WindowWidthSizeClass.Compact -> 150.dp
        WindowWidthSizeClass.Expanded -> 250.dp
        WindowWidthSizeClass.Medium -> 250.dp
        else -> 150.dp
    }

    Scaffold(
        topBar = {
            DynamicListHeaderComponentView(
                title = title,
                contextType = ContextType.CARD_DETAIL,
                bodyLazyGridState = listState
            )
        }
    ) { paddingValues ->

        LazyVerticalGrid(
            modifier = Modifier
                .testTag("card-container")
                .padding(paddingValues),
            state = listState,
            columns = GridCells.Adaptive(size),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(items = images) { _, item ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .wrapContentHeight()
                        .height(250.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item)
                        .crossfade(true)
                        .diskCacheKey(item)
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
                            println((painter.state as AsyncImagePainter.State.Error).result.throwable.message)
                        }
                        else -> {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        }
    }
}