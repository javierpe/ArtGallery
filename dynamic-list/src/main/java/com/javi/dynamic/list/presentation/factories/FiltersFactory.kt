package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.filters.Filters
import com.javi.dynamic.list.presentation.components.filters.FiltersComponentViewScreen
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.AdapterFactory
import javax.inject.Inject

const val FILTERS_COMPONENT_TAG = "filters_component"

@AdapterFactory
class FiltersFactory @Inject constructor() : DynamicListFactory {

    override val render = RenderType.FILTERS

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        FiltersComponentViewScreen(
            modifier = modifier.testTag(FILTERS_COMPONENT_TAG),
            data = (component.resource as Filters).items,
            windowWidthSizeClass = componentInfo.dynamicListObject.widthSizeClass
        ) {
            componentInfo.scrollAction?.invoke(
                com.javi.dynamic.list.data.actions.ScrollAction.ScrollRender(it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .testTag("skeleton")
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val width = 80.dp
            val height = 40.dp

            for (ignored in 0..5) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(MaterialTheme.colors.primary)
                        .width(width)
                        .height(height)
                )
            }
        }
    }
}
