package com.wh.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.wh.admin.componment.RequestHolder
import com.wh.admin.store.SettingStore
import com.wh.admin.ui.page.login.CollegePage
import com.wh.admin.ui.page.login.SettingPage
import com.wh.admin.ui.page.login.SocietyCombinePage
import com.wh.admin.ui.page.login.UserCombinePage
import com.wh.admin.ui.theme.SocietyTheme

class MainActivity : ComponentActivity(), RequestHolder {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocietyTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // society && bbs && post && reply && member && activity && chat
                    // user && login && picture
                    // college
                    // setting
                    HorizontalPager(count = 4) { i ->
                        when (i) {
                            0 -> SocietyCombinePage(requestHolder = this@MainActivity)
                            1 -> UserCombinePage(requestHolder = this@MainActivity)
                            2 -> CollegePage(requestHolder = this@MainActivity)
                            3 -> SettingPage(requestHolder = this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    override val settingStore: SettingStore by lazy {
        SettingStore(this)
    }
}