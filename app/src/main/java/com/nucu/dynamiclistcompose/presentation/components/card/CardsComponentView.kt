package com.nucu.dynamiclistcompose.presentation.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.javi.render.processor.data.enums.RenderType
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.destinations.CardScreenDestination
import com.nucu.dynamiclistcompose.presentation.components.common.CardItemVIew
import com.nucu.dynamiclistcompose.presentation.components.common.TitleDecoratedView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme

const val CARD_COMPONENT_SCREEN_TAG = "card_component_screen"
const val CARD_COMPONENT_TAG = "card_component"

@Composable
fun CardsComponentViewScreen(
    modifier: Modifier,
    data: CardsModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    navigationController: NavigationController
) {
    CardsComponentView(
        modifier = modifier.testTag(CARD_COMPONENT_SCREEN_TAG),
        data = data,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) { id, title ->
        navigationController.navigateTo(
            CardScreenDestination(
                id = id,
                title = title
            )
        )
    }
}

@Composable
fun CardsComponentView(
    modifier: Modifier,
    data: CardsModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onNavigateToDetail:(Int, String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {

        TitleDecoratedView(
            text = data.title
        )

        LazyRow(
            modifier = Modifier.testTag(CARD_COMPONENT_TAG),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        ) {
            itemsIndexed(
                items = data.cardElements
            ) { index, item ->

                val modifierCard = if (index == 0) {
                    Modifier.asShowCaseTarget(
                        index = componentIndex,
                        style = ShowCaseStyle.Default.copy(
                            shapeType = ShapeType.RECTANGLE,
                            cornerRadius = 16.dp,
                            withAnimation = false
                        ),
                        content = {
                            TooltipView(text = stringResource(R.string.tooltip_cards))
                        },
                        strategy = ShowCaseStrategy(firstToHappen = true),
                        key = RenderType.CARDS.value,
                        state = showCaseState
                    )
                } else Modifier

                CardItemVIew(
                    modifier = modifierCard,
                    title = item.title,
                    images = item.images
                ) {
                    onNavigateToDetail.invoke(
                        item.id,
                        item.title
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCardsComponentView() {
    DynamicListComposeTheme {
        CardsComponentView(
            data = CardsModel(
                cardElements = listOf(
                    CardElement(1, "Hola", images = emptyList())
                ),
                title = "Title",
            ),
            componentIndex = 0,
            showCaseState = rememberShowCaseState(),
            modifier = Modifier
        ) { _, _ -> }
    }
}