package com.algorithm.touchscreentestlab

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

fun Modifier.onSizeChangedCompat(
    block: (IntSize) -> Unit
): Modifier = this.then(
    Modifier.onSizeChanged { size ->
        block(size)
    }
)