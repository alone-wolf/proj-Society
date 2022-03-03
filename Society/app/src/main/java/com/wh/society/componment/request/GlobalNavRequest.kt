package com.wh.society.componment.request

import androidx.navigation.NavController
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage

class GlobalNavRequest(
    private val navController: NavController,
    private val requestHolder: RequestHolder
) {

    fun goBack() {
        navController.popBackStack()
    }

    fun goto(page: GlobalNavPage){
        navController.navigate(page.route)
    }

    fun <T>goto(page: GlobalNavPage, a:T){
        page.navExtraOperation(requestHolder, a!!)
        navController.navigate(page.route)
    }

    fun gotoWithBack(page: GlobalNavPage){
        navController.navigate(page.route){
            navController.popBackStack()
        }
    }
}
