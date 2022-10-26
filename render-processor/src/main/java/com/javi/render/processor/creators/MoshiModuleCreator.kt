package com.javi.render.processor.creators

import com.google.devtools.ksp.processing.CodeGenerator
import com.javi.render.processor.data.enums.RenderType
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.DI_MODULE_COMMENT
import com.javi.render.processor.data.utils.DI_MODULE_FILE_NAME
import com.javi.render.processor.data.utils.PACKAGE_DI
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.PACKAGE_HILT_SINGLETON_COMPONENT
import com.javi.render.processor.data.utils.PACKAGE_MOSHI
import com.javi.render.processor.data.utils.PACKAGE_PARENT_MODEL
import com.javi.render.processor.data.utils.PARENT_MODEL_FILE_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RENDER_NAME
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
    private val codeGenerator: CodeGenerator
) {

    fun makeModule(
        names: List<ModelClassProcessed>
    ) {

        var subtypes = "PolymorphicJsonAdapterFactory.of($PARENT_MODEL_FILE_NAME::class.java, \"$PROP_PARENT_MODEL_RENDER_NAME\")"

        names.forEach { model ->
            subtypes = subtypes.plus(
                ".withSubtype(${model.semanticName}::class.java, RenderType.${model.renderType}.value)"
            )
        }

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_DI,
            fileName = DI_MODULE_FILE_NAME
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
                TypeSpec.classBuilder(DI_MODULE_FILE_NAME)
                    .addAnnotation(
                        AnnotationSpec.builder(ClassName("dagger", listOf("Module"))).build()
                    )
                    .addAnnotation(
                        AnnotationSpec.builder(ClassName("dagger.hilt", listOf("InstallIn")))
                            .addMember("SingletonComponent::class")
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
        }.addFileComment(DI_MODULE_COMMENT).build()

        try {
            fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
        } catch (exception: FileAlreadyExistsException) {
            exception.run { printStackTrace() }
        }
    }
}