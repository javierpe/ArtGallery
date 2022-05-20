package com.nucu.dynamiclistcompose.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.actions.DynamicListComponentAction
import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.components.tobacco.TobaccoComponentView
import com.nucu.dynamiclistcompose.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import com.nucu.dynamiclistcompose.ui.base.ScrollAction
import com.nucu.dynamiclistcompose.ui.theme.Skeleton
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
        componentAction: ((ScrollAction) -> Unit)?,
        widthSizeClass: WindowWidthSizeClass,
    ) {

        val coordinatesState = remember {
            mutableStateOf<LayoutCoordinates?>(null)
        }

        TobaccoComponentView {
            if (component.index == 0 && it.isAttached) {
                if (coordinatesState.value == null) {
                    coordinatesState.value = it
                }
            }
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(70.dp)
                .background(Skeleton)
        )
    }
}