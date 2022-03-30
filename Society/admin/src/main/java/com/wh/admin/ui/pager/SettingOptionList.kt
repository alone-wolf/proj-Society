package com.wh.admin.ui.pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wh.admin.MainActivity
import com.wh.admin.corner8
import com.wh.admin.ext.borderButton
import com.wh.admin.listItemModifierWithPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingOptionList(activity: MainActivity) {
    var allowUserRegister by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        delay(500)
        activity.http.adminUserRegisterAllow { allowUserRegister = it }
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

            val bc by animateColorAsState(if (allowUserRegister) Color.Transparent else Color.LightGray)
            val animateColor by animateColorAsState(if (allowUserRegister) Color.Green else Color.Transparent)
            Row(
                modifier = listItemModifierWithPadding
                    .clip(corner8)
                    .clickable {
                        activity.coroutineScope.launch {
                            activity.http.adminUserRegisterAllowSwitch { allowUserRegister = it }
                        }
                    }
                    .border(width = 2.dp, bc, corner8)
                    .height(60.dp)
                    .background(animateColor, corner8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Allow User Register")
                AnimatedVisibility(allowUserRegister) {
                    Icon(Icons.Default.Check, "")
                }
                AnimatedVisibility(!allowUserRegister) {
                    Icon(Icons.Default.Close, "")
                }
            }
        }

        borderButton("导出全部用户数据", Icons.Default.Info) {}
        borderButton("追加导入用户数据", Icons.Default.Info) {}
        borderButton("覆盖导入用户数据", Icons.Default.Info) {}
        borderButton("生成用户数据表格模板", Icons.Default.Info) {}
        borderButton("导出全部社团数据", Icons.Default.Info) {}
        borderButton("追加导入社团数据", Icons.Default.Info) {}
        borderButton("覆盖导入社团数据", Icons.Default.Info) {}
        borderButton("生成社团数据表格模板", Icons.Default.Info) {}
    }
}