package com.javi.dynamic.list.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import com.javi.dynamic.list.data.factories.base.DynamicListFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselComponentViewScreen
import com.javi.dynamic.list.presentation.components.bannerCarousel.BannerCarouselModel
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.AdapterFactory
import javax.inject.Inject

const val BANNER_CAROUSEL_COMPONENT_TAG = "banner_carousel_component"
@AdapterFactory
class BannerCarouselFactory @Inject constructor(
    private val productDetailScreenLoader: GetProductDetailPageUseCase
) : DynamicListFactory {

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
            modifier = modifier.testTag(BANNER_CAROUSEL_COMPONENT_TAG),
            images = model.value,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState,
            widthSizeClass = componentInfo.dynamicListObject.widthSizeClass
        ) {
            componentInfo.dynamicListObject.destinationsNavigator?.navigate(
                direction = productDetailScreenLoader(it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("skeleton"),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .height(300.dp)
                    .width(350.dp)
                    .background(MaterialTheme.colors.primary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .wrapContentWidth()
                    .height(300.dp)
                    .width(350.dp)
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
}
