package com.javi.dynamic.list.data.controllers

import com.javi.dynamic.list.presentation.factories.base.DynamicListFactory
import javax.inject.Inject

class DefaultDynamicListComposeController @Inject constructor(
    override val delegates: MutableSet<@JvmSuppressWildcards DynamicListFactory>
) : DynamicListComposeController()
