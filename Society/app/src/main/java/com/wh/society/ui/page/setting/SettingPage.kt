package com.wh.society.ui.page.setting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.service.SocketIOService
import com.wh.society.typeExt.borderSwitcher
import com.wh.society.typeExt.textFiledV
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SettingPage(requestHolder: RequestHolder) {
    GlobalScaffold(
        page = GlobalNavPage.Setting,
        requestHolder = requestHolder
    ) {

        var showBBS by remember {
            mutableStateOf(requestHolder.settingStore.showBBS)
        }

        var receivePush by remember {
            mutableStateOf(requestHolder.settingStore.receivePush)
        }

        LazyColumn(
            content = {
                textFiledV(
                    "设备名称",
                    requestHolder.settingStore.deviceName
                ) {
                    requestHolder.settingStore.deviceName = it
                }

                borderSwitcher(showBBS, "显示论坛模块") {
                    showBBS = !showBBS
                    requestHolder.settingStore.showBBS = showBBS
                }
                borderSwitcher(receivePush, "接收推送") {
                    receivePush = !receivePush
                    requestHolder.settingStore.receivePush = receivePush
                    if (receivePush) {
                        requestHolder.apiViewModel.loginToken.data?.let {
                            requestHolder.sioService.start(it.userId, it.cookieToken)
                        }
                    } else {
                        requestHolder.sioService.stop.invoke()
                    }
                }

                item {
                    Text(
                        text = "服务器版本: v1.0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                item {
                    Text(
                        text = "应用版本：v1.0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

    }
}