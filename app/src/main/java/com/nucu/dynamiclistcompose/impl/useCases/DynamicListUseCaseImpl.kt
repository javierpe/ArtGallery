package com.nucu.dynamiclistcompose.impl.useCases

import com.nucu.dynamiclistcompose.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.actions.DynamicListAction
import com.nucu.dynamiclistcompose.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.database.AppDatabase
import com.nucu.dynamiclistcompose.database.skeletons.SkeletonsEntity
import com.nucu.dynamiclistcompose.models.ComponentItemModel
import com.nucu.dynamiclistcompose.models.ContextType
import com.nucu.dynamiclistcompose.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.renders.base.RenderType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicListUseCaseImpl @Inject constructor(
    private val controller: DynamicListControllerApi,
    private val database: AppDatabase
): DynamicListUseCaseApi {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel
    ): Flow<DynamicListAction> {

        return controller
            .get(page, requestModel)
            .onEach {
                // Save skeletons
                if (it is DynamicListAction.SuccessAction) {
                    makeSkeletons(
                        data = it.container.headers + it.container.bodies,
                        contextType = requestModel.contextType
                    )
                }
            }
            .onStart {

                val skeletonContext = withContext(Dispatchers.IO) {
                    database.skeletonsDao().provideSkeletonsByContext(requestModel.contextType.source)
                }

                skeletonContext?.let {
                    emit(DynamicListAction.SkeletonAction(it.renders))
                } ?: kotlin.run {
                    emit(DynamicListAction.LoadingAction)
                }
            }
            .catch {
                emit(DynamicListAction.ErrorAction(it))
            }
    }

    private suspend fun makeSkeletons(data: List<ComponentItemModel>, contextType: ContextType) {
        withContext(Dispatchers.IO) {
            database.skeletonsDao().saveSkeletonsByContext(
                SkeletonsEntity(
                    context = contextType.source,
                    renders = data.map {
                            component -> RenderType.valueOf(component.render.uppercase())
                    }
                )
            )
        }
    }
}