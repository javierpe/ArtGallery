package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.filters.FilterItemComponent
import com.nucu.dynamiclistcompose.components.filters.FiltersComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import javax.inject.Inject

class FiltersFactory @Inject constructor(): DynamicListAdapterFactory {
    override val renders: List<RenderType>
        get() = listOf(
            RenderType.FILTERS
        )

    @Composable
    override fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentAction: DynamicListComponentAction
    ) {
        FiltersComponentView {
            componentAction.scrollAction(ScrollAction.ScrollIndex(it))
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            FilterItemComponent("") { }

            FilterItemComponent("") { }

            FilterItemComponent("") { }

            FilterItemComponent("") { }

            FilterItemComponent("") { }
        }
    }
}