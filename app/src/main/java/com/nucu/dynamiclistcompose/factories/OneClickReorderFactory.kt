package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.oneclick.OneClickModel
import com.nucu.dynamiclistcompose.components.oneclick.OneClickReorderComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.theme.Purple200
import com.nucu.dynamiclistcompose.ui.theme.Skeleton
import javax.inject.Inject

class OneClickReorderFactory @Inject constructor(): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.ONE_CLICK_REORDER
        )

    @Composable
    override fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentAction: DynamicListComponentAction
    ) {
        OneClickReorderComponentView(
            listOf(
                OneClickModel("Product 1"),
                OneClickModel("Product 2"),
            ), "Date"
        ) { }
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .padding(16.dp)
                .background(Skeleton)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}