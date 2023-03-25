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
import com.javi.dynamic.list.presentation.components.banner.BannerComponentViewScreen
import com.javi.dynamic.list.presentation.components.banner.BannerModel
import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.factory.AdapterFactory
import javax.inject.Inject

@AdapterFactory
class BannerFactory @Inject constructor(

): DynamicListFactory {

    override val renders: List<RenderType>
        get() = listOf(
            RenderType.BANNER
        )

    override val hasShowCaseConfigured = true

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf {
                component.resource as BannerModel
            }
        }
        BannerComponentViewScreen(
            modifier = modifier.testTag("banner_component"),
            model = model.value,
            componentIndex = component.index,
            showCaseState = componentInfo.showCaseState,
            navigator = componentInfo.dynamicListObject.navigator
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .height(150.dp)
                .background(MaterialTheme.colors.primary)
        )
    }
}