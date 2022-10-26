package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.presentation.components.poster.PosterComponentScreenView
import com.nucu.dynamiclistcompose.presentation.components.poster.PosterModel
import javax.inject.Inject

class PosterFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType> = listOf(RenderType.POSTER)

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf { component.resource as PosterModel }
        }

        PosterComponentScreenView(
            model = model.value,
            isExpandedScreen = componentInfo.windowWidthSizeClass == WindowWidthSizeClass.Expanded
        )
    }

    @Composable
    override fun CreateSkeleton() {

    }
}