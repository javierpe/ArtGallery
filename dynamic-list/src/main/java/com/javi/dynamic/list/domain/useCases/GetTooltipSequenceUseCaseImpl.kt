package com.javi.dynamic.list.domain.useCases

import com.javi.api.TooltipPreferencesApi
import com.javi.dynamic.list.data.models.ComponentItemModel
import com.javi.dynamic.list.data.models.DynamicListElement
import com.javi.dynamic.list.domain.api.useCases.GetTooltipSequenceUseCase
import com.javi.dynamic.list.domain.models.DynamicListShowCaseModel
import com.javi.dynamic.list.domain.models.ShowCaseResultModel
import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import kotlinx.coroutines.flow.first
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

class GetTooltipSequenceUseCaseImpl @Inject constructor(
    private val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>,
    private val tooltipPreferencesApi: TooltipPreferencesApi
) : GetTooltipSequenceUseCase {

    override suspend operator fun invoke(
        header: List<ComponentItemModel>,
        body: List<ComponentItemModel>
    ): ShowCaseResultModel {
        val showCaseSequence: Queue<DynamicListShowCaseModel> = LinkedList()

        val bodyElements = body.map { component ->
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

        val headerElements = header.map { component ->
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

        return ShowCaseResultModel(
            body = bodyElements,
            header = headerElements,
            showCaseQueue = showCaseSequence
        )
    }
}
