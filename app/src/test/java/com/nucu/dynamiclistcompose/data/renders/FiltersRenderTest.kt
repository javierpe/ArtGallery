package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.nucu.dynamiclistcompose.presentation.components.filters.FilterItemModel
import com.nucu.dynamiclistcompose.presentation.components.filters.FiltersModel
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
class FiltersRenderTest {

    private lateinit var render: FiltersRender

    private val resource by lazy {
        """
          {
            "items": [
              {
                "text": "Abstract banner",
                "go_to": "text"
              }
            ]
          }
        """
    }

    private val sourceOfTrueModel by lazy {
        FiltersModel(
            items = listOf(
                FilterItemModel(
                    text = "Abstract banner",
                    goTo = "text"
                )
            )
        )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = FiltersRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to FiltersModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }
}