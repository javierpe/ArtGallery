package com.javi.render.processor.creators.kotlin

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.PARENT_MODEL_COMMENT
import com.javi.render.processor.data.utils.PARENT_MODEL_FILE_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_INDEX_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RENDER_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RESOURCE_NAME
import com.javi.render.processor.extensions.create
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
class KotlinComponentModelCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun makeModelsComponent() {

        logger.warn("Making $PARENT_MODEL_FILE_NAME")

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_FACTORIES,
            fileName = PARENT_MODEL_FILE_NAME
        )

        val render = PropertySpec
            .builder(PROP_PARENT_MODEL_RENDER_NAME, String::class)
            .mutable(mutable = false)
            .addModifiers(KModifier.ABSTRACT)
            .build()

        val index = PropertySpec
            .builder(PROP_PARENT_MODEL_INDEX_NAME, Int::class)
            .mutable(mutable = false)
            .addModifiers(KModifier.ABSTRACT)
            .build()

        val resource = PropertySpec
            .builder(PROP_PARENT_MODEL_RESOURCE_NAME, Any::class)
            .mutable(mutable = false)
            .addModifiers(KModifier.ABSTRACT)
            .build()

        fileSpec.addType(
            TypeSpec.classBuilder(PARENT_MODEL_FILE_NAME)
                .addModifiers(KModifier.SEALED)
                .addProperty(render)
                .addProperty(index)
                .addProperty(resource)
                /*.addAnnotation(
                    AnnotationSpec
                        .builder(ClassName("kotlinx.serialization", "ExperimentalSerializationApi"))
                        .build()
                )*/
                .addAnnotation(
                    AnnotationSpec
                        .builder(Serializable::class)
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec
                        .builder(JsonClassDiscriminator::class)
                        .addMember("\"render\"")
                        .build()
                )
                .build()
        ).addFileComment(PARENT_MODEL_COMMENT)

        fileSpec.create(codeGenerator)
    }
}