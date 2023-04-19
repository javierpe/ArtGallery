package com.javi.impl

import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TooltipPreferencesImplTest {

    private val key = "test-key"

    private lateinit var tooltipPreferencesImpl: TooltipPreferencesImpl

    @Before
    fun setUp() {
        tooltipPreferencesImpl = TooltipPreferencesImpl(
            ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun saveStateShouldSaveSuccess() = runTest {
        tooltipPreferencesImpl.saveBooleanState(
            key,
            true
        )

        tooltipPreferencesImpl.getBooleanState(
            key,
            false
        ).test {
            assert(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
