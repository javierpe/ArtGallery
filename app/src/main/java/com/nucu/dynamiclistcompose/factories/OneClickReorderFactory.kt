package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.oneclick.OneClickModel
import com.nucu.dynamiclistcompose.components.oneclick.OneClickReorderComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
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
                .placeholder(
                    visible = true,
                    highlight = PlaceholderHighlight.fade(Color.Gray),
                    color = Color.Gray,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}