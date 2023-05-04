package com.javi.dynamic.list.presentation.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.javi.dynamic.list.data.models.ComponentInfo
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.presentation.components.profile.ProfileComponentScreenView
import com.javi.dynamic.list.presentation.components.profile.ProfileModel
import com.javi.dynamic.list.presentation.components.profile.profileHeight
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import javax.inject.Inject

const val PROFILE_COMPONENT_TAG = "profile_component_tag"

@ComponentFactory
class ProfileFactory @Inject constructor() : DynamicListFactory {

    override val render = RenderType.PROFILE

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        ProfileComponentScreenView(
            modifier = modifier.testTag(PROFILE_COMPONENT_TAG),
            model = component.resource as ProfileModel,
            isExpandedScreen = componentInfo.dynamicListObject.widthSizeClass == WindowWidthSizeClass.Expanded
        )
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
            modifier = Modifier
                .testTag("skeleton")
                .fillMaxWidth()
                .height(profileHeight)
                .background(MaterialTheme.colors.primary)
        )
    }
}
