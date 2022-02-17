package com.wh.society.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wh.society.componment.RequestHolder
import com.wh.society.ui.page.login.FindPasswordPage
import com.wh.society.ui.page.login.LoginPage
import com.wh.society.ui.page.login.RegisterPage

//fun NavGraphBuilder.login(requestHolder: RequestHolder) {
//    navigation(startDestination = "login/login-page", route = "login") {
//        composable("login/login-page") {
//            LoginPage(requestHolder = requestHolder)
//        }
//        composable("login/register-page") {
//            RegisterPage(requestHolder = requestHolder)
//        }
//        composable("login/find-password") {
//            FindPasswordPage(requestHolder = requestHolder)
//        }
//    }
//}