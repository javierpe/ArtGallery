package com.nucu.dynamiclistcompose.di

import com.google.gson.Gson
import com.nucu.dynamiclistcompose.data.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.data.controllers.DefaultDynamicListController
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.BannerCarouselParentModel
import com.nucu.dynamiclistcompose.data.models.BannerParentModel
import com.nucu.dynamiclistcompose.data.models.CardsParentModel
import com.nucu.dynamiclistcompose.data.models.ComponentModel
import com.nucu.dynamiclistcompose.data.models.FacesParentModel
import com.nucu.dynamiclistcompose.data.models.FiltersParentModel
import com.nucu.dynamiclistcompose.data.models.MessageParentModel
import com.nucu.dynamiclistcompose.data.models.PosterParentModel
import com.nucu.dynamiclistcompose.data.models.ProfileParentModel
import com.nucu.dynamiclistcompose.data.models.TextParentModel
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object HomeDynamicListModule {

    @Provides
    fun provideDefaultAdapterController(
        delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        tooltipPreferencesApi: TooltipPreferencesApi
    ): DefaultDynamicListController {
        return DefaultDynamicListController(
            delegates,
            defaultDispatcher,
            tooltipPreferencesApi
        )
    }

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(ComponentModel::class.java, "render")
                .withSubtype(BannerParentModel::class.java, RenderType.BANNER.value)
                .withSubtype(BannerCarouselParentModel::class.java, RenderType.BANNER_CAROUSEL.value)
                .withSubtype(CardsParentModel::class.java, RenderType.CARDS.value)
                .withSubtype(FacesParentModel::class.java, RenderType.FACES.value)
                .withSubtype(FiltersParentModel::class.java, RenderType.FILTERS.value)
                .withSubtype(MessageParentModel::class.java, RenderType.MESSAGE.value)
                .withSubtype(PosterParentModel::class.java, RenderType.POSTER.value)
                .withSubtype(ProfileParentModel::class.java, RenderType.PROFILE.value)
                .withSubtype(TextParentModel::class.java, RenderType.TEXT.value)
        )
        .add(KotlinJsonAdapterFactory())
        .build()
}