package com.nucu.dynamiclistcompose.presentation.components.faces

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography

@Composable
fun FacesComponentView(
    modifier: Modifier,
    faces: List<FacesItemModel>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = modifier
    ) {
        items(items = faces, key = { it.hashCode() }) {
            FaceView(name = it.name, imageUrl = it.url)
        }
    }
}

@Composable
fun FaceView(
    name: String,
    imageUrl: String
) {

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.width(70.dp)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .diskCacheKey(imageUrl)
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

        Text(
            text = name,
            style = Typography.caption,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colors.secondary
        )
    }
}