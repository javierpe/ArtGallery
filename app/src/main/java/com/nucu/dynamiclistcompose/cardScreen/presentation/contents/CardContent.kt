package com.nucu.dynamiclistcompose.cardScreen.presentation.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.presentation.components.common.ImageComponentView
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
                ImageComponentView(
                    modifier = Modifier
                        .wrapContentHeight()
                        .height(250.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    imageURL = item
                )
            }
        }
    }
}