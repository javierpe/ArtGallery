package com.javi.render.processor.extensions

import com.google.devtools.ksp.processing.CodeGenerator
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

fun FileSpec.Builder.create(codeGenerator: CodeGenerator) {
    try {
        build().writeTo(codeGenerator = codeGenerator, aggregating = false)
    } catch (exception: FileAlreadyExistsException) {
        exception.run { printStackTrace() }
    }
}