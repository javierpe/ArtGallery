package com.javi.render.processor.processors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.javi.render.processor.annotations.FactoryModule
import com.javi.render.processor.annotations.RenderClass
import com.javi.render.processor.creators.ComponentsCreator
import com.javi.render.processor.creators.FactoryModuleCreator
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
    private val componentsCreator: ComponentsCreator,
    private val factoryModuleCreator: FactoryModuleCreator
): SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {

        logger.info("KSP Render: Start processing!")

        logger.info("KSP Render: Make renders...")
        makeRenders(resolver)

        logger.info("KSP Render: Make factories...")
        makeFactories(resolver)

        logger.info("KSP Render: finished!")
        return emptyList()
    }

    private fun makeRenders(resolver: Resolver) {
        val names = mutableListOf<ModelClassProcessed>()

        RenderClass::class.qualifiedName?.let {
            val resolved = resolver
                .getSymbolsWithAnnotation(it)
                .toList()

            componentsCreator.makeComponentClass(
                validatedSymbols = getValidSymbols(resolved),
                names = names
            )
        }

        moshiModuleCreator.makeModule(names)
    }

    private fun makeFactories(resolver: Resolver) {
        FactoryModule::class.qualifiedName?.let {
            val resolved = resolver
                .getSymbolsWithAnnotation(it)
                .toList()

            factoryModuleCreator.make(
                validatedSymbols = getValidSymbols(resolved)
            )
        }
    }

    private fun getValidSymbols(annotated: List<KSAnnotated>): List<KSClassDeclaration> {
        return annotated.asSequence().filter { ksAnnotated ->
            ksAnnotated.validate()
        }.toList().filter { ksAnnotated ->
            ksAnnotated.isValid()
        }.filterIsInstance<KSClassDeclaration>().filter(KSNode::validate).toList()
    }
}