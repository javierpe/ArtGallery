package com.nucu.dynamiclistcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.metrics.performance.JankStats
import com.javi.design.system.theme.DynamicListComposeTheme
import com.javi.navigation.api.NavigationContractApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationContractApi: NavigationContractApi

    private var jankStats: JankStats? = null

    private val jankFrameListener = JankStats.OnFrameListener { frameData ->
        if (frameData.isJank) {
            Log.v("JankStatsDynamicListCompose", frameData.toString())
        }
    }

    @OptIn(
        ExperimentalComposeUiApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicListComposeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .semantics {
                            testTagsAsResourceId = true
                        },
                    color = colorResource(id = R.color.ic_launcher_background)
                ) {
                    navigationContractApi.NavHost()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (jankStats == null) {
            jankStats = JankStats.createAndTrack(window, jankFrameListener)
        } else {
            jankStats?.isTrackingEnabled = true
        }
    }

    override fun onPause() {
        super.onPause()
        jankStats?.isTrackingEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
