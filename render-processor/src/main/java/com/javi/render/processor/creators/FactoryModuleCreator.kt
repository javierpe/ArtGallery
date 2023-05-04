package com.javi.render.processor.creators

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.javi.render.processor.data.utils.DI_FACTORY_MODULE_COMMENT
import com.javi.render.processor.data.utils.DI_FACTORY_MODULE_FILE_NAME
import com.javi.render.processor.data.utils.HILT_SINGLE_COMPONENT_CLASS_NAME
import com.javi.render.processor.data.utils.PACKAGE_DI
import com.javi.render.processor.data.utils.PACKAGE_HILT_SINGLETON_COMPONENT
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class FactoryModuleCreator(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun make(
        validatedSymbols: List<KSClassDeclaration>
    ) {
        logger.warn("Adapter Factories: $validatedSymbols")
        val fileSpec = FileSpec.builder(
            packageName = PACKAGE_DI,
            fileName = DI_FACTORY_MODULE_FILE_NAME
        ).apply {
            addImport("", names = listOf(PACKAGE_HILT_SINGLETON_COMPONENT))

            val type = TypeSpec.classBuilder(DI_FACTORY_MODULE_FILE_NAME)
                .addModifiers(listOf(KModifier.ABSTRACT))
                .addAnnotation(
                    AnnotationSpec.builder(ClassName("dagger", listOf("Module"))).build()
                )
                .addAnnotation(
                    AnnotationSpec.builder(ClassName("dagger.hilt", listOf("InstallIn")))
                        .addMember(HILT_SINGLE_COMPONENT_CLASS_NAME)
                        .build()
                )

            validatedSymbols.forEach { ksClassDeclaration ->
                val classType = ksClassDeclaration.asType(emptyList())
                logger.warn("Factory processed -> ${classType.declaration}")
                type.addFunction(
                    FunSpec.builder("bind${classType.declaration}")
                        .addParameter(
                            ParameterSpec(
                                name = "factory",
                                type = classType.toTypeName()
                            )
                        )
                        .addAnnotation(
                            AnnotationSpec.builder(ClassName("dagger", listOf("Binds"))).build()
                        )
                        .addAnnotation(
                            AnnotationSpec.builder(ClassName("dagger.multibindings", listOf("IntoSet"))).build()
                        )
                        .addModifiers(listOf(KModifier.ABSTRACT))
                        .returns(
                            returnType = ksClassDeclaration.superTypes.first().toTypeName()
                        )
                        .build()
                )
            }

            addType(type.build())
        }.addFileComment(DI_FACTORY_MODULE_COMMENT).build()

        try {
            fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
        } catch (exception: FileAlreadyExistsException) {
            exception.run { printStackTrace() }
        }
    }
}
