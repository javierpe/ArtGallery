package com.javi.render.processor.annotations.render

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RenderFactory(val model: KClass<*>)
