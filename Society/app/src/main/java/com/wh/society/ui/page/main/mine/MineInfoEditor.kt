package com.wh.society.ui.page.main.mine

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.util.ActivityOpener

@Composable
fun MineInfoEditor(requestHolder: RequestHolder) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = GlobalNavPage.MainMineInfoEditorPage.label)
            },
            navigationIcon = {
                IconButton(onClick = { requestHolder.globalNav.goBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                }
            }
        )
    }) {

        LazyColumn(
            content = {
                      item {
                          Button(onClick = {
                              requestHolder.imagePicker.launch("image/*")
                          }) {
                              Text(text = "select icon")
                          }
                      }
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}