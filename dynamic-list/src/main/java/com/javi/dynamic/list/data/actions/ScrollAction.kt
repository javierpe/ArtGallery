package com.javi.dynamic.list.data.actions

import com.javi.render.processor.core.RenderType

sealed class ScrollAction(val target: TargetAction) {

    /**
     * Default action
     */
    object None : ScrollAction(TargetAction.UNDEFINED)

    /**
     * Scroll to any index.
     */
    class ScrollIndex(
        val index: Int,
        target: TargetAction = TargetAction.BODY
    ) : ScrollAction(target)

    /**
     * Scroll to first known render.
     */
    class ScrollRender(
        val renderType: RenderType,
        target: TargetAction = TargetAction.BODY
    ) : ScrollAction(target)
}
