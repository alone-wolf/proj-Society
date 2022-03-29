package com.wh.admin.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wh.admin.corner8
import com.wh.admin.listItemModifierWithPadding

fun LazyListScope.textFiled(value: String, label: String, onUpdate: (String) -> Unit) {
    item {
        TextField(
            value = value,
            onValueChange = onUpdate,
            label = { Text(text = label) },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

fun LazyListScope.textLineButton(text: String, onClick: () -> Unit) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(onClick = onClick)
                .padding(horizontal = 20.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text, fontSize = 18.sp)
            Icon(Icons.Default.KeyboardArrowRight, "")
        }
    }
}

fun LazyListScope.borderButton(text: String, onClick: () -> Unit) {
    item {
        Row(
            modifier = listItemModifierWithPadding
                .clip(corner8)
                .clickable(onClick = onClick)
                .border(width = 2.dp, Color.LightGray, corner8)
                .height(60.dp)
                .background(Color.Transparent, corner8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text)
        }
    }
}

fun LazyListScope.borderButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    item {
        Row(
            modifier = listItemModifierWithPadding
                .clip(corner8)
                .clickable(onClick = onClick)
                .border(width = 2.dp, Color.LightGray, corner8)
                .height(60.dp)
                .background(Color.Transparent, corner8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text)
            Icon(icon, "")
        }
    }
}