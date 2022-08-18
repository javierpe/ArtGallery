package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.banner.BannerComponentView
import com.nucu.dynamiclistcompose.components.banner.BannerModel
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class BannerFactory @Inject constructor(

): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER
        )

    override val hasShowCaseConfigured = true

    @Composable
    override fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentInfo: ComponentInfo
    ) {
        BannerComponentView(
            imageURL = (component.resource as BannerModel).imageURL,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(150.dp)
                .background(MaterialTheme.colors.onPrimary)
        )
    }
}