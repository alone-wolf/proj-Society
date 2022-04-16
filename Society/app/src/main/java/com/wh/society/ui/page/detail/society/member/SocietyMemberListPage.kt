package com.wh.society.ui.page.detail.society.member

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.imageNames
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberListPage(requestHolder: RequestHolder) {
    GlobalScaffold(page = GlobalNavPage.SocietyMemberListPage, requestHolder = requestHolder) {
        LazyColumn(
            content = {

                imageNames(
                    items = requestHolder.trans.societyMemberList.data,
                    names = { it.username },
                    imageUrls = { it.realIconUrl },
                    requestHolder = requestHolder,
                    onClick = { requestHolder.globalNav.goto(GlobalNavPage.SocietyMemberDetailPage,it) }
                )

                empty(requestHolder.trans.societyMemberList)

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}