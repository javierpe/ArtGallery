package com.javi.navigation.api

import androidx.compose.runtime.Composable

interface NavigationContractApi {

    /**
     * This is called in root and should be not invoked again!
     */
    @Composable
    fun NavHost()

    /**
     * Dispose
     */
    fun onDispose()
}