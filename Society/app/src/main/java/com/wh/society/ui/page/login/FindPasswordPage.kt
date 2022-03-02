package com.wh.society.ui.page.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun FindPasswordPage(requestHolder: RequestHolder) {


    GlobalScaffold(
        page = GlobalNavPage.FindPasswordPage,
        requestHolder = requestHolder
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            var username by remember {
                mutableStateOf("")
            }
            var phone by remember {
                mutableStateOf("")
            }
            var email by remember {
                mutableStateOf("")
            }
            var name by remember {
                mutableStateOf("")
            }
            var studentNumber by remember {
                mutableStateOf("")
            }

            var password by remember {
                mutableStateOf("")
            }
            var password1 by remember {
                mutableStateOf("")
            }

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(text = "姓名") },
                label = { Text(text = "姓名") }
            )
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "用户名") },
                label = { Text(text = "用户名") }
            )
            TextField(
                value = studentNumber,
                onValueChange = { studentNumber = it },
                placeholder = { Text(text = "学号") },
                label = { Text(text = "学号") }
            )
            TextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text(text = "手机号码") },
                label = { Text(text = "手机号码") }
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "邮箱") },
                label = { Text(text = "邮箱") },
            )


            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "新密码") },
                label = { Text(text = "新密码") },
            )
            TextField(
                value = password1,
                onValueChange = { password1 = it },
                placeholder = { Text(text = "确认密码") },
                label = { Text(text = "确认密码") },
                isError = password == password1
            )

            Button(onClick = {

                requestHolder.apiViewModel.userInfoUpdatePassword(
                    name = name,
                    username = username,
                    studentNumber = studentNumber,
                    phone = phone,
                    email = email,
                    password = password
                ) {
                    requestHolder.alertRequest.alert(
                        "提示",
                        "密码已经重置"
                    ) { requestHolder.globalNav.goBack() }
                }
            }) {
                Text(text = "重置")
            }
        }
    }

}