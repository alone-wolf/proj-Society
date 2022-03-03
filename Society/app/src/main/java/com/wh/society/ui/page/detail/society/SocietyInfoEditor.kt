package com.wh.society.ui.page.detail.society

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyInfoEditor(requestHolder: RequestHolder) {
    GlobalScaffold(
        page = GlobalNavPage.SocietyInfoEditorPage,
        requestHolder = requestHolder,
        actions = {
            IconButton(onClick = {
                //save and
                requestHolder.globalNav.goBack()
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "")
            }
        }) {

    }
}