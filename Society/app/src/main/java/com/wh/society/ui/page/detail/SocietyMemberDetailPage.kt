package com.wh.society.ui.page.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun SocietyMemberDetailPage(requestHolder: RequestHolder) {

    var memberUserInfo by remember {
        mutableStateOf(UserInfo())
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.userInfoSimple(userId = requestHolder.trans.societyMember.userId) { it ->
            memberUserInfo = it
        }
    }

    GlobalScaffold(page = GlobalNavPage.SocietyMemberDetailPage, requestHolder = requestHolder) {
        val joint = requestHolder.trans.societyMember


        LazyColumn(
            content = {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Image(
                                    painter = rememberImagePainter(data = joint.realIconUrl),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .size(55.dp)
                                        .clip(shape = RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = joint.username,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Text(text = "学院：${memberUserInfo.college}", fontSize = 14.sp)
                                    Text(text = "邮箱：${memberUserInfo.email}", fontSize = 14.sp)
                                    Text(text = "级别：${joint.levelToString()}", fontSize = 14.sp)
                                }
                            }

                        }
                    }
                }
                item {
                    Button(
                        onClick = {
                            requestHolder.globalNav.goto<UserInfo>(GlobalNavPage.DetailUserInfo,memberUserInfo)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .height(50.dp),
                    ) {
                        Text(text = "More Person Info")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}