package com.wh.society.ui.page.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.ui.componment.RoundedTextFiled
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun LoginPage(requestHolder: RequestHolder) {
    var phoneStudentIdEmail by remember {
        mutableStateOf(requestHolder.settingStore.phoneStudentIdEmail)
    }
    var password by remember {
        mutableStateOf(requestHolder.settingStore.password)
    }

    var userRegisterAllow by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.adminUserRegisterAllow {
            userRegisterAllow = it
        }
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
                    requestHolder.settingStore.phoneStudentIdEmail = phoneStudentIdEmail
                    requestHolder.settingStore.password = password
                    requestHolder.loginLogic()
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
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "找回密码", fontSize = 14.sp, modifier = Modifier.clickable {
                requestHolder.globalNav.goto(GlobalNavPage.FindPasswordPage)
            }, textAlign = TextAlign.Center)
            if (userRegisterAllow) {
                Text(text = " | ", textAlign = TextAlign.Center)
                Text(
                    text = "注册用户",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        requestHolder.globalNav.goto(GlobalNavPage.RegisterPage)
                    })
            }
        }
    }
}