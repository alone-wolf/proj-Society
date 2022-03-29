package com.wh.admin.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.wh.admin.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainPage(activity: MainActivity) {
    Column(modifier = Modifier.fillMaxSize()) {
        TabLayout(
            pagerState = activity.pagerState,
            titles = listOf("用户", "社团", "设置"),
            activity = activity
        )
        HorizontalPager(
            count = 3,
            state = activity.pagerState,
            modifier = Modifier.fillMaxSize()
        ) { i ->
            when (i) {
                0 -> UserList(activity = activity)
                1 -> SocietyList(activity = activity)
                2 -> SettingOptionList(activity = activity)
            }
        }
    }
}