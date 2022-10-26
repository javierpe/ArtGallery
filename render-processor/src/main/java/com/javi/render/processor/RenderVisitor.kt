package com.javi.render.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid

class RenderVisitor(
    private val renderFactoryGenerator: RenderFactoryGenerator
) : KSVisitorVoid() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {

        if (classDeclaration.isDataClass()) {
            renderFactoryGenerator.generate(
                classDeclaration,
            )
        } else {
            if (classDeclaration.modifiers.isNotEmpty()) {
                throw java.lang.RuntimeException(
                    "Class must be a Data Class, ${classDeclaration.modifiers.first()} current"
                )
            }
        }
    }
}