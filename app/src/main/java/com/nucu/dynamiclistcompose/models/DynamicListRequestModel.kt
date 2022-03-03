package com.nucu.dynamiclistcompose.models

data class DynamicListRequestModel(
    val context: String,
    val storeType: String,
    val aSources: AnalyticSources,
    val storeIds: List<Int> = arrayListOf(),
    val zoneIds: List<Int> = arrayListOf(),
    val shouldAddNewsFeed: Boolean = false,
    val state: HashMap<String, String>? = hashMapOf(),
    val orderId: String? = null,
    val shouldAddFooter: Boolean = true,
    val parentStoreType: String? = null,
    val hasPadding: Boolean = true
)