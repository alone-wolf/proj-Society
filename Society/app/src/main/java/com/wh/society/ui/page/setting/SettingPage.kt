package com.wh.society.ui.page.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        LazyColumn(
            content = {
                item {
                    var deviceName by remember {
                        mutableStateOf(requestHolder.deviceName)
                    }
                    TextField(
                        value = deviceName,
                        onValueChange = {
                            deviceName = it
                            requestHolder.settingStore.deviceName = it
                        },
                        placeholder = { Text(text = requestHolder.deviceName) },
                        label = { Text(text = "设备名称") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
                item {
                    var showBBS by remember {
                        mutableStateOf(requestHolder.settingStore.showBBS)
                    }
                    val bgc by animateColorAsState(targetValue = if (showBBS) Color.Green else Color.Red)
                    Row(modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clickable {
                            showBBS = !showBBS
                            requestHolder.settingStore.showBBS = showBBS
                        }
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = bgc),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(0.7F),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Show BBS",
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            AnimatedVisibility(visible = showBBS) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "")
                            }
                            AnimatedVisibility(visible = !showBBS) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "")
                            }
                        }

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