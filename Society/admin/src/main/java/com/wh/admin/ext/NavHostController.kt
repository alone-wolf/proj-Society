package com.wh.admin.ext

import androidx.navigation.NavHostController
import com.wh.admin.NavDes

fun NavHostController.goto(n: NavDes) {
    this.navigate(n.route)
}