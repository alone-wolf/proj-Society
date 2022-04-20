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
import com.wh.admin.ext.empty
import com.wh.admin.listItemModifierWithPadding

@Composable
fun UserSocietyJoinerPage(activity: MainActivity) {

    var societyList by remember {
        mutableStateOf(emptyList<Society>())
    }

    val userInfo = activity.selectedUserInfo

    val dataPrepare: () -> Unit = {
        activity.http.allUserSocietyMember(userInfo.id) {
            val memberList = it

            societyList = activity.serverDataViewModel.allSociety.filterNot { s ->
                memberList.any { sm -> s.id == sm.societyId }
            }
        }
    }

    LaunchedEffect(Unit) { dataPrepare.invoke() }

    LazyColumn(content = {
        items(
            items = societyList,
            key = { item: Society -> item.hashCode() },
            itemContent = {
                var permissionLevel by remember {
                    mutableStateOf(11)
                }

                var show by remember {
                    mutableStateOf(false)
                }

                val level2 = mapOf(
                    111 to "管理员",
                    11 to "普通成员"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            activity.alert.show2BtnAlert(
                                "提示",
                                {
                                    Column {
                                        Text(text = "要将用户 ${userInfo.username} 设置为社团 ${it.name} 的成员吗")
                                        Box {
                                            TextButton(onClick = { show = true }) {
                                                Text(text = "用户权限 ${level2[permissionLevel]}")
                                            }
                                            DropdownMenu(
                                                expanded = show,
                                                onDismissRequest = { show = false },
                                                content = {
                                                    level2.forEach {
                                                        DropdownMenuItem(onClick = {
                                                            show = false
                                                            permissionLevel = it.key
                                                        }) {
                                                            Text(text = it.value)
                                                        }
                                                    }
                                                }
                                            )
                                        }
                                    }
                                },
                                onOk = {
                                    activity.http.adminSocietyMemberCreate(
                                        userId = userInfo.id,
                                        societyId = it.id,
                                        permissionLevel = permissionLevel
                                    ) {
                                        dataPrepare.invoke()
                                    }
                                }
                            )

                        }
                        .padding(vertical = 10.dp, horizontal = 16.dp)
                ) {
                    Text(text = "社团名称：${it.name}")
                    Text(text = "所属学院：${it.optCollege}")
                }
            }
        )

        empty(societyList)
    }, modifier = Modifier.fillMaxSize())
}