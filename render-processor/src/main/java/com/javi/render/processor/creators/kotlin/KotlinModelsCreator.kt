package com.javi.render.processor.creators.kotlin

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.javi.render.processor.core.RenderType
import com.javi.render.processor.core.annotations.render.RenderModel
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.PARENT_MODELS_CATALOG_FILE_NAME
import com.javi.render.processor.data.utils.PARENT_MODEL_FILE_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_INDEX_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RENDER_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RESOURCE_NAME
import com.javi.render.processor.data.utils.isDataClass
import com.javi.render.processor.data.utils.semanticName
import com.javi.render.processor.extensions.create
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.toTypeName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class KotlinModelsCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun makeComponent(
        validatedSymbols: List<KSClassDeclaration>,
        names: MutableList<ModelClassProcessed>
    ) {
        logger.warn("Components: $names")

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_FACTORIES,
            fileName = PARENT_MODELS_CATALOG_FILE_NAME
        )

        validatedSymbols.forEach { ksAnnotated ->
            if (ksAnnotated.isDataClass()) {

                val resourceProp = PropertySpec
                    .builder(PROP_PARENT_MODEL_RESOURCE_NAME, ksAnnotated.asType(emptyList()).toTypeName())
                    .initializer(PROP_PARENT_MODEL_RESOURCE_NAME)
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                val renderProp = PropertySpec
                    .builder(PROP_PARENT_MODEL_RENDER_NAME, String::class)
                    .initializer(PROP_PARENT_MODEL_RENDER_NAME)
                    .addModifiers(KModifier.OVERRIDE)
                    .mutable(false)
                    .build()

                val indexProp = PropertySpec
                    .builder(PROP_PARENT_MODEL_INDEX_NAME, Int::class)
                    .initializer(PROP_PARENT_MODEL_INDEX_NAME)
                    .addModifiers(KModifier.OVERRIDE)
                    .mutable(false)
                    .build()

                val argument = ksAnnotated.annotations.firstOrNull { annotation ->
                    annotation.shortName.asString() == RenderModel::class.simpleName
                }?.arguments?.first()!!.value as KSType

                fileSpec.addType(
                    TypeSpec.classBuilder(ksAnnotated.semanticName())
                        .addModifiers(KModifier.DATA)
                        .addSuperinterface(
                            superinterface = TypeVariableName.Companion.invoke(
                                "$PARENT_MODEL_FILE_NAME()"
                            )
                        )
                        .primaryConstructor(
                            primaryConstructor = FunSpec
                                .constructorBuilder()
                                .addParameter(
                                    ParameterSpec.builder(
                                        name = PROP_PARENT_MODEL_RESOURCE_NAME,
                                        type = ksAnnotated.asType(emptyList()).toTypeName()
                                    ).build()
                                )
                                .addParameter(
                                    ParameterSpec.builder(
                                        name = PROP_PARENT_MODEL_RENDER_NAME,
                                        type = String::class
                                    ).build()
                                )
                                .addParameter(
                                    ParameterSpec.builder(
                                        name = PROP_PARENT_MODEL_INDEX_NAME,
                                        type = Int::class
                                    ).build()
                                )
                                .build()
                        )
                        .addProperties(
                            listOf(
                                resourceProp, renderProp, indexProp
                            )
                        )
                        .addAnnotation(
                            AnnotationSpec
                                .builder(Serializable::class)
                                .build()
                        )
                        .addAnnotation(
                            AnnotationSpec
                                .builder(SerialName::class)
                                .addMember("value = \"${RenderType.valueOf(argument.declaration.toString()).value}\"")
                                .build()
                        )
                        .build()
                ).addImport(
                    PACKAGE_FACTORIES,
                    listOf(PARENT_MODEL_FILE_NAME)
                )
            }
        }

        fileSpec.create(codeGenerator)
    }
}