package com.naranjo.dishcovery.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.google.android.material.textview.MaterialTextView

@Composable
fun HtmlText(text: String) {
    val content = HtmlCompat.fromHtml(text, 0)

    AndroidView(
        modifier = Modifier,
        factory = { MaterialTextView(it) },
        update = { it.text = content }
    )
}