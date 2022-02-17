package com.wh.society.ui.page.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder

@Composable
fun RegisterPage(requestHolder: RequestHolder) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "login/register-page",
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}