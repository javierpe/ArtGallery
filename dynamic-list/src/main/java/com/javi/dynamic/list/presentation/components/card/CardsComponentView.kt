package com.javi.dynamic.list.presentation.components.card

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import com.javi.design.system.data.showCase.ShapeType
import com.javi.design.system.data.showCase.ShowCaseStrategy
import com.javi.design.system.atoms.TitleDecoratedView
import com.javi.design.system.molecules.CardItemView
import com.javi.design.system.molecules.showCase.ShowCaseState
import com.javi.design.system.molecules.showCase.ShowCaseStyle
import com.javi.design.system.molecules.TooltipView
import com.javi.design.system.molecules.showCase.asShowCaseTarget
import com.javi.design.system.molecules.showCase.rememberShowCaseState
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.dynamic.list.R
import com.javi.render.processor.core.RenderType

const val CARD_COMPONENT_SCREEN_TAG = "card_component_screen"
const val CARD_COMPONENT_TAG = "card_component"

@Composable
fun CardsComponentViewScreen(
    modifier: Modifier,
    data: CardsModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onCardPage: (Int, String) -> Unit
) {
    CardsComponentView(
        modifier = modifier.testTag(CARD_COMPONENT_SCREEN_TAG),
        data = data,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) { id, title ->
        onCardPage(id, title)
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

                CardItemView(
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
@Preview(
    uiMode = UI_MODE_NIGHT_YES,
)
fun PreviewNightModeCardsComponentView() {
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

@Composable
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
fun PreviewNoNightModeCardsComponentView() {
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