package com.javi.dynamic.list.presentation.factories

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.cards.page.api.GetCardsPageUseCase
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.card.CardsComponentViewScreen
import com.javi.dynamic.list.presentation.components.card.CardsModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import javax.inject.Inject

const val CARDS_COMPONENT_TAG = "cards_component"

@ComponentFactory
class CardsFactory @Inject constructor(
    private val getCardsPageUseCase: GetCardsPageUseCase
) : DynamicListFactory {

    override val render = RenderType.CARDS

    override val hasShowCaseConfigured: Boolean = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        CardsComponentViewScreen(
            modifier = modifier.testTag(CARDS_COMPONENT_TAG),
            data = component.resource as CardsModel,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState
        ) { id, title ->
            componentInfo.navigator()?.navigate(
                getCardsPageUseCase(
                    id = id,
                    title = title
                )
            )
        }
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
                    .background(MaterialTheme.colors.primary)
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .width(200.dp)
                    .height(100.dp)
                    .background(MaterialTheme.colors.primary)
            )
        }
    }
}
