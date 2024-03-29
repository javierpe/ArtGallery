package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.text.TextComponentView
import com.javi.dynamic.list.presentation.components.text.TextModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import javax.inject.Inject

const val TEXT_COMPONENT_TAG = "text_component"

@ComponentFactory
class TextFactory @Inject constructor() : DynamicListFactory {

    override val render = RenderType.TEXT

    override val hasShowCaseConfigured: Boolean = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        TextComponentView(
            modifier = modifier.testTag(TEXT_COMPONENT_TAG),
            componentIndex = component.index,
            componentInfo.showCaseState,
            text = (component.resource as TextModel).text
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .clip(RoundedCornerShape(7.dp))
                .width(140.dp)
                .height(30.dp)
                .background(MaterialTheme.colors.primary)
        )
    }
}
