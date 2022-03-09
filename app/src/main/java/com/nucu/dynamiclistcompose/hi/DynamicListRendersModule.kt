package com.nucu.dynamiclistcompose.hi

import com.nucu.dynamiclistcompose.renders.FiltersRender
import com.nucu.dynamiclistcompose.renders.HeaderRender
import com.nucu.dynamiclistcompose.renders.OneClickRender
import com.nucu.dynamiclistcompose.renders.TobaccoRender
import com.nucu.dynamiclistcompose.renders.base.DynamicListRender
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

/**
 * TODO Maybe:
 *
 * @BindsWith(DynamicListRender::class)
 * class OneClickRender : DynamicListRender<OneClickModel> {
 *     ...
 * }
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DynamicListRendersModule {

    @Binds
    abstract fun bindOneClickRender(
        render: OneClickRender
    ): DynamicListRender<*>

    @Binds
    abstract fun bindBannerRender(
        render: OneClickRender
    ): DynamicListRender<*>

    @Binds
    abstract fun bindTobaccoRender(
        render: TobaccoRender
    ): DynamicListRender<*>

    @Binds
    abstract fun bindHeaderRender(
        render: HeaderRender
    ): DynamicListRender<*>

    @Binds
    abstract fun bindFiltersRender(
        render: FiltersRender
    ): DynamicListRender<*>
}