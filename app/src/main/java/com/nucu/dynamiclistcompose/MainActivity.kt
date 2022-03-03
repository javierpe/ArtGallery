package com.nucu.dynamiclistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nucu.dynamiclistcompose.adapters.DefaultAdapterController
import com.nucu.dynamiclistcompose.models.AnalyticSources
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.ui.base.ContextView
import com.nucu.dynamiclistcompose.ui.theme.DynamicListComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var bodyAdapterController: DefaultAdapterController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // TODO: Request model could be an state.
                    ContextView(
                        bodyComposeController = bodyAdapterController,
                        requestModel = DynamicListRequestModel(
                            context = "",
                            storeType = "",
                            aSources = AnalyticSources.MARKET
                        )
                    )
                }
            }
        }
    }
}
