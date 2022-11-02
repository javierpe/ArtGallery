package com.javi.render.processor.provider

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.javi.render.processor.creators.ComponentsCreator
import com.javi.render.processor.creators.FactoryModuleCreator
import com.javi.render.processor.creators.MoshiModuleCreator
import com.javi.render.processor.creators.RenderModuleCreator
import com.javi.render.processor.processors.RenderProcessor

class RenderProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return RenderProcessor(
            environment.logger,
            MoshiModuleCreator(environment.codeGenerator, environment.logger),
            ComponentsCreator(environment.codeGenerator, environment.logger),
            FactoryModuleCreator(environment.codeGenerator, environment.logger),
            RenderModuleCreator(environment.codeGenerator, environment.logger),
        )
    }
}