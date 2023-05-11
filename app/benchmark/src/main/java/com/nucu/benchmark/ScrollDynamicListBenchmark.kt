package com.nucu.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val MAIN_CONTAINER_TAG = "dynamic-list-container"
private const val SHOW_CASE_TAG = "show-case"

private const val GESTURE_MARGIN = 2
private const val TIMEOUT: Long = 30_000

@RunWith(AndroidJUnit4ClassRunner::class)
class ScrollDynamicListBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollingDynamicListContainer() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 50,
        startupMode = StartupMode.COLD, // Restarts activity each iteration
        setupBlock = {
            pressHome()
            startActivityAndWait() // Time To Initial Display (TTID)
        }
    ) {
        // Wait to until show case is hidden
        device.wait(Until.gone(By.res(SHOW_CASE_TAG)), TIMEOUT)

        device.wait(Until.hasObject(By.res(MAIN_CONTAINER_TAG)), TIMEOUT) // Time To Frame Display (TTFD)

        // Dynamic list container reference
        val dynamicListContainer = device.findObject(By.res(MAIN_CONTAINER_TAG))

        dynamicListContainer.setGestureMargin(device.displayWidth / GESTURE_MARGIN)

        // Scroll down several times
        dynamicListContainer.swipe(Direction.DOWN, 1f, 8000)
    }
}
