package com.wh.admin

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun SingleLineText(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, maxLines = 1, overflow = TextOverflow.Ellipsis)
}