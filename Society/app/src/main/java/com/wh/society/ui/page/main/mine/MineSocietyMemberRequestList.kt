package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.MemberRequest
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MineSocietyRequestListPage(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.MainMineSocietyRequestListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {

                items(
                    items = requestHolder.transSocietyRequestList.data,
                    key = { item: MemberRequest -> item.id },
                    itemContent = {
                        MemberRequestItem(memberRequest = it)
                    }
                )

                empty(requestHolder.transSocietyRequestList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}

@Composable
fun MemberRequestItem(memberRequest: MemberRequest) {
    Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {
            Text(text = memberRequest.societyName)
            Text(text = "申请事项: ${if (memberRequest.isJoin) "加入" else "退出"}")
            Text(text = "申请内容：${memberRequest.request}")
            Text(text = "时间：${memberRequest.updateTimestamp}")
            Text(text = if (memberRequest.isDealDone) "已处理" else "未处理")
            if (memberRequest.isDealDone) {
                Text(text = "结果: ${if (memberRequest.isPass) "已通过" else "未通过"}")
            }
        }
    }
}