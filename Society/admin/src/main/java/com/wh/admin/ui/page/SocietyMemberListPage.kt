package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember
import com.wh.admin.data.society.bbs.Post

@Composable
fun SocietyMemberListPage(activity: MainActivity) {
    val society = activity.selectedSociety

    var memberList by remember {
        mutableStateOf(emptyList<SocietyMember>())
    }

    val dataPrepare = {
        activity.http.allUserSocietyMember(society.id) {
            memberList = it
        }
    }

    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    LazyColumn(content = {

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp)
            ) {
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 16.dp)
                    ) {
                        Text(text = "本社团成员数量：${memberList.size}")
                    }
                }
            }
        }

        items(
            items = memberList,
            key = { item: SocietyMember -> item.hashCode() },
            itemContent = {

                var permissionLevel by remember {
                    mutableStateOf(it.permissionLevel)
                }
                var showDropdownMenu by remember {
                    mutableStateOf(false)
                }

                val plm = mapOf(
                    11 to "普通成员",
                    111 to "管理员"
                )

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.alert.showXBtnAlert(
                            title = "操作",
                            text = "请选择要进行的操作",
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
                                            activity.http.adminSocietyMemberUpdatePermission(
                                                userId = it.userId,
                                                societyId = it.societyId,
                                                permissionLevel = permissionLevel,
                                                onReturn = dataPrepare
                                            )
                                        }
                                    )
                                },
                                "删除成员" to {
                                    activity.alert.alertConfirm {
                                        activity.http.adminSocietyMemberDelete(
                                            it.userId,
                                            it.societyId,
                                            dataPrepare
                                        )
                                    }
                                },
                                "取消" to {}
                            )
                        )
                    }
                    .padding(vertical = 10.dp, horizontal = 16.dp)) {
                    Text(text = "用户id：${it.userId}")
                    Text(text = "成员用户名：${it.username}")
                    Text(text = "加入时间：${it.createTSFmt()}")
                    Text(text = "权限等级：${it.permissionLevel}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}