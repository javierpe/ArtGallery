package com.javi.render.processor.processors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.javi.render.processor.annotations.RenderClass
import com.javi.render.processor.creators.ComponentsCreator
import com.javi.render.processor.creators.MoshiModuleCreator
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.isValid

/**
 * This class detect all @RenderClass annotations and process it to new component and
 * hilt module.
 */
internal class RenderProcessor(
    private val logger: KSPLogger,
    private val moshiModuleCreator: MoshiModuleCreator,
    private val componentsCreator: ComponentsCreator
): SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        logger.info("KSP Render: Start processing!")
        var unresolvedSymbols: List<KSAnnotated> = emptyList()
        val names = mutableListOf<ModelClassProcessed>()

        RenderClass::class.qualifiedName?.let {
            val resolved = resolver
                .getSymbolsWithAnnotation(it)
                .toList()

            val validatedSymbols = resolved.asSequence().filter { ksAnnotated ->
                ksAnnotated.validate()
            }.toList().filter { ksAnnotated ->
                ksAnnotated.isValid()
            }.filterIsInstance<KSClassDeclaration>().filter(KSNode::validate).toList()


            unresolvedSymbols = resolved - validatedSymbols.toSet()

            componentsCreator.makeComponentClass(
                validatedSymbols = validatedSymbols,
                names = names
            )
        }

        moshiModuleCreator.makeModule(names)
        logger.info("KSP Render: finished!")
        return unresolvedSymbols
    }
}