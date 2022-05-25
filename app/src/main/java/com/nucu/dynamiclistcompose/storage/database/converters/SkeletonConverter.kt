package com.nucu.dynamiclistcompose.storage.database.converters

import androidx.room.TypeConverter
import com.nucu.dynamiclistcompose.renders.base.RenderType
import org.json.JSONArray
import org.json.JSONObject

class SkeletonConverter {

    @TypeConverter
    fun fromRenders(renderType: List<RenderType>): String {
        val data = JSONObject()
        val list = JSONArray()
        renderType.map { it.value }.forEach { list.put(it) }

        return data.put("renders", list).toString()
    }

    @TypeConverter
    fun toRenders(renders: String): List<RenderType> {
        val data = JSONObject(renders).getJSONArray("renders")

        val renderList = mutableListOf<RenderType>()
        (0 until data.length()).forEach { item ->
            renderList.add(RenderType.valueOf(data.getString(item).uppercase()))
        }

        return renderList
    }
}