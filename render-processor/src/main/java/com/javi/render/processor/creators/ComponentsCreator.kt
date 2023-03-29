package com.javi.render.processor.creators

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.javi.render.data.RenderType
import com.javi.render.processor.annotations.render.RenderClass
import com.javi.render.processor.data.models.ModelClassProcessed
import com.javi.render.processor.data.utils.PACKAGE_FACTORIES
import com.javi.render.processor.data.utils.PACKAGE_MOSHI
import com.javi.render.processor.data.utils.PARENT_MODEL_COMMENT
import com.javi.render.processor.data.utils.PARENT_MODEL_FILE_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_INDEX_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RENDER_NAME
import com.javi.render.processor.data.utils.PROP_PARENT_MODEL_RESOURCE_NAME
import com.javi.render.processor.data.utils.isDataClass
import com.javi.render.processor.data.utils.log
import com.javi.render.processor.data.utils.semanticName
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

/**
 * This class create a parent class of all components annotated with @RenderClass
 * for Moshi reader.
 */
class ComponentsCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    @Suppress("LongMethod")
    fun make(
        validatedSymbols: List<KSClassDeclaration>,
        names: MutableList<ModelClassProcessed>
    ) {

        logger.log("Component Module: ${names.toString()}")

        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_FACTORIES,
            fileName = PARENT_MODEL_FILE_NAME
        )

        val render = PropertySpec
            .builder(PROP_PARENT_MODEL_RENDER_NAME, String::class)
            .initializer("\"\"")
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassName(PACKAGE_MOSHI, listOf("Json")))
                    .addMember("name = \"$PROP_PARENT_MODEL_RENDER_NAME\"")
                    .build()
            )
            .mutable(mutable = true)
            .build()

        val index = PropertySpec
            .builder(PROP_PARENT_MODEL_INDEX_NAME, Int::class)
            .initializer("0")
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassName(PACKAGE_MOSHI, listOf("Json")))
                    .addMember("name = \"$PROP_PARENT_MODEL_INDEX_NAME\"")
                    .build()
            )
            .mutable(mutable = true)
            .build()

        val resource = PropertySpec
            .builder(PROP_PARENT_MODEL_RESOURCE_NAME, TypeVariableName.Companion.invoke("T"))
            .addModifiers(KModifier.ABSTRACT)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassName(PACKAGE_MOSHI, listOf("Json")))
                    .addMember("name = \"$PROP_PARENT_MODEL_RESOURCE_NAME\"")
                    .build()
            )
            .build()


        fileSpec.addType(
            TypeSpec.classBuilder(PARENT_MODEL_FILE_NAME)
                .addTypeVariable(TypeVariableName.Companion.invoke("T"))
                .addModifiers(KModifier.SEALED)
                .addProperty(render)
                .addProperty(index)
                .addProperty(resource)
                .build()
        ).addFileComment(PARENT_MODEL_COMMENT)

        validatedSymbols.forEach { ksAnnotated ->
            if (ksAnnotated.isDataClass()) {

                val property = PropertySpec
                    .builder(PROP_PARENT_MODEL_RESOURCE_NAME, ksAnnotated.asType(emptyList()).toTypeName())
                    .initializer(PROP_PARENT_MODEL_RESOURCE_NAME)
                    .addModifiers(KModifier.OVERRIDE)
                    .build()

                val argument = ksAnnotated.annotations.firstOrNull { annotation ->
                    annotation.shortName.asString() == RenderClass::class.simpleName
                }?.arguments?.first()!!.value as KSType

                fileSpec.addType(
                    TypeSpec.classBuilder(ksAnnotated.semanticName())
                        .addModifiers(KModifier.DATA)
                        .addSuperinterface(
                            superinterface = TypeVariableName.Companion.invoke(
                                "$PARENT_MODEL_FILE_NAME<${ksAnnotated.simpleName.getShortName()}>()"
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
                                ).build()
                        )
                        .addProperty(property)
                        .build()
                )

                names.add(
                    ModelClassProcessed(
                        packageName =  ksAnnotated.packageName.asString(),
                        semanticName = ksAnnotated.semanticName(),
                        simpleName = ksAnnotated.simpleName.asString(),
                        renderType = RenderType.valueOf(argument.declaration.toString())
                    )
                )
            }
        }

        try {
            fileSpec.build().writeTo(codeGenerator = codeGenerator, aggregating = false)
        } catch (exception: FileAlreadyExistsException) {
            exception.run { printStackTrace() }
        }
    }
}