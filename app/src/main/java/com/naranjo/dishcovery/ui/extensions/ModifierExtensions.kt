package com.naranjo.dishcovery.ui.extensions

import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// I didn't have the time to implement responsiveness sorry, so I did this workaround
// No tuve el tiempo para implementar pantallas responsivas asi que hice este workaround
// 785 is the exact sum of all categories view sizes and spacers between them
fun Modifier.responsive(): Modifier {
    return this.widthIn(0.dp, 785.dp)
}