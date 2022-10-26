package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.render.processor.annotations.FactoryModule
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.presentation.components.card.CardsComponentViewScreen
import com.nucu.dynamiclistcompose.presentation.components.card.CardsModel
import javax.inject.Inject

@FactoryModule
class CardsFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.CARDS
        )

    override val hasShowCaseConfigured: Boolean = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf {
                component.resource as CardsModel
            }
        }
        CardsComponentViewScreen(
            modifier = modifier.testTag("cards_component"),
            data = model.value,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier.testTag("skeleton"),
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