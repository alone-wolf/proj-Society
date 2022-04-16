package com.wh.society.ui.componment

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.impl.ChatMessage
import com.wh.society.api.data.society.Society
import com.wh.society.api.data.society.bbs.BBS
import com.wh.society.api.data.society.bbs.Post
import com.wh.society.api.data.society.bbs.PostReply
import com.wh.society.api.data.user.UserInfo
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import kotlinx.coroutines.CoroutineScope

@Composable
fun UserBigIcon(requestHolder: RequestHolder, modifier: Modifier) {
    val padding = 4.dp
    val size = 80.dp
    val border = 3.dp

    Image(
        painter = rememberImagePainter(
            requestHolder.apiViewModel.userInfo.realIconUrl,
            imageLoader = requestHolder.coilImageLoader
        ),
        contentDescription = "",
        modifier = modifier
            .padding(padding)
            .size(size)
            .border(
                width = border,
                color = Color.White,
                shape = CircleShape
            )
            .shadow(5.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable { Log.d("WH_", "MinePage: onClick") }
            .background(Color.LightGray),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ChatMessageItem(
    meId: Int,
    chatMessage: ChatMessage,
    requestHolder: RequestHolder,
    onClick: () -> Unit = {}
) {
    val isMe = meId == chatMessage.userId
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp, horizontal = 10.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMe) {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${chatMessage.createTSFmt()}·${chatMessage.username}")
                Text(text = chatMessage.message)
            }
            Spacer(modifier = Modifier.padding(start = 4.dp))
        }
        Image(
            painter = rememberImagePainter(
                data = chatMessage.realIconUrl,
                imageLoader = requestHolder.coilImageLoader
            ),
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        if (!isMe) {
            Spacer(modifier = Modifier.padding(start = 4.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "${chatMessage.username}·${chatMessage.createTSFmt()}")
                Text(text = chatMessage.message)
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SocietyItem(requestHolder: RequestHolder, society: Society, modifier: Modifier = Modifier) {
    Card(
        onClick = { requestHolder.globalNav.goto(GlobalNavPage.DetailSociety, society) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Text(text = society.name, fontSize = 16.sp)
                Text(
                    text = society.describe,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    modifier = Modifier.alpha(0.7F)
                )
            }
        },
        elevation = 3.dp
    )
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun BBSItem(requestHolder: RequestHolder, bbs: BBS) {
    Card(
        onClick = {
            requestHolder.globalNav.goto<BBS>(GlobalNavPage.DetailBBS, bbs)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Text(text = bbs.name, fontSize = 16.sp)
                Text(
                    text = bbs.describe,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    modifier = Modifier.alpha(0.7F)
                )
            }
        },
        elevation = 5.dp
    )
}

@ExperimentalMaterialApi
@Composable
fun SmallListTitle(title: String, n: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp),
        elevation = 3.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            Row(Modifier.padding(end = 16.dp)) {
                Text(text = "共 $n 条")
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MineUserPostItem(requestHolder: RequestHolder, post: Post, modifier: Modifier = Modifier) {
    var society = Society()
    requestHolder.societyList.find { it.id == post.societyId }?.let {
        society = it
    }
    Card(
        onClick = {
            requestHolder.trans.society = society
            requestHolder.trans.bbs = society.toBBS()
            requestHolder.globalNav.goto(GlobalNavPage.DetailPost, post.id)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Text(text = post.title, fontSize = 16.sp)
                Text(
                    text = post.societyName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    modifier = Modifier.alpha(0.7F)
                )
            }
        },
        elevation = 5.dp
    )
}

@ExperimentalMaterialApi
@Composable
fun MineUserPostReplyItem(
    requestHolder: RequestHolder,
    postReply: PostReply,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {
            requestHolder.globalNav.goto(GlobalNavPage.DetailPost, postReply.postId)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    text = postReply.reply,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = postReply.createTSFmt())
                Text(
                    text = "${postReply.postTitle}·${postReply.societyName}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    modifier = Modifier.alpha(0.7F)
                )
            }
        },
        elevation = 5.dp
    )
}

@ExperimentalMaterialApi
@Composable
fun PostItem(
    requestHolder: RequestHolder,
    post: Post,
    modifier: Modifier = Modifier,
    postMaxLine: Int = 5,
    ignoreClick: Boolean = false
) {
    Card(
        onClick = {
            if (!ignoreClick) {
                requestHolder.globalNav.goto(GlobalNavPage.DetailPost, post.id)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
        content = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = post.realIconUrl,
                            imageLoader = requestHolder.coilImageLoader
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(top = 12.dp, start = 16.dp, end = 12.dp)
                            .size(40.dp)
                            .shadow(3.dp, shape = CircleShape)
                            .background(color = Color.White)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = post.username)
                        Text(
                            text = "来自${post.deviceName}",
                            fontSize = 12.sp,
                            modifier = Modifier.alpha(0.7F)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
                ) {
                    Text(text = post.title, fontWeight = FontWeight.Bold)
                    Text(text = post.post, maxLines = postMaxLine)
                }
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun PostReplyItem(
    requestHolder: RequestHolder,
    postReply: PostReply,
    modifier: Modifier = Modifier,
    postMaxLine: Int = 5,
    onItemDeleteDone: () -> Unit
) {
    Card(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = postReply.realIconUrl,
                        imageLoader = requestHolder.coilImageLoader
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 12.dp, start = 16.dp, end = 12.dp)
                        .size(40.dp)
                        .shadow(3.dp, shape = CircleShape)
                        .background(color = Color.White)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(0.7F)
                ) {
                    Text(text = postReply.username)
                    Text(
                        text = "来自${postReply.deviceName}",
                        fontSize = 12.sp,
                        modifier = Modifier.alpha(0.7F)
                    )
                }
                if (requestHolder.apiViewModel.userInfo.id == postReply.userId) {
                    IconButton(onClick = {
                        requestHolder.apiViewModel.societyBBSPostReplyDelete(
                            postReply.id,
                            onItemDeleteDone
                        ) { }
                    }) {
                        Icon(Icons.Default.Delete, "")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)
            ) {
                Text(text = postReply.reply, maxLines = postMaxLine)
            }
        }
    }
}