package com.wh.society.ui.page.setting

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SettingPage(requestHolder: RequestHolder) {
    GlobalScaffold(
        page = GlobalNavPage.Setting,
        requestHolder = requestHolder
    ) {

    }
}