package com.wh.society.ui.page.main.mine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.user.UserPicture
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MineInfoEditor(requestHolder: RequestHolder) {

    val tempUserInfo = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).shadow()

    GlobalScaffold(
        page = GlobalNavPage.MainMineInfoEditorPage,
        requestHolder = requestHolder,
        actions = {
            IconButton(onClick = {
                requestHolder.apiViewModel.userInfoUpdate(tempUserInfo) {
                    requestHolder.apiViewModel.userInfo {}
                }
                requestHolder.globalNav.goBack()
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "")
            }
        }) {


        LazyColumn(
            content = {

                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        elevation = 5.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(
                                    data = tempUserInfo.realIconUrl,
                                    imageLoader = requestHolder.coilImageLoader
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(2.dp)
                                    .size(60.dp)
                                    .border(width = 2.dp, color = Color.White, shape = CircleShape)
                                    .shadow(5.dp, shape = CircleShape)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )

                            LazyRow(
                                content = {
                                    items(
                                        requestHolder.apiViewModel.picDataList.data,
                                        key = { item: UserPicture -> item.id },
                                        itemContent = { it ->
                                            Box(modifier = Modifier.clickable {
                                                tempUserInfo.iconUrl = it.newFilename
                                            }) {
                                                Image(
                                                    painter = rememberImagePainter(
                                                        data = it.realIconUrl,
                                                        imageLoader = requestHolder.coilImageLoader
                                                    ),
                                                    contentDescription = "",
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .size(60.dp),
                                                    contentScale = ContentScale.Crop,
                                                    alignment = Alignment.Center
                                                )
                                                if (tempUserInfo.iconUrl == it.newFilename) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = "",
                                                        modifier = Modifier
                                                            .align(Alignment.Center)
                                                            .size(60.dp)
                                                            .background(
                                                                Color(0.3F, 0.7F, 0.3F, 0.5f),
                                                                shape = CircleShape
                                                            )
                                                            .alpha(0.3F),
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        })
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(28.dp))
                                    .background(Color.Gray)
                            )
                        }
                    }
                }


                item {
                    TextField(
                        value = tempUserInfo.phone,
                        onValueChange = { tempUserInfo.phone = it },
                        placeholder = { Text(text = "手机号码") },
                        label = { Text(text = "手机号码")}
                    )
                }
                item {
                    TextField(
                        value = tempUserInfo.email,
                        onValueChange = { tempUserInfo.email = it },
                        placeholder = { Text(text = "邮箱") },
                        label = { Text(text = "邮箱") },
                    )
                }
                item {
                    TextField(
                        value = tempUserInfo.username,
                        onValueChange = { tempUserInfo.username = it },
                        placeholder = { Text(text = "用户名") },
                        label = { Text(text = "用户名") },
                    )
                }
                item {
                    TextField(
                        value = tempUserInfo.name,
                        onValueChange = { tempUserInfo.name = it },
                        placeholder = { Text(text = "姓名") },
                        label = { Text(text = "姓名") },
                    )
                }
                item {
                    TextField(
                        value = tempUserInfo.password,
                        onValueChange = { tempUserInfo.password = it },
                        placeholder = { Text(text = "密码") },
                        label = { Text(text = "密码") },
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

}