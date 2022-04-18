package com.wh.admin.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.wh.admin.MainActivity
import com.wh.admin.SingleLineText
import com.wh.admin.ext.textLineButton
import com.wh.admin.listItemModifierWithPadding

@Composable
fun SocietyDetailPage(activity: MainActivity) {

    val society = activity.selectedSociety

    LazyColumn(content = {
        item {
            Box(modifier = listItemModifierWithPadding) {
                Card(
                    modifier = Modifier.padding(bottom = 50.dp),
                    elevation = 5.dp
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = society.realIconUrl,
                            imageLoader = activity.coilImageLoader,
                            builder = {
                                transformations(BlurTransformation(activity))
                            }
                        ),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Image(
                    painter = rememberImagePainter(
                        data = society.realIconUrl,
                        imageLoader = activity.coilImageLoader
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(100.dp)
                        .shadow(elevation = 5.dp, CircleShape)
                        .clip(CircleShape)
                        .border(4.dp, color = Color.White, shape = CircleShape)
                        .align(Alignment.BottomEnd),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 30.dp)
                ) {
                    IconButton(
                        onClick = activity.nav.toSocietyEditor,
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Green, CircleShape)
                    ) {
                        Icon(Icons.Default.Edit, "")
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    IconButton(
                        onClick = {
                            activity.alert.alertConfirm {
                                activity.http.adminSocietyDelete(society.id) {
                                    activity.http.allSociety()
                                    activity.nav.back.invoke()
                                }
                            }
                        },
                        modifier = Modifier
                            .shadow(3.dp, CircleShape)
                            .background(Color.Red, CircleShape)
                    ) {
                        Icon(Icons.Default.Delete, "")
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
            ) {
                SingleLineText(text = "名称: ${society.name}")
                SingleLineText(text = "简介: ${society.describe}")
                SingleLineText(text = "学院: ${society.college}")
                SingleLineText(text = "论坛名称: ${society.bbsName}")
                SingleLineText(text = "论坛简介: ${society.bbsDescribe}")
            }
        }
        textLineButton("论坛帖子", activity.nav.toSocietyPostList)
        textLineButton("社团成员", activity.nav.toSocietyMemberList)
//        textLineButton("成员申请", activity.nav.toSocietyMemberRequestList)
        textLineButton("社团活动", activity.nav.toSocietyActivityList)

    }, modifier = Modifier.fillMaxSize())
}