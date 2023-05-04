package com.javi.dynamic.list.data.extensions

import com.squareup.moshi.JsonAdapter
import java.io.IOException

fun <T> JsonAdapter<T>.tryFromJson(string: String?): T? {
    return try {
        fromJson(string.orEmpty())
    } catch (ignore: IOException) {
        null
    }
}