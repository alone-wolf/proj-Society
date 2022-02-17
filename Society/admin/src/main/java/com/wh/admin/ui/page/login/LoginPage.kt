package com.wh.society.ui.page.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wh.admin.componment.RequestHolder
import com.wh.society.ui.componment.RoundedTextFiled

@Composable
fun LoginPage(requestHolder: RequestHolder) {
    var phoneStudentIdEmail by remember {
        mutableStateOf(requestHolder.settingStore.phoneStudentIdEmail)
    }
    var password by remember {
        mutableStateOf(requestHolder.settingStore.password)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Text(
                text = "Society",
                fontSize = 40.sp,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )
            RoundedTextFiled(
                value = phoneStudentIdEmail,
                onValueChange = { phoneStudentIdEmail = it },
                modifier = Modifier.padding(vertical = 8.dp),
                placeHolder = "手机号/邮箱/学号"
            )
            RoundedTextFiled(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.padding(vertical = 8.dp),
                placeHolder = "密码",
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = {
//                    requestHolder.globalNav.gotoMain()
                },
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(text = "登录")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "找回密码", fontSize = 14.sp, modifier = Modifier.clickable {
//                requestHolder.globalNav.gotoFindPassword()
            })
            Text(text = " | ")
            Text(text = "注册用户", fontSize = 14.sp, modifier = Modifier.clickable {
//                requestHolder.globalNav.gotoRegister()
            })
        }
    }
}