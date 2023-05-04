package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.useCases.SaveSkeletonsUseCase
import com.javi.dynamic.list.domain.database.AppDatabase
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsEntity
import com.javi.render.processor.core.RenderType
import javax.inject.Inject

class SaveSkeletonsUseCaseImpl @Inject constructor(
    private val database: AppDatabase
) : SaveSkeletonsUseCase {

    override operator fun invoke(
        body: List<ComponentItemModel>,
        header: List<ComponentItemModel>,
        context: String
    ) {
        database.skeletonsDao().saveSkeletonsByContext(
            SkeletonsEntity(
                context = context,
                renders = (header + body).map { component ->
                    RenderType.valueOf(component.render.uppercase())
                }
            )
        )
    }
}
