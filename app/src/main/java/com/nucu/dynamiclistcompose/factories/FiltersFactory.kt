package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.filters.FiltersComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentInfo
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.base.TargetAction
import com.nucu.dynamiclistcompose.ui.theme.Skeleton
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
        componentInfo: ComponentInfo
    ) {
        FiltersComponentView {
            componentInfo.scrollAction?.invoke(
                ScrollAction.ScrollRender(it, target = TargetAction.BODY)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            val width = 60.dp
            val height = 30.dp

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Skeleton)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Skeleton)
                    .width(width)
                    .height(height)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(Skeleton)
                    .width(width)
                    .height(height)
            )
        }
    }
}