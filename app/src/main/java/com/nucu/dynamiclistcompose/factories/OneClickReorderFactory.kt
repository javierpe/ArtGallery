package com.nucu.dynamiclistcompose.factories

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.oneclick.OneClickModel
import com.nucu.dynamiclistcompose.components.oneclick.OneClickReorderComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class OneClickReorderFactory @Inject constructor(): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.ONE_CLICK_REORDER
        )

    @Composable
    override fun Create(
        component: ComponentItemModel,
        storeType: String?,
        source: String?,
        listener: DynamicListComponentListener?
    ) {
        OneClickReorderComponentView(
            listOf(
                OneClickModel("Product 1"),
                OneClickModel("Product 2"),
            ), "2 Marzo"
        ) { }
    }
}