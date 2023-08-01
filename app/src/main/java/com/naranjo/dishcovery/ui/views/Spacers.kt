package com.naranjo.dishcovery.ui.views

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naranjo.dishcovery.ui.theme.LARGE
import com.naranjo.dishcovery.ui.theme.MEDIUM
import com.naranjo.dishcovery.ui.theme.SMALL
import com.naranjo.dishcovery.ui.theme.TINY

@Composable
fun NanoSpacer() {
    Spacer(modifier = Modifier.size(TINY.dp))
}

@Composable
fun TinySpacer() {
    Spacer(modifier = Modifier.size(TINY.dp))
}

@Composable
fun SmallSpacer() {
    Spacer(modifier = Modifier.size(SMALL.dp))
}

@Composable
fun MediumSpacer() {
    Spacer(modifier = Modifier.size(MEDIUM.dp))
}

@Composable
fun BigSpacer() {
    Spacer(modifier = Modifier.size(LARGE.dp))
}