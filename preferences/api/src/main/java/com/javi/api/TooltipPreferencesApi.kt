package com.javi.api

import kotlinx.coroutines.flow.Flow

interface TooltipPreferencesApi {

    suspend fun saveBooleanState(key: String, value: Boolean)

    suspend fun getBooleanState(key: String, defaultValue: Boolean): Flow<Boolean>
}
