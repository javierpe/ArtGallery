package com.nucu.dynamiclistcompose.data.renders

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.presentation.components.message.MessageModel
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
class MessageRenderTest {

    private lateinit var render: MessageRender

    private val resource by lazy {
        """
          {
            "message": "Hello!"
          }
        """
    }

    private val sourceOfTrueModel by lazy {
         MessageModel(
             message = "Hello!"
         )
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        render = MessageRender(
            Gson()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `resolve should convert JSON to MessageModel`() = runTest {
        val model = render.resolve(String(), JsonParser().parse(resource).asJsonObject)
        assert(model == sourceOfTrueModel)
    }

    @Test
    fun `Render name should be MESSAGE`() = runTest {
        assert(render.renders.contains(RenderType.MESSAGE))
    }
}