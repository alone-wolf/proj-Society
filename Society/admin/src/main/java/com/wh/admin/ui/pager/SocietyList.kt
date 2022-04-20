package com.wh.admin.ui.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.wh.admin.MainActivity
import com.wh.admin.SingleLineText
import com.wh.admin.data.society.Society
import com.wh.admin.ext.empty
import com.wh.admin.listItemModifierWithPadding

@Composable
fun SocietyList(activity: MainActivity) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = activity.serverDataViewModel.allSociety,
            key = { item: Society -> item.hashCode() },
            itemContent = { it ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        activity.nav.toSocietyDetail(it)
                    }
                ) {
                    Row(
                        modifier = listItemModifierWithPadding.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = it.realIconUrl,
                                imageLoader = activity.coilImageLoader
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(color = Color.LightGray),
                            contentScale = ContentScale.Crop,
                        )
                        Column {
                            SingleLineText(text = "社团名称: ${it.name}")
                            SingleLineText(
                                text = "论坛名称: ${it.bbsName}"
                            )
                            SingleLineText(
                                text = "学院: ${it.optCollege}"
                            )
                        }
                    }
                }
            }
        )

        empty(activity.serverDataViewModel.allSociety)
    }
}