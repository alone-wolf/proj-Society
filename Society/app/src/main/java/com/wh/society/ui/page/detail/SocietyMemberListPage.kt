package com.wh.society.ui.page.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wh.society.api.data.SocietyJoint
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.spacer
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberListPage(requestHolder: RequestHolder) {
    GlobalScaffold(page = GlobalNavPage.SocietyMemberListPage, requestHolder = requestHolder) {
        LazyColumn(
            content = {
                items(
                    items = requestHolder.transSocietyJointList,
                    key = { item: SocietyJoint -> item.id },
                    itemContent = { it ->
                        Text(text = it.toString())
                    }
                )

                empty(requestHolder.transSocietyJointList)

                spacer()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}