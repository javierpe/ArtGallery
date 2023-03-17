package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.render.processor.annotations.factory.AdapterFactory
import com.javi.render.processor.data.enums.RenderType
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.presentation.components.cardsGrid.CardsGridComponentViewScreen
import com.nucu.dynamiclistcompose.presentation.components.cardsGrid.CardsGridModel
import com.nucu.dynamiclistcompose.presentation.ui.components.VerticalGrid
import javax.inject.Inject

@AdapterFactory
class CardsGridFactory @Inject constructor(
    private val navigationController: NavigationController
): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.CARDS_GRID
        )

    override val hasShowCaseConfigured: Boolean = false

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo,
    ) {
        val model = remember {
            derivedStateOf {
                component.resource as CardsGridModel
            }
        }

        CardsGridComponentViewScreen(
            images = model.value.images,
            navigationController = navigationController,
        )
    }

    @Suppress("UnusedPrivateMember", "MagicNumber")
    @Composable
    override fun CreateSkeleton() {

        VerticalGrid(
            modifier = Modifier
                .testTag("card-container-skeleton")
        ) {
            for (x in 1..6) {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colors.primary)
                )
            }
        }
    }
}