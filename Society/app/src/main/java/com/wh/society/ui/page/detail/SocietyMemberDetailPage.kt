package com.wh.society.ui.page.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.society.api.data.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberDetailPage(requestHolder: RequestHolder) {

    var userInfo by remember {
        mutableStateOf(UserInfo())
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.userInfoSimple(userId = requestHolder.transSocietyJoint.userId) { it ->
            userInfo = it
        }
    }

    GlobalScaffold(page = GlobalNavPage.SocietyMemberDetailPage, requestHolder = requestHolder) {
        val joint = requestHolder.transSocietyJoint


        LazyColumn(
            content = {
                item {
                    Text(text = joint.toString())
                }
                item {
                    Button(onClick = {
                        requestHolder.globalNav.gotoUserInfo(userInfo)
                    }) {
                        Text(text = "More Person Info")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}