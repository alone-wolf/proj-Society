package com.wh.society.ui.componment

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.R
import com.wh.society.api.data.BBSInfo
import com.wh.society.api.data.ReturnListData
import com.wh.society.api.data.SocietyJoint
import com.wh.society.api.data.UserInfo
import com.wh.society.componment.RequestHolder

@ExperimentalAnimationApi
@Composable
fun MinePageTopInfoCard(requestHolder: RequestHolder) {
    var showMoreUserInfo by remember { mutableStateOf(requestHolder.settingStore.showMineInfo) }
    val scaleAngle by animateFloatAsState(targetValue = if (showMoreUserInfo) 0F else 180F)
    Column(modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {
                showMoreUserInfo = showMoreUserInfo.not()
                requestHolder.settingStore.showMineInfo = showMoreUserInfo
            },
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier.rotate(scaleAngle)
            )
        }
        AnimatedVisibility(
            visible = showMoreUserInfo,
            enter = slideInVertically() + fadeIn() + expandVertically(),
            exit = slideOutVertically() + fadeOut() + shrinkVertically()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp),
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "姓名: ")
                        Text(text = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).name)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "学号: ")
                        Text(text = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).studentNumber)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "邮箱: ")
                        Text(text = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).email)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(text = "电话: ")
                        Text(text = requestHolder.apiViewModel.userInfo.notNullOrBlank(UserInfo()).phone)
                    }
                }
            }
        }
    }
}

@Composable
fun SocietyDetailTopInfoPart(
    requestHolder: RequestHolder,
    thisSocietyJointList: ReturnListData<SocietyJoint>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = requestHolder.transSociety.iconUrl,
                imageLoader = requestHolder.coilImageLoader,
                builder = {
                    this.placeholder(R.drawable.ic_baseline_supervisor_account_24)
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Green,
                            Color.Blue,
                            Color.Cyan
                        )
                    )
                ),
            colorFilter = ColorFilter.tint(Color.Magenta)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 4.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = requestHolder.transSociety.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(0.9F)
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = " ${thisSocietyJointList.data.size}成员 ",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .alpha(0.8F)
                        .padding(end = 4.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 2.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = requestHolder.transSociety.describe,
                    fontSize = 14.sp,
                    modifier = Modifier.alpha(0.8F)
                )
            }
        }
    }
}

@Composable
fun BBSDetailTopInfoCard(requestHolder: RequestHolder, thisInfo: BBSInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 16.dp)) {
            Text(
                text = requestHolder.transBBS.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.alpha(0.9F)
            )
            Text(
                text = requestHolder.transBBS.describe,
                fontSize = 14.sp,
                modifier = Modifier.alpha(0.8F)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "总发帖",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(0.7F)
                    )
                    Text(
                        text = thisInfo.postCount.toString(),
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "总回复",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(0.7F)
                    )
                    Text(text = thisInfo.postReplyCount.toString(), color = Color.Gray)
                }
            }
        }
    }
}