package com.nucu.dynamiclistcompose.domain.useCases

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.javi.api.LocalBasketApi
import com.javi.render.processor.data.enums.RenderType
import com.nucu.dynamiclistcompose.data.actions.DynamicListAction
import com.nucu.dynamiclistcompose.data.api.DynamicListControllerApi
import com.nucu.dynamiclistcompose.data.api.DynamicListUseCaseApi
import com.nucu.dynamiclistcompose.data.api.TooltipPreferencesApi
import com.nucu.dynamiclistcompose.data.factories.base.DynamicListFactory
import com.nucu.dynamiclistcompose.data.models.DynamicListElement
import com.nucu.dynamiclistcompose.data.models.DynamicListRequestModel
import com.nucu.dynamiclistcompose.data.models.DynamicListShowCaseModel
import com.nucu.dynamiclistcompose.di.IODispatcher
import com.nucu.dynamiclistcompose.domain.database.AppDatabase
import com.nucu.dynamiclistcompose.domain.database.skeletons.SkeletonsEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class DynamicListUseCaseImpl @Inject constructor(
    @IODispatcher val ioDispatcher: CoroutineDispatcher,
    private val controller: DynamicListControllerApi,
    private val database: AppDatabase,
    private val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    private val tooltipPreferencesApi: TooltipPreferencesApi
): DynamicListUseCaseApi {

    override suspend fun get(
        page: Int,
        requestModel: DynamicListRequestModel,
        withSkeletons: Boolean
    ): Flow<DynamicListAction> {
        return controller
            .get(page, requestModel)
            .map {
                // Save skeletons
                if (it is DynamicListAction.SuccessAction) {
                    val showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

                    val bodyElements = it.container.body.map { component ->
                        val adapter = delegates.firstOrNull { adapterFactory ->
                            adapterFactory.renders.any { renderType ->
                                renderType.value == component.render
                            }
                        }

                        if (
                            adapter?.hasShowCaseConfigured == true &&
                            showCaseSequence.none { it.render == component.render }
                        ) {
                            val alreadyShowed = tooltipPreferencesApi.getState(
                                booleanPreferencesKey(component.render),
                                false
                            ).first()

                            if (alreadyShowed.not()) {
                                // Add to sequence
                                showCaseSequence.add(
                                    DynamicListShowCaseModel(component.render, component.index)
                                )
                            }
                        }

                        DynamicListElement(
                            factory = adapter,
                            componentItemModel = component
                        )
                    }

                    val headerElements = it.container.header.map { component ->
                        val adapter = delegates.firstOrNull { adapterFactory ->
                            adapterFactory.renders.any { renderType ->
                                renderType.value == component.render
                            }
                        }

                        DynamicListElement(
                            factory = adapter,
                            componentItemModel = component
                        )
                    }

                    showCaseSequence.add(
                        DynamicListShowCaseModel("", 0, true)
                    )

                    DynamicListAction.SuccessAction(
                        container = it.container,
                        body = bodyElements,
                        header = headerElements,
                        showCaseQueue = showCaseSequence
                    )
                } else {
                    it
                }
            }
            .onEach {
                if (it is DynamicListAction.SuccessAction) {
                    database.skeletonsDao().saveSkeletonsByContext(
                        SkeletonsEntity(
                            context = requestModel.contextType.source,
                            renders = (it.container.header + it.container.body).map { component ->
                                RenderType.valueOf(component.render.uppercase())
                            }
                        )
                    )
                }
            }
            .onStart {

                if (withSkeletons) {
                    val skeletonContext = withContext(Dispatchers.IO) {
                        database.skeletonsDao()
                            .provideSkeletonsByContext(requestModel.contextType.source)
                    }

                    skeletonContext?.let {
                        emit(DynamicListAction.SkeletonAction(it.renders))
                    } ?: kotlin.run {
                        emit(DynamicListAction.LoadingAction)
                    }
                }
            }
            .catch {
                println(it)
                emit(DynamicListAction.ErrorAction(it))
            }.flowOn(ioDispatcher)
    }
}