package com.wh.society.ui.page.main.mine

import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.PicData
import com.wh.society.api.data.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun MineInfoEditor(requestHolder: RequestHolder) {

//    var tempUserInfo by remember {
//        mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()))
//    }

    var username by remember { mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).username) }
    var phone by remember { mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).phone) }
    var email by remember { mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).email) }
    var name by remember { mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).name) }
    var password by remember { mutableStateOf("") }
    var college by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf(requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).iconUrl) }

    GlobalScaffold(
        page = GlobalNavPage.MainMineInfoEditorPage,
        requestHolder = requestHolder,
        actions = {
            IconButton(onClick = {
                requestHolder.apiViewModel.userInfoUpdate(
                    username = username,
                    email = email,
                    studentNumber = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).studentNumber,
                    iconUrl = iconUrl,
                    phone = phone,
                    name = name,
                    college = college
                ) {
                    requestHolder.apiViewModel.userInfo {
                        Log.d(
                            "WH_",
                            "MineInfoEditor:${
                                requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo())
                            } "
                        )
                    }
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
                                    data = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).realIconUrl,
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
                                        key = { item: PicData -> item.id },
                                        itemContent = { it ->
                                            Box(modifier = Modifier.clickable {
                                                iconUrl = it.newFilename
//                                                val t = tempUserInfo.copy()
//                                                t.iconUrl = it.newFilename
//                                                tempUserInfo = t
//                                                Log.d("WH_", "MineInfoEditor: $tempUserInfo")
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
                                                if (requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).iconUrl == it.newFilename) {
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
                        value = phone,
                        onValueChange = { phone = it },
                        placeholder = { Text(text = "手机号码") })
                }
                item {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(text = "邮箱") })
                }
                item {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text(text = "用户名") })
                }
                item {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text(text = "姓名") })
                }
                item {
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text(text = "密码") })
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

}