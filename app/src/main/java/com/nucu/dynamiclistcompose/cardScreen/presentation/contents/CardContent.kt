package com.nucu.dynamiclistcompose.cardScreen.presentation.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
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
import com.nucu.dynamiclistcompose.ui.components.DynamicListHeaderComponentView

@Composable
fun CardContent(
    title: String,
    images: List<String>,
    onBackPressed: () -> Unit
) {

    val listState = rememberLazyGridState()

    Scaffold(
        topBar = {
            DynamicListHeaderComponentView(
                title = title,
                contextType = ContextType.CARD_DETAIL,
                onBackPressed = onBackPressed,
                bodyLazyGridState = listState
            )
        }
    ) {

        LazyVerticalGrid(
            modifier = Modifier.testTag("card-container"),
            state = listState,
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            itemsIndexed(items = images) { index, item ->
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .wrapContentHeight()
                        .height(250.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
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