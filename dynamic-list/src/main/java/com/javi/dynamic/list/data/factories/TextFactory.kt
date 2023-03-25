package com.javi.dynamic.list.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.factories.base.DynamicListFactory
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.text.TextComponentView
import com.javi.dynamic.list.presentation.components.text.TextModel
import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.factory.AdapterFactory
import javax.inject.Inject

@AdapterFactory
class TextFactory @Inject constructor(): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.TEXT
        )
    override val hasShowCaseConfigured: Boolean = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf {
                (component.resource as TextModel).text
            }
        }
        TextComponentView(
            modifier = modifier.testTag("text_component"),
            componentIndex = component.index,
            componentInfo.showCaseState,
            text = model.value
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .clip(RoundedCornerShape(7.dp))
                .fillMaxWidth()
                .height(30.dp)
                .background(MaterialTheme.colors.primary)
        )
    }
}