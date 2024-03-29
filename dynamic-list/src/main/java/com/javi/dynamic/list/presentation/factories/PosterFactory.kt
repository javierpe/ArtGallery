package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.poster.PosterComponentScreenView
import com.javi.dynamic.list.presentation.components.poster.PosterModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.product.detail.api.GetProductDetailPageUseCase
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import javax.inject.Inject

const val POSTER_COMPONENT_TAG = "poster_component_tag"

@ComponentFactory
class PosterFactory @Inject constructor(
    private val getProductDetailScreenUseCase: GetProductDetailPageUseCase
) : DynamicListFactory {

    override val render = RenderType.POSTER

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        PosterComponentScreenView(
            modifier = Modifier.testTag(POSTER_COMPONENT_TAG),
            model = component.resource as PosterModel
        ) {
            componentInfo.navigator()?.navigate(
                getProductDetailScreenUseCase(it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .testTag("skeleton")
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colors.primary)
        )
    }
}
