package com.javi.render.processor.provider

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.javi.render.processor.creators.moshi.MoshiComponentCreator
import com.javi.render.processor.creators.FactoryModuleCreator
import com.javi.render.processor.creators.kotlin.KotlinModuleCreator
import com.javi.render.processor.creators.moshi.MoshiModuleCreator
import com.javi.render.processor.creators.RenderModuleCreator
import com.javi.render.processor.creators.kotlin.KotlinComponentModelCreator
import com.javi.render.processor.creators.kotlin.KotlinComponentSerializerCreator
import com.javi.render.processor.creators.kotlin.KotlinModelsCreator
import com.javi.render.processor.processors.RenderProcessor

class RenderProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return RenderProcessor(
            environment.logger,
            MoshiModuleCreator(
                MoshiComponentCreator(environment.codeGenerator, environment.logger),
                environment.codeGenerator,
                environment.logger
            ),
            KotlinModuleCreator(
                KotlinComponentModelCreator(environment.codeGenerator, environment.logger),
                KotlinModelsCreator(environment.codeGenerator, environment.logger),
                KotlinComponentSerializerCreator(environment.codeGenerator, environment.logger),
            ),
            FactoryModuleCreator(environment.codeGenerator, environment.logger),
            RenderModuleCreator(environment.codeGenerator, environment.logger),
            environment.options
        )
    }
}
