package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nucu.dynamiclistcompose.presentation.components.faces.FacesItemModel
import com.nucu.dynamiclistcompose.presentation.components.faces.FacesModel
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
class FacesRenderTest {

    private lateinit var render: FacesRender

    private val resource by lazy {
        """
          {
            "items": [
              {
                "name": "Salvador Dalí",
                "url": "https://i.pinimg.com/474x/d3/ba/44/d3ba44589354e6b2c2d317fb6a9d8d25.jpg"
              }
            ]
          }
        """
    }

    private val sourceOfTrueModel by lazy {
        FacesModel(
            items = listOf(
                FacesItemModel(
                    name = "Salvador Dalí",
                    url = "https://i.pinimg.com/474x/d3/ba/44/d3ba44589354e6b2c2d317fb6a9d8d25.jpg"
                )
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = FacesRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to FacesModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }
}