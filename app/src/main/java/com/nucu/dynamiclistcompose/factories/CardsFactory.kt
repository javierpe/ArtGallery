package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.card.CardsModel
import com.nucu.dynamiclistcompose.components.card.CardsComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import javax.inject.Inject

class CardsFactory @Inject constructor(): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.CARDS
        )

    @Composable
    override fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentInfo: ComponentInfo
    ) {
        CardsComponentView(component.resource as CardsModel)
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(200.dp)
                    .height(100.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(200.dp)
                    .height(100.dp)
                    .background(MaterialTheme.colors.onPrimary)
            )
        }
    }
}