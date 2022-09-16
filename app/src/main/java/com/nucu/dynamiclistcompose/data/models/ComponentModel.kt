package com.nucu.dynamiclistcompose.data.models

import com.nucu.dynamiclistcompose.presentation.components.banner.BannerModel
import com.nucu.dynamiclistcompose.presentation.components.bannerCarousel.BannerCarouselModel
import com.nucu.dynamiclistcompose.presentation.components.card.CardsModel
import com.nucu.dynamiclistcompose.presentation.components.faces.FacesModel
import com.nucu.dynamiclistcompose.presentation.components.filters.Filters
import com.nucu.dynamiclistcompose.presentation.components.message.MessageModel
import com.nucu.dynamiclistcompose.presentation.components.poster.PosterModel
import com.nucu.dynamiclistcompose.presentation.components.profile.ProfileModel
import com.nucu.dynamiclistcompose.presentation.components.text.TextModel
import com.squareup.moshi.Json

data class DataContentModel(
    @Json(name = "header") val header: List<ComponentModel<Any>>,
    @Json(name = "body") val body: List<ComponentModel<Any>>
)

sealed class ComponentModel<T> {
    @Json(name = "render") var render: String = ""
    @Json(name = "index") var index: Int = 0
    @Json(name = "resource") abstract val resource: T
}

data class FiltersParentModel(
    override val resource: Filters,
): ComponentModel<Filters>()

data class BannerParentModel(
    override val resource: BannerModel,
): ComponentModel<BannerModel>()

data class BannerCarouselParentModel(
    override val resource: BannerCarouselModel,
): ComponentModel<BannerCarouselModel>()

data class CardsParentModel(
    override val resource: CardsModel,
): ComponentModel<CardsModel>()

data class FacesParentModel(
    override val resource: FacesModel,
): ComponentModel<FacesModel>()

data class MessageParentModel(
    override val resource: MessageModel,
): ComponentModel<MessageModel>()

data class PosterParentModel(
    override val resource: PosterModel,
): ComponentModel<PosterModel>()

data class ProfileParentModel(
    override val resource: ProfileModel,
): ComponentModel<ProfileModel>()

data class TextParentModel(
    override val resource: TextModel,
): ComponentModel<TextModel>()