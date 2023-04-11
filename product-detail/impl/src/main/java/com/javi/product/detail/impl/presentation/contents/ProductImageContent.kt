package com.javi.product.detail.impl.presentation.contents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.javi.data.DEEPLINK_MAIN
import com.javi.data.enums.ContextType
import com.javi.design.system.atoms.ImageComponentView
import com.javi.design.system.atoms.StepperButtonComponentView
import com.javi.design.system.molecules.headers.DynamicListHeaderComponentView
import com.javi.design.system.theme.DynamicListComposeTheme
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProductImageContent(
    imageURL: String,
    quantity: Int,
    destinationsNavigator: DestinationsNavigator? = null,
    onDecrement: () -> Unit
) {
    Box {

        ImageComponentView(
            modifier = Modifier.fillMaxSize(),
            imageURL = imageURL,
            contentScale = ContentScale.Crop
        )

        DynamicListHeaderComponentView(
            modifier = Modifier.align(Alignment.TopCenter),
            title = "Buy",
            contextType = ContextType.BANNER_DETAIL,
            destinationsNavigator = destinationsNavigator
        )

        StepperButtonComponentView(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
}

@Composable
@Preview
fun PreviewBannerContent() {
    DynamicListComposeTheme {
        ProductImageContent(
            imageURL = "Hello",
            quantity = 0,
            onDecrement = { }
        )
    }
}
