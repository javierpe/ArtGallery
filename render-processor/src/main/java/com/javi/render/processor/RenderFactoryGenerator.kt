package com.javi.render.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class RenderFactoryGenerator(
    private val codeGenerator: CodeGenerator
) {

    fun generate(dataClass: KSClassDeclaration) {

        val packageName = dataClass.packageName.asString()
        val factoryName = dataClass.semanticName()

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = factoryName
        ).apply {
            addImport(packageName, names = listOf(dataClass.simpleName.asString()))

            val property = PropertySpec
                .builder("resource", dataClass.asType(emptyList()).toTypeName())
                .initializer("resource")
                .build()

            addType(
                TypeSpec.classBuilder(factoryName)
                    .addModifiers(KModifier.DATA)
                    .primaryConstructor(
                        primaryConstructor = FunSpec
                            .constructorBuilder()
                            .addParameter(
                                ParameterSpec(
                                    name = "resource",
                                    type = dataClass.asType(emptyList()).toTypeName()
                                )
                            ).build()
                    )
                    .addProperty(property)
                    .build()
            )
        }.build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

}