package com.nucu.dynamiclistcompose.factories

import androidx.compose.runtime.Composable
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.banner.BannerComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class BannerFactory @Inject constructor(): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER_IMAGE
        )

    @Composable
    override fun Create(
        component: ComponentItemModel,
        storeType: String?,
        source: String?,
        listener: DynamicListComponentListener?
    ) {
        BannerComponentView()
    }
}