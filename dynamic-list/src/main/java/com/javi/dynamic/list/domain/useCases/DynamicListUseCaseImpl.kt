package com.javi.dynamic.list.domain.useCases

import com.javi.dynamic.list.data.actions.DynamicListAction
import com.javi.api.TooltipPreferencesApi
import com.javi.dynamic.list.data.api.DynamicListControllerApi
import com.javi.dynamic.list.data.api.DynamicListUseCaseApi
import com.javi.dynamic.list.data.factories.base.DynamicListFactory
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListRequestModel
import com.javi.dynamic.list.data.models.DynamicListShowCaseModel
import com.javi.dynamic.list.di.IODispatcher
import com.javi.dynamic.list.domain.database.AppDatabase
import com.javi.dynamic.list.domain.database.skeletons.SkeletonsEntity
import com.javi.render.data.RenderType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
                            val alreadyShowed = tooltipPreferencesApi.getBooleanState(
                                component.render,
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
                    saveSkeletons(
                        it.container.body,
                        it.container.header,
                        requestModel.contextType.source
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
            }
            .flowOn(ioDispatcher)
    }

    private fun saveSkeletons(
        body: List<ComponentItemModel>,
        header: List<ComponentItemModel>,
        source: String
    ) {
        database.skeletonsDao().saveSkeletonsByContext(
            SkeletonsEntity(
                context = source,
                renders = (header + body).map { component ->
                    RenderType.valueOf(component.render.uppercase())
                }
            )
        )
    }
}