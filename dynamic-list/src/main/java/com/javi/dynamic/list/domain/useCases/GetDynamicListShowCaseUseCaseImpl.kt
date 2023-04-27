package com.javi.dynamic.list.domain.useCases

import com.javi.api.TooltipPreferencesApi
import com.javi.dynamic.list.data.actions.DynamicListUIState
import com.javi.dynamic.list.data.models.DynamicListContainer
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.data.models.DynamicListShowCaseModel
import com.javi.dynamic.list.data.useCases.GetDynamicListShowCaseUseCase
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import kotlinx.coroutines.flow.first
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

class GetDynamicListShowCaseUseCaseImpl @Inject constructor(
    private val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    private val tooltipPreferencesApi: TooltipPreferencesApi
) : GetDynamicListShowCaseUseCase {

    override suspend operator fun invoke(
        dynamicListContainer: DynamicListContainer
    ): DynamicListUIState {
        val showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

        val bodyElements = dynamicListContainer.body.map { component ->
            val adapter = delegates.firstOrNull { adapterFactory ->
                adapterFactory.render.value == component.render
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

        val headerElements = dynamicListContainer.header.map { component ->
            val adapter = delegates.firstOrNull { adapterFactory ->
                adapterFactory.render.value == component.render
            }

            DynamicListElement(
                factory = adapter,
                componentItemModel = component
            )
        }

        showCaseSequence.add(
            DynamicListShowCaseModel("", 0, true)
        )

        return DynamicListUIState.SuccessAction(
            container = dynamicListContainer,
            body = bodyElements,
            header = headerElements,
            showCaseQueue = showCaseSequence
        )
    }
}
