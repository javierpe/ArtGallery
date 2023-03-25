package com.javi.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.javi.api.TooltipPreferencesApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = TooltipPreferencesImpl.DATA_STORE_NAME
)

class TooltipPreferencesImpl @Inject constructor(
    @ApplicationContext val context: Context
) : TooltipPreferencesApi {


    override suspend fun saveBooleanState(key: String, value: Boolean) {
        saveState(
            state = booleanPreferencesKey(key),
            value = value
        )
    }

    override suspend fun getBooleanState(key: String, defaultValue: Boolean): Flow<Boolean> {
        return getState(
            state = booleanPreferencesKey(key),
            defaultValue = defaultValue
        )
    }

    private suspend fun <T> saveState(state: Preferences.Key<T>, value: T) {
        context.dataStore.edit {
            it[state] = value
        }
    }

    private fun <T> getState(
        state: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> = context.dataStore.data.map { preferences -> preferences[state] ?: defaultValue }

    companion object {
        const val DATA_STORE_NAME = "tooltips"
    }
}