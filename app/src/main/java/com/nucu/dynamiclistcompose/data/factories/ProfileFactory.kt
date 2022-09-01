package com.nucu.dynamiclistcompose.data.factories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.ComponentInfo
import com.nucu.dynamiclistcompose.data.models.ComponentItemModel
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.nucu.dynamiclistcompose.presentation.components.profile.ProfileComponentScreenView
import com.nucu.dynamiclistcompose.presentation.components.profile.ProfileModel
import com.nucu.dynamiclistcompose.presentation.components.profile.profileHeight
import javax.inject.Inject

class ProfileFactory @Inject constructor(

): DynamicListFactory {

    override val renders: List<RenderType> = listOf(RenderType.PROFILE)

    @Composable
    override fun CreateComponent(
        modifier: Modifier,
        component: ComponentItemModel,
        componentInfo: ComponentInfo
    ) {
        val model = remember {
            derivedStateOf { component.resource as ProfileModel }
        }

        ProfileComponentScreenView(model = model.value)
    }

    @Composable
    override fun CreateSkeleton() {
        Box(
          modifier = Modifier
              .testTag("skeleton")
              .height(profileHeight)
              .background(MaterialTheme.colors.onPrimary)
        )
    }
}