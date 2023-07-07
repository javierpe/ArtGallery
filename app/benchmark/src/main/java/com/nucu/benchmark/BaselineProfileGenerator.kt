package com.nucu.benchmark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalBaselineProfilesApi
@RequiresApi(Build.VERSION_CODES.P)
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(packageName = PACKAGE_NAME) {
            pressHome()
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            startActivityAndWait()
        }
}
