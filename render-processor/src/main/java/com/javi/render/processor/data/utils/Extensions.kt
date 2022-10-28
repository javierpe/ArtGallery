package com.javi.render.processor.data.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

fun KSClassDeclaration.semanticName(): String {
    return makeSemanticName(this.simpleName.asString())
}

fun KSClassDeclaration.isDataClass(): Boolean {
    return modifiers.any { it.name == "DATA" }
}

fun KSAnnotated.isValid(): Boolean {
    return this is KSClassDeclaration && this.validate()
}

fun KSPLogger.log(message: String) {
    info("KSP Processor: $message")
}

private fun makeSemanticName(name: String): String {
    return "${name}ParentModel"
}