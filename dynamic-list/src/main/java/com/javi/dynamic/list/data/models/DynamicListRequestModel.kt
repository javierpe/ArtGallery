package com.javi.dynamic.list.data.models

import android.os.Parcelable
import com.javi.data.enums.ContextType
import kotlinx.parcelize.Parcelize

@Parcelize
data class DynamicListRequestModel(
    val contextType: ContextType,
    val state: HashMap<String, String> = hashMapOf()
) : Parcelable
