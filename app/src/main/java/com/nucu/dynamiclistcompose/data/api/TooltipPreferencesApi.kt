package com.nucu.dynamiclistcompose.data.api

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface TooltipPreferencesApi {
    suspend fun <T> saveState(state: Preferences.Key<T>, value: T)

    suspend fun <T> getState(state: Preferences.Key<T>, defaultValue: T): Flow<T>
}