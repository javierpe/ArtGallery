package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.message.MessageComponentView
import com.javi.dynamic.list.presentation.components.message.MessageModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import javax.inject.Inject

const val MESSAGE_COMPONENT_TAG = "message_component"

@ComponentFactory
class MessageFactory @Inject constructor() : DynamicListFactory {

    override val render = RenderType.MESSAGE

    override val hasShowCaseConfigured = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        MessageComponentView(
            modifier = modifier.testTag(MESSAGE_COMPONENT_TAG),
            message = component.resource as MessageModel,
            componentIndex = component.index,
            componentInfo.showCaseState
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(80.dp)
                .background(MaterialTheme.colors.primary)
        )
    }
}
