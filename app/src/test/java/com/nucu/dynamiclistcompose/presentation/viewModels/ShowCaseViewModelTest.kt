package com.nucu.dynamiclistcompose.presentation.viewModels

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.javi.api.TooltipPreferencesApi
import com.javi.design.system.viewModels.ShowCaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ShowCaseViewModelTest {

    @Mock
    lateinit var tooltipPreferencesApi: com.javi.api.TooltipPreferencesApi

    private lateinit var showCaseViewModel: ShowCaseViewModel

    private val preferenceKey = "preference-key"

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        showCaseViewModel = ShowCaseViewModel(
            tooltipPreferencesApi
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveState in preferences should be set preference value true`() = runTest {
        showCaseViewModel.setShowed(preferenceKey)

        TestScope(dispatcher).launch {
            verify(tooltipPreferencesApi).saveState(
                booleanPreferencesKey(preferenceKey),
                true
            )
        }
    }
}