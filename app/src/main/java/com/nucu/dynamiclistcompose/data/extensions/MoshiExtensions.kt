package com.nucu.dynamiclistcompose.data.extensions

import com.squareup.moshi.JsonAdapter
import java.io.IOException

fun <T> JsonAdapter<T>.tryFromJson(string: String?): T? {
    return try {
        lenient().fromJsonValue(string.orEmpty())
    } catch (ignore: IOException) {
        null
    }
}