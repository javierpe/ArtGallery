package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.presentation.components.text.TextComponentView
import com.nucu.dynamiclistcompose.presentation.components.text.TextModel
import com.nucu.dynamiclistcompose.data.listeners.DynamicListComponentListener
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import javax.inject.Inject

class TextFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.TEXT
        )

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        listener: DynamicListComponentListener?,
        componentInfo: ComponentInfo
    ) {
        TextComponentView(
            modifier = modifier,
            componentIndex = component.index,
            componentInfo.showCaseState,
            text = (component.resource as TextModel).text
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(7.dp))
                .fillMaxWidth()
                .height(30.dp)
                .background(MaterialTheme.colors.onPrimary)
        )
    }
}