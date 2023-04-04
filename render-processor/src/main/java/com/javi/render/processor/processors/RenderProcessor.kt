package com.javi.render.processor.processors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.javi.render.processor.core.annotations.factory.AdapterFactory
import com.javi.render.processor.core.annotations.render.RenderClass
import com.javi.render.processor.core.annotations.render.RenderFactory
import com.javi.render.processor.creators.ComponentsCreator
import com.javi.render.processor.creators.FactoryModuleCreator
import com.javi.render.processor.creators.MoshiModuleCreator
import com.javi.render.processor.creators.RenderModuleCreator
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.isValid
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.toJavaDuration

/**
 * This class detect all @RenderClass annotations and process it to new component and
 * hilt module.
 */
internal class RenderProcessor(
    private val logger: KSPLogger,
    private val moshiModuleCreator: MoshiModuleCreator,
    private val componentsCreator: ComponentsCreator,
    private val factoryModuleCreator: FactoryModuleCreator,
    private val renderModuleCreator: RenderModuleCreator
): SymbolProcessor {

    private val names = mutableListOf<ModelClassProcessed>()
    private val renders = mutableListOf<String>()

    override fun finish() {
        names.clear()
        super.finish()
    }

    @OptIn(ExperimentalTime::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {

        val elapsedTime = measureTime {
            make(resolver)
        }

        logger.warn("Finished in ${elapsedTime.toJavaDuration().seconds}")
        finish()
        return emptyList()
    }

    private fun make(resolver: Resolver) {
        logger.warn("Start processing!")

        logger.warn("Make FactoryModule...")
        makeFactories(resolver)

        logger.warn("Make RenderModule...")
        makeRenderModule(resolver)

        logger.warn("Make Components...")
        makeComponents(resolver)
    }

    private fun makeRenderModule(resolver: Resolver) {
        RenderFactory::class.qualifiedName?.let { module ->
            val resolved = resolver
                .getSymbolsWithAnnotation(module)
                .toList()

            logger.warn("Found ${resolved.size} factories!")

            val symbols = getValidSymbols(resolved)

            // Map arguments
            symbols.map { it.annotations.firstOrNull()?.arguments?.first()?.value }.forEach { model ->
                model?.let {
                    renders.add(it.toString())
                }
            }

            renderModuleCreator.make(
                validatedSymbols = symbols
            )
        }
    }

    private fun makeComponents(resolver: Resolver) {
        RenderClass::class.qualifiedName?.let {
            val resolved = resolver
                .getSymbolsWithAnnotation(it, inDepth = true)
                .toList()


            logger.warn("Found ${resolved.size} renders!")

            componentsCreator.make(
                validatedSymbols = getValidSymbols(resolved),
                names = names
            )
        }

        moshiModuleCreator.make(names)
    }

    private fun makeFactories(resolver: Resolver) {
        AdapterFactory::class.qualifiedName?.let { module ->
            val resolved = resolver
                .getSymbolsWithAnnotation(module)
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