package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.data.society.SocietyActivity
import com.wh.admin.data.society.bbs.Post

@Composable
fun SocietyActivityListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var activityList by remember {
        mutableStateOf(emptyList<SocietyActivity>())
    }

    val dataPrepare = {
        activity.http.allSocietyActivity(society.id) {
            activityList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {
        items(
            items = activityList,
            key = { item: SocietyActivity -> item.hashCode() },
            itemContent = {

                var showDropdownMenu by remember {
                    mutableStateOf(false)
                }

                var permissionLevel by remember {
                    mutableStateOf(it.level)
                }

                val plm = mapOf(
                    0 to "所有用户",
                    10 to "仅成员可见",
                    100 to "仅管理员可见",
                )

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.alert.showXBtnAlert(
                            title = "操作",
                            text = "选择将要进行的操作",
                            btnX = mapOf(
                                "修改权限" to {
                                    activity.alert.show2BtnAlert(
                                        title = "修改权限",
                                        text = {

                                            Box {
                                                DropdownMenu(
                                                    expanded = showDropdownMenu,
                                                    onDismissRequest = {
                                                        showDropdownMenu = false
                                                    }) {
                                                    plm.entries.forEach {
                                                        DropdownMenuItem(onClick = {
                                                            showDropdownMenu = false
                                                            permissionLevel = it.key
                                                        }) {
                                                            Text(text = "${it.key}:${it.value}")
                                                        }
                                                    }
                                                }
                                                OutlinedButton(onClick = {
                                                    showDropdownMenu = true
                                                }) {
                                                    Text(text = "$permissionLevel ${plm[permissionLevel]}")
                                                }
                                            }

                                        },
                                        onOk = {
                                            activity.alert.alertConfirm {
                                                activity.http.adminSocietyActivityUpdateLevel(
                                                    activityId = it.id,
                                                    level = permissionLevel,
                                                    onReturn = dataPrepare
                                                )
                                            }
                                        }
                                    )
                                },
                                "删除活动" to {
                                    activity.alert.alertConfirm {
                                        activity.http.adminSocietyActivityDelete(it.id,dataPrepare)
                                    }
                                },
                                "取消" to {}
                            )
                        )
                    }
                    .padding(vertical = 10.dp, horizontal = 16.dp)) {
                    Text(text = "活动标题：${it.title}")
                    Text(text = "所属社团：${it.societyName}")
                    Text(text = "设备名称：${it.deviceName}")
                    Text(text = "权限：${it.level}")
                    Text(text = "发布时间：${it.createTSFmt()}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}