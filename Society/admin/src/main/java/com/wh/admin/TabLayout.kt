package com.wh.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabLayout(pagerState: PagerState, titles: List<String>, activity: MainActivity) {

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp, start = 4.dp, end = 4.dp)) {
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.fillMaxWidth(),
            indicatorHeight = 40.dp,
            indicatorWidth = (screenWidth - 8.dp) / titles.size,
            inactiveColor = Color.Transparent,
            activeColor = Color.LightGray,
            indicatorShape = corner8,
            spacing = 0.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            titles.forEachIndexed { index, s ->
                val m = Modifier
                    .weight(1f / titles.size)
                    .height(40.dp)
                    .clip(corner8)
                    .clickable {
                        activity.coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                Column(
                    modifier = m,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = s, textAlign = TextAlign.Center)
                }
            }
        }
    }
}