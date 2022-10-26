package com.javi.render.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

internal class RenderProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val symbolValidator: SymbolValidator,
    private val renderFactoryGenerator: RenderFactoryGenerator
): SymbolProcessor {

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {

        logger.info("KSP: START!")
        var unresolvedSymbols: List<KSAnnotated> = emptyList()
        val names = mutableListOf<ModelClassProcessed>()

        RenderClass::class.qualifiedName?.let {
            val resolved = resolver
                .getSymbolsWithAnnotation(it)
                .toList()

            logger.info("KSP: ${resolved.toString()}")

            val validatedSymbols = resolved.filter { it.validate() }.toList()

            val fileSpec = FileSpec.builder(
                packageName = "com.render.factory",
                fileName = "ComponentModel"
            )

            val render = PropertySpec
                .builder("render", String::class)
                .initializer(" \"\"")
                .addAnnotation(
                    AnnotationSpec
                        .builder(ClassName("com.squareup.moshi", listOf("Json")))
                        .addMember("name = \"render\"")
                        .build()
                )
                .mutable(mutable = true)
                .build()

            val index = PropertySpec
                .builder("index", Int::class)
                .initializer("0")
                .addAnnotation(
                    AnnotationSpec
                        .builder(ClassName("com.squareup.moshi", listOf("Json")))
                        .addMember("name = \"index\"")
                        .build()
                )
                .mutable(mutable = true)
                .build()

            val resource = PropertySpec
                .builder("resource", TypeVariableName.Companion.invoke("T"))
                .addModifiers(KModifier.ABSTRACT)
                .addAnnotation(
                    AnnotationSpec
                        .builder(ClassName("com.squareup.moshi", listOf("Json")))
                        .addMember("name = \"resource\"")
                        .build()
                )
                .build()


            fileSpec.addType(
                TypeSpec.classBuilder("ComponentModel")
                    .addTypeVariable(TypeVariableName.Companion.invoke("T"))
                    .addModifiers(KModifier.SEALED)
                    .addProperty(render)
                    .addProperty(index)
                    .addProperty(resource)
                    .build()
            )

            validatedSymbols
                .filter { ksAnnotated ->
                    symbolValidator.isValid(ksAnnotated)
                }
                .filterIsInstance<KSClassDeclaration>()
                .filter(KSNode::validate)
                .forEach { ksAnnotated ->
                    if (ksAnnotated.isDataClass()) {

                        val property = PropertySpec
                            .builder("resource", ksAnnotated.asType(emptyList()).toTypeName())
                            .initializer("resource")
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
                                        "ComponentModel<${ksAnnotated.simpleName.getShortName()}>()"
                                    )
                                )
                                .primaryConstructor(
                                    primaryConstructor = FunSpec
                                        .constructorBuilder()
                                        .addParameter(
                                            ParameterSpec.builder(
                                                name = "resource",
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
            unresolvedSymbols = resolved - validatedSymbols.toSet()

            try {
                fileSpec.build().writeTo(codeGenerator = codeGenerator, aggregating = false)
            } catch (exception: FileAlreadyExistsException) {
                exception.printStackTrace()
            }
        }

        makeModule(names)
        logger.info("KSP: Found -> ${names.toString()}")
        logger.info("KSP: END!")
        return unresolvedSymbols
    }

    private fun makeModule(
        names: List<ModelClassProcessed>
    ) {

        val packageName =  "di"
        val factoryName = "RenderModule"

        var subtypes = "PolymorphicJsonAdapterFactory.of(ComponentModel::class.java, \"render\")"

        names.forEach { model ->
            subtypes = subtypes.plus(
                ".withSubtype(${model.semanticName}::class.java, RenderType.${model.renderType}.value)"
            )
        }

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = factoryName
        ).apply {
            addImport("", names = names.map { "${it.packageName}.${it.simpleName}" })
            addImport("", names = listOf("com.squareup.moshi.Moshi"))
            addImport("", names = listOf("com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory"))
            addImport("", names = listOf("com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory"))
            addImport("", names = listOf("com.render.factory.ComponentModel"))
            addImport("", names = names.map { "com.render.factory.${it.semanticName}" })
            addImport("", names = listOf("com.javi.render.processor.RenderType"))
            addImport("", names = listOf("dagger.hilt.components.SingletonComponent"))

            addType(
                TypeSpec.classBuilder(factoryName)
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
                            .returns(ClassName("com.squareup.moshi", listOf("Moshi")))
                            .addCode(
                                CodeBlock.of(
                                    "return Moshi.Builder().add($subtypes).add(KotlinJsonAdapterFactory()).build()"
                                )
                            )
                            .build()
                    )
                    .build()
            )
        }.build()

        try {
            fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
        } catch (exception: FileAlreadyExistsException) {
            exception.printStackTrace()
        }
    }
}