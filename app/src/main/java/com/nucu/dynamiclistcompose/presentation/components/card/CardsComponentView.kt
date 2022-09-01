package com.nucu.dynamiclistcompose.presentation.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nucu.dynamiclistcompose.R
import com.nucu.dynamiclistcompose.data.models.showCase.ShapeType
import com.nucu.dynamiclistcompose.data.models.showCase.ShowCaseStrategy
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.destinations.CardScreenDestination
import com.nucu.dynamiclistcompose.presentation.components.common.CardItemVIew
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.ShowCaseStyle
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.TooltipView
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.asShowCaseTarget
import com.nucu.dynamiclistcompose.presentation.ui.components.showCase.rememberShowCaseState
import com.nucu.dynamiclistcompose.presentation.ui.theme.Typography
import com.nucu.dynamiclistcompose.presentation.viewModels.CardsViewModel

const val CARD_COMPONENT_SCREEN_TAG = "card_component_screen"
const val CARD_COMPONENT_TAG = "card_component"

@Composable
fun CardsComponentViewScreen(
    modifier: Modifier,
    data: CardsModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    viewModel: CardsViewModel = hiltViewModel(),
) {
    CardsComponentView(
        modifier = modifier.testTag(CARD_COMPONENT_SCREEN_TAG),
        data = data,
        componentIndex = componentIndex,
        showCaseState = showCaseState
    ) { title, images ->
        viewModel.navigateToCardsDetail(
            CardScreenDestination(title, images.toTypedArray())
        )
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun CardsComponentView(
    modifier: Modifier,
    data: CardsModel,
    componentIndex: Int,
    showCaseState: ShowCaseState,
    onNavigateToDetail:(String, List<String>) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {

        val decoration by remember {
            derivedStateOf {
                TextDecoration.Underline
            }
        }

        val letterSpacing by remember {
            derivedStateOf {
                TextUnit(40f, TextUnitType.Sp)
            }
        }

        val titleUpperCase by remember {
            derivedStateOf { data.title.uppercase() }
        }

        Text(
            text = titleUpperCase,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            style = Typography.h6,
            color = MaterialTheme.colors.secondary,
            letterSpacing = letterSpacing,
            textAlign = TextAlign.Center,
            textDecoration = decoration
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
                    onNavigateToDetail.invoke(item.title,
                        item.images.map { it.imageURL })
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCardsComponentView() {
    CardsComponentView(
        data = CardsModel(
            cardElements = listOf(
                CardElement("Hola", images = emptyList())
            ),
            title = "Title",
        ),
        componentIndex = 0,
        showCaseState = rememberShowCaseState(),
        modifier = Modifier
    ) { _, _ -> }
}