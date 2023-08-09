package com.javi.render.processor.data

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.javi.render.processor.data.models.ModelClassProcessed

interface ModuleCreator {

    fun makeComponent(
        validatedSymbols: List<KSClassDeclaration>,
        names: MutableList<ModelClassProcessed>
    )

    fun makeModule(
        names: List<ModelClassProcessed>
    ) { }
}