package com.nucu.dynamiclistcompose.domain.converters

import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.domain.database.converters.SkeletonConverter
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class SkeletonConverterTest {

    private lateinit var skeletonConverter: SkeletonConverter

    private val renders = """
            {
              "renders": [
                   "text",
                   "message"
              ]
            }
             """

    @Before
    fun setUp() {
        skeletonConverter = SkeletonConverter()
    }

    @Test
    fun `fromRenders should convert render list to valid JSON string`() {
        val json = skeletonConverter.fromRenders(
            listOf(
                RenderType.TEXT,
                RenderType.MESSAGE
            )
        )

        val convertToJSON = JSONObject(json)
        assert(convertToJSON.has("renders"))
    }

    @Test
    fun `toRenders should convert JSON string to valid render list`() {
        val data = skeletonConverter.toRenders(renders)
        val toMatchWith = listOf(
            RenderType.TEXT,
            RenderType.MESSAGE
        )

        assert(data == toMatchWith)
    }
}