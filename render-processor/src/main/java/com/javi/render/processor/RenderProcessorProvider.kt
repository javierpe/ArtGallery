package com.javi.render.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class RenderProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return RenderProcessor(
            environment.codeGenerator,
            environment.logger,
            SymbolValidator(),
            RenderFactoryGenerator(environment.codeGenerator)
        )
    }
}