package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.basket.api.AddProductToBasketUseCase
import com.javi.basket.api.DecrementProductOnBasketUseCase
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.banner.BannerComponentViewScreen
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

const val BANNER_COMPONENT_TAG = "banner_component"

@ComponentFactory
class BannerFactory @Inject constructor(
    private val getProductDetailPageUseCase: GetProductDetailPageUseCase,
    private val addProductToBasketUseCase: AddProductToBasketUseCase,
    private val decrementProductOnBasketUseCase: DecrementProductOnBasketUseCase
) : DynamicListFactory {

    override val render = RenderType.BANNER

    override val hasShowCaseConfigured = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {

        val coroutine = rememberCoroutineScope()

        BannerComponentViewScreen(
            modifier = modifier.testTag(BANNER_COMPONENT_TAG),
            model = component.resource as BannerModel,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState,
            onAdd = {
                coroutine.launch {
                    addProductToBasketUseCase(it)
                }
            },
            onDecrement = { decrementProductOnBasketUseCase(it) }
        ) {
            componentInfo.dynamicListObject.destinationsNavigator?.navigate(
                getProductDetailPageUseCase(it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(150.dp)
                .background(MaterialTheme.colors.primary)
        )
    }
}
