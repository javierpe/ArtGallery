package com.javi.render.processor

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

class SymbolValidator {

    fun isValid(symbol: KSAnnotated): Boolean {
        return symbol is KSClassDeclaration && symbol.validate()
    }
}