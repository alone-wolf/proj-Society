package com.wh.society

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.wh.society.api.data.College
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.society.Society
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.componment.request.GlobalNavRequest
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.navigation.MainNavPage
import com.wh.society.ui.componment.MyAlertDialog
import com.wh.society.ui.componment.NavHost
import com.wh.society.ui.theme.SocietyTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : BaseMainActivity() {

    val TAG = "WH__"

    override lateinit var coroutineScope: CoroutineScope
    private lateinit var globalNavController: NavHostController
    override lateinit var mainNavController: NavHostController

    override val globalNav: GlobalNavRequest by lazy {
        GlobalNavRequest(globalNavController, this)
    }

    override val societyList: List<Society> by lazy { apiViewModel.societyList }
    override val collegeList: ReturnListData<College> by lazy { apiViewModel.collegeList }
    override val bbsList: List<BBS> by lazy { apiViewModel.bbsList }

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            coroutineScope = rememberCoroutineScope()
            globalNavController = rememberNavController()
            mainNavController = rememberNavController()

            LaunchedEffect(Unit) {
                if (settingStore.autoLogin) {
                    loginLogic()
                }
            }

            SocietyTheme {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Surface(color = MaterialTheme.colors.background) {
                        NavHost(
                            navController = globalNavController,
                            pageArray = GlobalNavPage.aa,
                            startPage = GlobalNavPage.LoginPage,
                            requestHolder = this
                        )
                        MyAlertDialog(alert)
                    }

                }
            }
        }
    }
}

