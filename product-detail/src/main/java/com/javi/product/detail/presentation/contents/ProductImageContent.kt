package com.javi.product.detail.presentation.contents

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.navDeepLink
import com.javi.data.DEEPLINK_MAIN
import com.javi.data.enums.ContextType
import com.javi.design.system.ImageComponentView
import com.javi.design.system.atoms.StepperButtonComponentView
import com.javi.design.system.molecules.headers.DynamicListHeaderComponentView
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProductImageContent(
    imageURL: String,
    quantity: Int,
    destinationsNavigator: DestinationsNavigator? = null,
    onAdd: () -> Unit,
    onDecrement: () -> Unit
) {
    Scaffold(
        topBar = {
            DynamicListHeaderComponentView(
                title = "Buy",
                contextType = ContextType.BANNER_DETAIL,
                destinationsNavigator = destinationsNavigator
            )
        },
        bottomBar = {
            StepperButtonComponentView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                quantity = quantity,
                onAdd = {
                    destinationsNavigator?.navigate(
                        route = "${DEEPLINK_MAIN}cards_page/1/Cactus/",
                        onlyIfResumed = true
                    )
                },
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
    ProductImageContent(
        imageURL = "Hello",
        quantity = 0,
        onAdd = { },
        onDecrement = { }
    )
}