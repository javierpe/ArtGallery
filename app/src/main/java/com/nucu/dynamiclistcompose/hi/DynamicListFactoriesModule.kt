package com.nucu.dynamiclistcompose.hi

import com.nucu.dynamiclistcompose.adapters.DynamicListAdapterFactory
import com.nucu.dynamiclistcompose.factories.BannerFactory
import com.nucu.dynamiclistcompose.factories.HeaderFactory
import com.nucu.dynamiclistcompose.factories.OneClickReorderFactory
import com.nucu.dynamiclistcompose.factories.TobaccoFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoSet

/**
 * TODO Maybe:
 *
 * @BindsWith(DynamicListAdapterFactory::class)
 * class OneClickReorderFactory @Inject constructor(): DynamicListAdapterFactory {
 *     ...
 * }
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class DynamicListFactoriesModule {

    @Binds
    @IntoSet
    abstract fun bindOneClickReorderFactory(
        factory: OneClickReorderFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindBannerFactory(
        factory: BannerFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindTobaccoFactory(
        factory: TobaccoFactory
    ): DynamicListAdapterFactory

    @Binds
    @IntoSet
    abstract fun bindHeaderFactory(
        factory: HeaderFactory
    ): DynamicListAdapterFactory
}