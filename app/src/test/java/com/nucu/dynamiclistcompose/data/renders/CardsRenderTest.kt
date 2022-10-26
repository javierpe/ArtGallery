package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.javi.render.processor.RenderType
import com.nucu.dynamiclistcompose.presentation.components.card.CardElement
import com.nucu.dynamiclistcompose.presentation.components.card.CardImage
import com.nucu.dynamiclistcompose.presentation.components.card.CardsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CardsRenderTest {

    private lateinit var render: CardsRender

    private val resource by lazy {
        """
          {
            "title": "Nature",
            "items": [
              {
                "title": "Pets",
                "images": [
                  {
                    "image_url": "https://i.pinimg.com/474x/17/85/01/1785013f487a2a7ef3ef6ff763b07b8f.jpg"
                  }
                ]
              }
            ]
          }
        """
    }

    private val sourceOfTrueModel by lazy {
        CardsModel(
            title = "Nature",
            cardElements = listOf(
                CardElement(
                    title = "Pets",
                    images = listOf(
                        CardImage(
                            imageURL = "https://i.pinimg.com/474x/17/85/01/1785013f487a2a7ef3ef6ff763b07b8f.jpg"
                        )
                    )
                )
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = CardsRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to CardsModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }

    @Test
    fun `Render name should be CARDS`() = runTest {
        assert(render.renders.contains(RenderType.CARDS))
    }
}