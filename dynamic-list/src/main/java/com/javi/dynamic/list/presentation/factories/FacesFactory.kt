package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.faces.FacesComponentView
import com.javi.dynamic.list.presentation.components.faces.FacesModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.AdapterFactory
import javax.inject.Inject

const val FACES_COMPONENT_TAG = "faces_component"

@AdapterFactory
class FacesFactory @Inject constructor() : DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.FACES
        )

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo,
    ) {
        FacesComponentView(
            modifier = modifier.testTag(FACES_COMPONENT_TAG),
            faces = (component.resource as FacesModel).items
        ) {
            componentInfo.scrollAction?.invoke(
                com.javi.dynamic.list.data.actions.ScrollAction.ScrollIndex(index = it)
            )
        }
    }

    @Composable
    override fun CreateSkeleton() {
        Row(
            modifier = Modifier
                .testTag("skeleton")
                .fillMaxWidth()
                .padding(start = 16.dp)
                .wrapContentSize(unbounded = true),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (ignored in 0..4) {
                FacesSkeletonItem()
            }
        }
    }
}

@Composable
fun FacesSkeletonItem() {
    val size = 70.dp
    val roundedText = 6.dp
    val heightText = 13.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
        )

        Box(
            modifier = Modifier
                .width(size)
                .height(heightText)
                .clip(RoundedCornerShape(roundedText))
                .background(MaterialTheme.colors.primary)
        )
    }
}
