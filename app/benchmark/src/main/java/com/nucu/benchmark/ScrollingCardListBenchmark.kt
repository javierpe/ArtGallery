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

private const val CARD_CONTAINER_TAG = "card-container"
private const val CAROUSEL_CARDS = "carousel-cards"
private const val CARD_TAG = "card-item"
private const val SHOW_CASE_TAG = "show-case"

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

        // Wait to until show case is hidden
        device.wait(Until.gone(By.res(SHOW_CASE_TAG)), TIMEOUT)

        // Wait to carousel cards is showed
        device.wait(Until.hasObject(By.res(CAROUSEL_CARDS)), TIMEOUT)

        // Get card item component and click it
        device.findObject(By.res(CARD_TAG)).click()

        // Wait to card list is showed
        device.wait(Until.hasObject(By.res(CARD_CONTAINER_TAG)), TIMEOUT)

        // Get card list
        val container = device.findObject(By.res(CARD_CONTAINER_TAG))

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
        // Wait to until show case is hidden
        device.wait(Until.gone(By.res(SHOW_CASE_TAG)), TIMEOUT)

        // Wait to carousel cards is showed
        device.wait(Until.hasObject(By.res(CAROUSEL_CARDS)), TIMEOUT)

        // Get card list
        val container = device.findObject(By.res(CAROUSEL_CARDS))

        // Set gesture margin to avoid triggering gesture navigation
        // with input events from automation.
        container.setGestureMargin(device.displayWidth / GESTURE_MARGIN)

        // Scroll down
        container.fling(Direction.RIGHT)
    }
}