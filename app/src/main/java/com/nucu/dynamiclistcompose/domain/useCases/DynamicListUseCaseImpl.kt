package com.nucu.dynamiclistcompose.domain.useCases

import com.nucu.dynamiclistcompose.data.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.domain.database.AppDatabase
import com.nucu.dynamiclistcompose.domain.database.skeletons.SkeletonsEntity
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.di.IODispatcher
import com.nucu.dynamiclistcompose.data.renders.base.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicListUseCaseImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
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
                    database.skeletonsDao().saveSkeletonsByContext(
                        SkeletonsEntity(
                            context = requestModel.contextType.source,
                            renders = (it.container.headers + it.container.bodies).map {
                                    component -> RenderType.valueOf(component.render.uppercase())
                            }
                        )
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
            }.flowOn(ioDispatcher)
    }
}