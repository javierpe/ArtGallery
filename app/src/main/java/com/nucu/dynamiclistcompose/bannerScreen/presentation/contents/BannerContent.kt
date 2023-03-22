@file:OptIn(ExperimentalMaterial3Api::class)

package com.nucu.dynamiclistcompose.bannerScreen.presentation.contents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.models.ContextType
import com.nucu.dynamiclistcompose.presentation.components.common.ImageComponentView
import com.nucu.dynamiclistcompose.presentation.ui.components.StepperButtonComponentView
import com.nucu.dynamiclistcompose.presentation.ui.components.headers.DynamicListHeaderComponentView
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProductImageContent(
    navigator: DestinationsNavigator? = null,
    imageURL: String,
    quantity: Int,
    onAdd: () -> Unit,
    onDecrement: () -> Unit
) {
    Scaffold(
        topBar = {
            DynamicListHeaderComponentView(
                title = "Buy",
                contextType = ContextType.BANNER_DETAIL,
                navigator = navigator
            )
        },
        bottomBar = {
            StepperButtonComponentView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                quantity = quantity,
                onAdd = onAdd,
                onDecrement = onDecrement
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            ImageComponentView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                imageURL = imageURL,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
@Preview
fun PreviewBannerContent() {
    DynamicListComposeTheme {
        ProductImageContent(
            imageURL = "Hello",
            quantity = 0,
            onAdd = { },
            onDecrement = { }
        )
    }
}