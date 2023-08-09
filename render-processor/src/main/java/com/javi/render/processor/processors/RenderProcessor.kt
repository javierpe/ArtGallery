package com.javi.render.processor.processors

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.javi.render.processor.core.annotations.factory.ComponentFactory
import com.javi.render.processor.core.annotations.render.RenderFactory
import com.javi.render.processor.core.annotations.render.RenderModel
import com.javi.render.processor.creators.FactoryModuleCreator
import com.javi.render.processor.creators.RenderModuleCreator
import com.javi.render.processor.creators.kotlin.KotlinModuleCreator
import com.javi.render.processor.creators.moshi.MoshiModuleCreator
import com.javi.render.processor.data.enums.Engine
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.isValid
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.toJavaDuration

/**
 * This key allows to alternate between moshi, gson or kt
 */
private const val ENGINE_KEY = "render.processor.engine"

/**
 * This class detect all @RenderModel annotations and process it to new component and
 * hilt module.
 */
internal class RenderProcessor(
    private val logger: KSPLogger,
    private val moshiModuleCreator: MoshiModuleCreator,
    private val kotlinModuleCreator: KotlinModuleCreator,
    private val factoryModuleCreator: FactoryModuleCreator,
    private val renderModuleCreator: RenderModuleCreator,
    private val options: Map<String, String>
) : SymbolProcessor {

    private val names = mutableListOf<ModelClassProcessed>()
    private val renders = mutableListOf<String>()

    @OptIn(ExperimentalTime::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {

        val engine = Engine.valueOf(options.getOrDefault(ENGINE_KEY, Engine.MOSHI.value).uppercase())

        logger.warn("====== Working for $engine ======")

        val elapsedTime = measureTime {
            make(resolver, engine)
        }

        logger.warn("Finished in ${elapsedTime.toJavaDuration().toMillis()}ms")
        logger.warn("===========================")
        return emptyList()
    }

    private fun make(resolver: Resolver, engine: Engine) {
        logger.warn("Start processing!")
        makeFactories(resolver)
        makeRenderModule(resolver)

        RenderModel::class.qualifiedName?.let {
            val resolvedSymbols = resolver
                .getSymbolsWithAnnotation(it, inDepth = true)
                .toList()

            val validSymbols = getValidSymbols(resolvedSymbols)

            when (engine) {
                Engine.MOSHI -> makeWithMoshi(validSymbols)
                Engine.GSON -> makeWithGson(validSymbols)
                Engine.KOTLIN -> makeWithKotlin(validSymbols)
            }
        }
    }

    private fun makeWithMoshi(symbols: List<KSClassDeclaration>) {
        if (symbols.isNotEmpty()) {
            moshiModuleCreator.makeComponent(
                validatedSymbols = symbols,
                names = names
            )
        }

        if (names.isNotEmpty()) {
            moshiModuleCreator.makeModule(names)
        }
    }

    private fun makeWithGson(symbols: List<KSClassDeclaration>) {
        // ToDo
    }

    private fun makeWithKotlin(symbols: List<KSClassDeclaration>) {
        if (symbols.isNotEmpty()) {
            kotlinModuleCreator.makeComponent(
                validatedSymbols = symbols,
                names = names
            )
        }

        if (names.isNotEmpty()) {
            kotlinModuleCreator.makeModule(names)
        }
    }

    private fun makeRenderModule(resolver: Resolver) {
        RenderFactory::class.qualifiedName?.let { module ->
            val resolved = resolver
                .getSymbolsWithAnnotation(module)
                .toList()

            val symbols = getValidSymbols(resolved)

            // Map arguments
            symbols.map { it.annotations.firstOrNull()?.arguments?.first()?.value }.forEach { model ->
                model?.let {
                    renders.add(it.toString())
                }
            }

            if (symbols.isNotEmpty()) {
                renderModuleCreator.make(
                    validatedSymbols = symbols
                )
            }
        }
    }

    private fun makeFactories(resolver: Resolver) {
        ComponentFactory::class.qualifiedName?.let { name ->
            val resolved = resolver
                .getSymbolsWithAnnotation(name)
                .toList()

            if (resolved.isNotEmpty()) {
                factoryModuleCreator.make(
                    validatedSymbols = getValidSymbols(resolved)
                )
            }
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
