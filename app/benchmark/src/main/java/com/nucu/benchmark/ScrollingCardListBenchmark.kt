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

private const val CARD_COMPONENT_TAG = "card_component"
private const val CARD_ITEM_COMPONENT_TAG = "card_item_component"
private const val CARDS_GRID_TAG = "cards_grid"
private const val DYNAMIC_LIST_CONTAINER = "dynamic-list-container"

private const val GESTURE_MARGIN = 5
private const val TIMEOUT: Long = 30_000

@RunWith(AndroidJUnit4ClassRunner::class)
class ScrollingCardListBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scrollingCardContainer() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM, // Restarts activity each iteration
        setupBlock = {
            pressHome()
            startActivityAndWait() // Time To Initial Display (TTID)
        }
    ) {
        // Wait to carousel cards is showed
        device.wait(Until.hasObject(By.res(CARD_COMPONENT_TAG)), TIMEOUT)

        // Get card item component and click it
        device.findObject(By.res(CARD_ITEM_COMPONENT_TAG)).click()

        // Wait to card list is showed
        device.wait(Until.hasObject(By.res(CARDS_GRID_TAG)), TIMEOUT)

        // Get card list
        val container = device.findObject(By.res(DYNAMIC_LIST_CONTAINER))

        // Set gesture margin to avoid triggering gesture navigation
        // with input events from automation.
        container.setGestureMargin(device.displayWidth / GESTURE_MARGIN)

        // Scroll down
        container.fling(Direction.DOWN)
    }

    @Test
    fun scrollingCarousel() = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM, // Restarts activity each iteration
        setupBlock = {
            pressHome()
            startActivityAndWait() // Time To Initial Display (TTID)
        }
    ) {
        // Wait to carousel cards is showed
        device.wait(Until.hasObject(By.res(CARD_COMPONENT_TAG)), TIMEOUT)

        // Get card list
        val container = device.findObject(By.res(CARD_COMPONENT_TAG))

        // Set gesture margin to avoid triggering gesture navigation
        // with input events from automation.
        container.setGestureMargin(device.displayWidth / GESTURE_MARGIN)

        // Scroll down
        container.fling(Direction.RIGHT)
    }
}
