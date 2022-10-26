package com.javi.render.processor.annotations.factory

/**
 * This annotation allows FactoryModule creation that binds factory with his supertype.
 * Keep in mind that the bind interface must be single supertype in the interface.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class FactoryParentImpl
