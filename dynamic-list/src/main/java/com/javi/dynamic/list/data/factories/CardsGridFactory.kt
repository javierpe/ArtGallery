package com.javi.dynamic.list.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.design.system.molecules.VerticalGrid
import com.javi.dynamic.list.data.factories.base.DynamicListFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.cardsGrid.CardsGridComponentViewScreen
import com.javi.dynamic.list.presentation.components.cardsGrid.CardsGridModel
import com.javi.product.detail.api.ProductDetailScreenLoader
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.AdapterFactory
import javax.inject.Inject

@AdapterFactory
class CardsGridFactory @Inject constructor(
    private val productDetailScreenLoader: ProductDetailScreenLoader
): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.CARDS_GRID
        )

    override val hasShowCaseConfigured: Boolean = false

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo,
    ) {
        val model = remember {
            derivedStateOf {
                component.resource as CardsGridModel
            }
        }

        CardsGridComponentViewScreen(
            images = model.value.images,
        ) {
            componentInfo.navigator()?.navigate(
                productDetailScreenLoader.getDestination(
                    it
                )
            )
        }
    }

    @Suppress("UnusedPrivateMember", "MagicNumber")
    @Composable
    override fun CreateSkeleton() {

        VerticalGrid(
            modifier = Modifier
                .testTag("card-container-skeleton")
        ) {
            for (x in 1..6) {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
    }
}