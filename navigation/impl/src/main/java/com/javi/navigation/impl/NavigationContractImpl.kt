package com.javi.navigation.impl

import android.app.Activity
import androidx.compose.runtime.Composable
import com.javi.navigation.api.NavigationContractApi
import com.javi.navigation.api.NavigationDestinationsApi
import javax.inject.Inject

class NavigationContractImpl @Inject constructor(
    private val navigationDestinationsApi: NavigationDestinationsApi
): NavigationContractApi {

    @Composable
    override fun NavHost(activity: Activity) {
        MainNavigationHost(
            activity,
        ) {
            navigationDestinationsApi.setUp(it)
        }
    }
}