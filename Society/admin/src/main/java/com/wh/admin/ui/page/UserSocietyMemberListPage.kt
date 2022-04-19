package com.wh.admin.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.SingleLineText
import com.wh.admin.data.society.Society
import com.wh.admin.data.society.SocietyMember

@Composable
fun UserSocietyMemberListPage(activity: MainActivity) {
    val userInfo = activity.selectedUserInfo
    var memberList by remember {
        mutableStateOf(emptyList<SocietyMember>())
    }

    var societyList by remember {
        mutableStateOf(emptyList<Society>())
    }


    val dataPrepare: () -> Unit = {
        activity.http.allUserSocietyMember(userInfo.id) {
            memberList = it

            societyList = activity.serverDataViewModel.allSociety.filter { s ->
                memberList.any { sm -> s.id == sm.societyId }
            }
        }
    }
    LaunchedEffect(Unit) {
        dataPrepare.invoke()
    }

    val level2 = mapOf(
        11 to "普通成员",
        111 to "管理员"
    )

    LazyColumn(content = {
        items(
            items = societyList,
            key = { item: Society -> item.hashCode() },
            itemContent = { it ->

                val m = memberList.firstOrNull { item: SocietyMember ->
                    item.societyId == it.id
                }

                var permissionLevelForUpdate by remember {
                    mutableStateOf(
                        m?.permissionLevel ?: 11
                    )
                }

                var showDropdownMenu by remember { mutableStateOf(false) }


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {

                        activity.alert.showXBtnAlert(
                            title = "操作",
                            text = "选择要进行的操作",
                            btnX = mapOf(
                                "修改权限" to {
                                    activity.alert.show2BtnAlert(
                                        title = "修改权限",
                                        text = {

                                            Box {
                                                TextButton(onClick = {
                                                    showDropdownMenu = true
                                                }) {
                                                    Text(text = "权限 ${level2[permissionLevelForUpdate]}")
                                                }

                                                DropdownMenu(expanded = showDropdownMenu,
                                                    onDismissRequest = {
                                                        showDropdownMenu = false
                                                    },
                                                    content = {
                                                        level2.forEach {
                                                            DropdownMenuItem(onClick = {
                                                                showDropdownMenu = false
                                                                permissionLevelForUpdate = it.key
                                                            }) {
                                                                Text(text = it.value)
                                                            }
                                                        }
                                                    }
                                                )
                                            }

                                        },
                                        onOk = {
                                            activity.http.adminSocietyMemberUpdatePermission(
                                                userId = userInfo.id,
                                                societyId = it.id,
                                                permissionLevel = permissionLevelForUpdate,
                                                dataPrepare
                                            )
                                        }
                                    )
                                },
                                "删除社团" to {
                                    activity.http.adminSocietyMemberDelete(
                                        userId = userInfo.id,
                                        it.id,
                                        dataPrepare
                                    )
                                },
                                "取消" to {}
                            )
                        )
                        
                    }
                    .padding(horizontal = 20.dp, vertical = 10.dp)) {
                    SingleLineText(text = "社团id：${it.id}")
                    SingleLineText(text = "社团名称：${it.name}")
                    SingleLineText(text = "权限：${level2[m?.permissionLevel ?: 11]}")
                    SingleLineText(text = "加入时间：${it.createTSFmt()}")
                    SingleLineText(text = "更新时间：${it.updateTSFmt()}")
                }
            }
        )

    }, modifier = Modifier.fillMaxSize())
}