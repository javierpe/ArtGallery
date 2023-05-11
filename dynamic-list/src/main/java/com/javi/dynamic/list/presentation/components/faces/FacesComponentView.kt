package com.javi.dynamic.list.presentation.components.faces

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.size.Size
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.theme.Typography

@Composable
fun FacesComponentView(
    modifier: Modifier,
    faces: List<FacesItemModel>,
    onClick: (Int) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = modifier,
    ) {
        itemsIndexed(items = faces) { _, item ->
            FaceView(name = item.name, imageUrl = item.url) {
                onClick(item.goTo)
            }
        }
    }
}

@Composable
fun FaceView(
    name: String,
    imageUrl: String,
    onClick: () -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.width(70.dp)
    ) {
        ImageComponentView(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClick()
                },
            imageURL = imageUrl,
            overrideSize = Size(
                100,
                100
            )
        )

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
