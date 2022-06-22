package com.nucu.dynamiclistcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.javier.api.NavigationController
import com.nucu.dynamiclistcompose.ui.base.ContextView
import com.nucu.dynamiclistcompose.ui.navigation.bannerScreenNav
import com.nucu.dynamiclistcompose.ui.navigation.homeNav
import com.nucu.dynamiclistcompose.ui.theme.DynamicListComposeTheme
import com.nucu.dynamiclistcompose.ui.theme.Typography
import com.nucu.dynamiclistcompose.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationController: NavigationController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DynamicListComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass

                    navigationController.NavHost {

                        // Main
                        homeNav(widthSizeClass)

                        // Banner
                        bannerScreenNav()
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
    MainContent(widthSizeClass = widthSizeClass) {
        ContextView(
            title = "Screen title",
            widthSizeClass = widthSizeClass,
            viewModel = viewModel
        )
    }
}

@Composable
fun MainContent(
    widthSizeClass: WindowWidthSizeClass,
    content: @Composable () -> Unit
) {
    when (widthSizeClass) {

        WindowWidthSizeClass.Compact -> {
            content()
        }

        WindowWidthSizeClass.Expanded -> {
            Row {
                Box(
                    modifier = Modifier
                        .width(500.dp)
                        .fillMaxHeight()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colors.primary),
                ) {
                    Text(
                        text = "Navigation",
                        style = Typography.h4,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentHeight()
                            .alpha(0.5f)
                            .align(Center)
                    )
                }

                content()
            }
        }

        WindowWidthSizeClass.Medium -> {
            content()
        }
    }
}
