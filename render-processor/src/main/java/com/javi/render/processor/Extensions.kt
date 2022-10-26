package com.javi.render.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration

fun KSClassDeclaration.semanticName(): String {
    return makeSemanticName(this.simpleName.asString())
}

fun KSClassDeclaration.isDataClass(): Boolean {
    return modifiers.any { it.name == "DATA" }
}

private fun makeSemanticName(name: String): String {
    return "${name}ParentModel"
}