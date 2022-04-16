package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.society.SocietyMemberRequest
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MineSocietyRequestListPage(requestHolder: RequestHolder) {

    var mineSocietyRequestList by remember {
        mutableStateOf(emptyList<SocietyMemberRequest>())
    }

    val updateList:()->Unit = {
        requestHolder.apiViewModel.userJoinRequestList(
            userId = requestHolder.apiViewModel.userInfo.id
        ){
            mineSocietyRequestList = it.data
        }
    }


    LaunchedEffect(Unit){
        updateList.invoke()
    }

    GlobalScaffold(
        page = GlobalNavPage.MainMineSocietyRequestListPage,
        requestHolder = requestHolder
    ) {

        LazyColumn(
            content = {

                items(
                    items = mineSocietyRequestList,
                    key = { item: SocietyMemberRequest -> item.id },
                    itemContent = { MemberRequestItem(societyMemberRequest = it) }
                )

                empty(mineSocietyRequestList)
                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )

    }
}

@Composable
fun MemberRequestItem(societyMemberRequest: SocietyMemberRequest) {
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
            Text(text = societyMemberRequest.societyName)
            Text(text = "申请事项: ${if (societyMemberRequest.isJoin) "加入" else "退出"}")
            Text(text = "申请内容：${societyMemberRequest.request}")
            Text(text = "时间：${societyMemberRequest.updateTimestamp}")
            Text(text = if (societyMemberRequest.isDealDone) "已处理" else "未处理")
            if (societyMemberRequest.isDealDone) {
                Text(text = "结果: ${if (societyMemberRequest.isAgreed) "已通过" else "未通过"}")
            }
        }
    }
}