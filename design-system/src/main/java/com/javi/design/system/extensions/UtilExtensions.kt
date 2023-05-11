package com.javi.design.system.extensions

import android.content.Context
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun Dp.toPx(context: Context): Float = value * Density(context).density