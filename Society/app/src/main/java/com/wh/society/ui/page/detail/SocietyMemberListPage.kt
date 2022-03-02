package com.wh.society.ui.page.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.society.SocietyMember
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
                    items = requestHolder.trans.societyMemberList.data,
                    key = { item: SocietyMember -> item.id },
                    itemContent = { it ->
                        Card(
                            onClick = {
                                requestHolder.globalNav.gotoSocietyMemberDetail(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp, horizontal = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = it.realIconUrl,
                                        imageLoader = requestHolder.coilImageLoader
                                    ), contentDescription = "",
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp)
                                ) {
                                    Text(text = it.username)
                                }
                            }
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