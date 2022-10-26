package com.javi.render.processor.annotations

import com.javi.render.processor.data.enums.RenderType

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class RenderClass(val type: RenderType)