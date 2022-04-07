package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.tobacco.TobaccoComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.DynamicListComponentAction
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import javax.inject.Inject

class TobaccoFactory @Inject constructor(): DynamicListAdapterFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.TOBACCO_PREFERENCE
        )

    @Composable
    override fun CreateComponent(
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentAction: DynamicListComponentAction
    ) {

        val coordinatesState = remember {
            mutableStateOf<LayoutCoordinates?>(null)
        }

        TobaccoComponentView(false) {
            if (component.index == 0 && it.isAttached) {
                if (coordinatesState.value == null) {
                    coordinatesState.value = it
                }
            }
        }

        if (coordinatesState.value != null) {
            LaunchedEffect(Unit) {
                componentAction.scrollAction(
                    ScrollAction.ScrollWithTooltip(
                        renderType = RenderType.BANNER_IMAGE,
                        message = "Esto es un tooltip desde un componente de DL Compose",
                        coordinates = coordinatesState.value!!
                    )
                )
            }
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .background(Color.LightGray)
        )
    }
}