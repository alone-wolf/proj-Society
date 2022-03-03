package com.wh.society.ui.page.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.GlobalScaffold

@ExperimentalMaterialApi
@Composable
fun RegisterPage(requestHolder: RequestHolder) {

    GlobalScaffold(
        page = GlobalNavPage.RegisterPage,
        requestHolder = requestHolder
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var username by remember { mutableStateOf("") }
            var phone by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            TextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text(text = "手机号码") })
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "邮箱") })
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text(text = "用户名") })
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(text = "姓名") })
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "密码") })
            Button(onClick = {
                requestHolder.apiViewModel.userRegister(username, phone, email, name, password) {
                    username = ""
                    phone = ""
                    email = ""
                    name = ""
                    password = ""
                    requestHolder.alert.alert("提示", "新用户已创建") {
                        requestHolder.globalNav.goBack()
                    }
                }
            }) {
                Text(text = "注册")
            }
        }
    }
}