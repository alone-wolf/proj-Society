package com.wh.society.ui.page.detail.society.member.request

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.SocietyMemberRequest
import com.wh.society.api.data.society.SocietyNotice
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberRequestListPage(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.SocietyMemberRequestListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {
                items(
                    items = requestHolder.trans.societyMemberRequestList.data.asReversed(),
                    key = { item: SocietyMemberRequest -> item.id },
                    itemContent = { it ->
                        Card(modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp, horizontal = 10.dp)
                            ) {
                                Column {
                                    Text(text = it.request)
                                    Row {
                                        if (it.isJoin) {
                                            Text(text = "加入申请 ")
                                        } else {
                                            Text(text = "退出申请 ")
                                        }
                                        if (it.isDealDone) {
                                            Text(text = "已处理 ")
                                            if (it.isPass) {
                                                Text(text = "已通过")
                                            } else {
                                                Text(text = "未通过")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
                empty(requestHolder.trans.societyMemberRequestList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}