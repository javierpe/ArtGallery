package com.javi.render.processor.creators

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.javi.render.processor.data.enums.RenderType
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.DI_RENDER_MODULE_COMMENT
import com.javi.render.processor.data.utils.DI_RENDER_MODULE_FILE_NAME
import com.javi.render.processor.data.utils.HILT_SINGLE_COMPONENT_CLASS_NAME
import com.javi.render.processor.data.utils.MOSHI_SUBTYPE_FACTORY
import com.javi.render.processor.data.utils.PACKAGE_DI
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.PACKAGE_HILT_SINGLETON_COMPONENT
import com.javi.render.processor.data.utils.PACKAGE_MOSHI
import com.javi.render.processor.data.utils.PACKAGE_PARENT_MODEL
import com.javi.render.processor.data.utils.log
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * This class create a Hilt module that provides Moshi instance.
 */
class MoshiModuleCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun make(
        names: List<ModelClassProcessed>
    ) {

        logger.log("Moshi Module: ${names.toString()}")

        var subtypes = MOSHI_SUBTYPE_FACTORY

        names.forEach { model ->
            subtypes = subtypes.plus(
                ".withSubtype(${model.semanticName}::class.java, RenderType.${model.renderType}.value)"
            )
        }

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_DI,
            fileName = DI_RENDER_MODULE_FILE_NAME
        ).apply {
            addImport("", names = names.map { "${it.packageName}.${it.simpleName}" })
            addImport("", names = listOf("$PACKAGE_MOSHI.Moshi"))
            addImport("", names = listOf("$PACKAGE_MOSHI.kotlin.reflect.KotlinJsonAdapterFactory"))
            addImport("", names = listOf("$PACKAGE_MOSHI.adapters.PolymorphicJsonAdapterFactory"))
            addImport("", names = listOf(PACKAGE_PARENT_MODEL))
            addImport("", names = names.map { "$PACKAGE_FACTORIES.${it.semanticName}" })
            addImport("", names = listOf(RenderType::class.qualifiedName.orEmpty()))
            addImport("", names = listOf(PACKAGE_HILT_SINGLETON_COMPONENT))

            addType(
                TypeSpec.classBuilder(DI_RENDER_MODULE_FILE_NAME)
                    .addAnnotation(
                        AnnotationSpec.builder(ClassName("dagger", listOf("Module"))).build()
                    )
                    .addAnnotation(
                        AnnotationSpec.builder(ClassName("dagger.hilt", listOf("InstallIn")))
                            .addMember(HILT_SINGLE_COMPONENT_CLASS_NAME)
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("provideMoshi")
                            .addAnnotation(
                                AnnotationSpec.builder(ClassName("dagger", listOf("Provides"))).build()
                            )
                            .returns(ClassName(PACKAGE_MOSHI, listOf("Moshi")))
                            .addCode(
                                CodeBlock.of(
                                    "return Moshi.Builder().add($subtypes).add(KotlinJsonAdapterFactory()).build()"
                                )
                            )
                            .build()
                    )
                    .build()
            )
        }.addFileComment(DI_RENDER_MODULE_COMMENT).build()

        try {
            fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
        } catch (exception: FileAlreadyExistsException) {
            exception.run { printStackTrace() }
        }
    }
}