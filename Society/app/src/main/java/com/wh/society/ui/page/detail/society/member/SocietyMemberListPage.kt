package com.wh.society.ui.page.detail.society.member

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.society.api.data.society.SocietyMember
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.imageNames
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberListPage(requestHolder: RequestHolder) {

    var societyMemberList by remember {
        mutableStateOf(requestHolder.trans.societyMemberList.data)
    }

    val dataPrepare: () -> Unit = {
        requestHolder.apiViewModel.societyMemberListBySociety(
            requestHolder.trans.society.id,
            {
                requestHolder.trans.societyMemberList = it
                societyMemberList = it.data
            },
            requestHolder.toast.toast
        )
    }

    GlobalScaffold(page = GlobalNavPage.SocietyMemberListPage, requestHolder = requestHolder) {

        var level by remember {
            mutableStateOf(11)
        }

        LazyColumn(
            content = {

                imageNames(
                    items = requestHolder.trans.societyMemberList.data,
                    names = { it.username },
                    imageUrls = { it.realIconUrl },
                    requestHolder = requestHolder,
                    onClick = {
                        requestHolder.globalNav.goto(
                            GlobalNavPage.SocietyMemberDetailPage,
                            it
                        )
                    },
                    onLongClick = {
                        if (requestHolder.trans.isAdmin) {

                            requestHolder.alert.alert(
                                title = "操作",
                                content = "选择要进行的操作",
                                btns = mapOf(
                                    "修改权限" to {
                                        requestHolder.alert.alert("修改权限", {
                                            Box {


                                                var showDropdownMenu by remember {
                                                    mutableStateOf(false)
                                                }

                                                val m = mapOf(
                                                    11 to "普通成员",
                                                    111 to "管理员"
                                                )

                                                OutlinedButton(onClick = {
                                                    showDropdownMenu = true
                                                }) {
                                                    Text(text = "$level ${m[level]}")
                                                }

                                                DropdownMenu(
                                                    expanded = showDropdownMenu,
                                                    onDismissRequest = {
                                                        showDropdownMenu = false
                                                    }) {
                                                    m.entries.forEach {
                                                        DropdownMenuItem(onClick = {
                                                            showDropdownMenu = false
                                                            level = it.key
                                                        }) {
                                                            Text(text = "${it.key} ${it.value}")
                                                        }
                                                    }
                                                }
                                            }
                                        }, onOk = {
                                            requestHolder.apiViewModel.societyMemberUpdatePermissionLevel(
                                                it.id,
                                                level,
                                                dataPrepare,
                                                requestHolder.toast.toast
                                            )
                                        })
                                    },
                                    "取消" to {}
                                )
                            )
                        }
                    }
                )

                empty(requestHolder.trans.societyMemberList)

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}