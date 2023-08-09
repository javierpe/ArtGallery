package com.javi.render.processor.creators.kotlin

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.javi.render.processor.data.utils.COMPONENT_SERIALIZER_FILE_NAME
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.semanticName
import com.javi.render.processor.extensions.create
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.json.Json

class KotlinComponentSerializerCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun makeComponentSerializer(
        validatedSymbols: List<KSClassDeclaration>
    ) {

        logger.warn("Modules: ${validatedSymbols.map { it.semanticName() }}")

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_FACTORIES,
            fileName = COMPONENT_SERIALIZER_FILE_NAME
        )

        fileSpec.addType(
            TypeSpec.objectBuilder(COMPONENT_SERIALIZER_FILE_NAME)
                .addProperty(
                    PropertySpec
                        .builder("serializerComponent", Json::class)
                        .initializer(
                            CodeBlock.of("Json { \nserializersModule = SerializersModule { \npolymorphic(ComponentModel::class) { \n${
                                validatedSymbols.joinToString {
                                    "\nsubclass(${it.semanticName()}::class)"
                                }.replace(",", "").trim()
                            } } } }")
                        )
                        .mutable(false)
                        .build()
                )
                .build()
        )
            .addImport(
                "kotlinx.serialization",
                listOf(
                    "json.Json",
                    "modules.SerializersModule",
                    "modules.polymorphic",
                    "modules.subclass"
                )
            )


        fileSpec.create(codeGenerator)
    }
}