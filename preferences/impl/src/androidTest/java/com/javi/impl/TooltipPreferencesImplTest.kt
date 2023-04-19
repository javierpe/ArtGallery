package com.javi.impl

import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TooltipPreferencesImplTest {

    private lateinit var tooltipPreferencesImpl: TooltipPreferencesImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        tooltipPreferencesImpl = TooltipPreferencesImpl(
            ApplicationProvider.getApplicationContext()
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun saveStateShouldSaveSuccess() = runTest {
        tooltipPreferencesImpl.saveBooleanState(
            "test-key",
            true
        )

        tooltipPreferencesImpl.getBooleanState(
            "test-key",
            false
        ).test {
            assert(awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}
