package com.javi.render.processor.creators.kotlin

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.javi.render.processor.data.ModuleCreator
import com.javi.render.processor.data.models.ModelClassProcessed

class KotlinModuleCreator(
    private val kotlinComponentModelCreator: KotlinComponentModelCreator,
    private val kotlinModelsCreator: KotlinModelsCreator,
    private val kotlinComponentSerializerCreator: KotlinComponentSerializerCreator,
) : ModuleCreator {

    override fun makeComponent(
        validatedSymbols: List<KSClassDeclaration>,
        names: MutableList<ModelClassProcessed>
    ) {
        kotlinComponentModelCreator.makeModelsComponent()
        kotlinModelsCreator.makeComponent(
            validatedSymbols,
            names
        )
        kotlinComponentSerializerCreator.makeComponentSerializer(validatedSymbols)
    }
}