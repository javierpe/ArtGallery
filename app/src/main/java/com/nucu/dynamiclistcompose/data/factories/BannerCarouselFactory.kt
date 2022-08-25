package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselComponentViewScreen
import javax.inject.Inject

class BannerCarouselFactory @Inject constructor(

): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_CAROUSEL
        )

    override val hasShowCaseConfigured: Boolean = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentInfo: ComponentInfo,
    ) {
        BannerCarouselComponentViewScreen(
            modifier = modifier.testTag("banner_carousel_component"),
            images = (component.resource as BannerCarouselModel).banners,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier.testTag("skeleton"),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .wrapContentWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .wrapContentWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )
        }
    }
}