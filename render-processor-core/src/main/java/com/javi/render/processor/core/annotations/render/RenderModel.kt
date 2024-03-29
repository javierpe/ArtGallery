package com.javi.render.processor.core.annotations.render

import com.javi.render.processor.core.RenderType

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RenderModel(val type: RenderType)
