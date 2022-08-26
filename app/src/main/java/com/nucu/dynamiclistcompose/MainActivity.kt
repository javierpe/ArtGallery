package com.nucu.dynamiclistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.hilt.navigation.compose.hiltViewModel
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.presentation.ui.base.ContextView
import com.nucu.dynamiclistcompose.presentation.ui.navigation.bannerScreenNav
import com.nucu.dynamiclistcompose.presentation.ui.navigation.cardScreenNav
import com.nucu.dynamiclistcompose.presentation.ui.navigation.homeNav
import com.nucu.dynamiclistcompose.presentation.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.presentation.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationController: NavigationController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().semantics {
                        testTagsAsResourceId = true
                    },
                    color = MaterialTheme.colors.background
                ) {
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

                    navigationController.NavHost {

                        // Main
                        homeNav(widthSizeClass)

                        // Banner screen
                        bannerScreenNav()

                        // Card screen
                        cardScreenNav(widthSizeClass)
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    widthSizeClass: WindowWidthSizeClass,
    viewModel: MainViewModel = hiltViewModel()
) {
    ContextView(
        title = "Art Gallery",
        widthSizeClass = widthSizeClass,
        viewModel = viewModel
    )
}
