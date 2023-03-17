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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.render.processor.annotations.factory.AdapterFactory
import com.javi.render.processor.data.enums.RenderType
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselComponentViewScreen
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
import javax.inject.Inject

@AdapterFactory
class BannerCarouselFactory @Inject constructor(
    private val navigationController: NavigationController
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
        componentInfo: ComponentInfo,
    ) {

        val model = remember {
            derivedStateOf {
                (component.resource as BannerCarouselModel).banners
            }
        }
        BannerCarouselComponentViewScreen(
            modifier = modifier.testTag("banner_carousel_component"),
            images = model.value,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState,
            widthSizeClass = componentInfo.dynamicListObject.widthSizeClass,
            navigationController = navigationController
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
                    .background(MaterialTheme.colors.primary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .wrapContentWidth()
                    .height(100.dp)
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
}