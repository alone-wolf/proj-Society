package com.wh.society.typeExt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wh.society.api.data.ReturnListData

fun LazyListScope.spacer(height:Dp=60.dp) {
    item {
        Spacer(modifier = Modifier.padding(bottom = height))
    }
}

fun LazyListScope.conditionItem(show:Boolean,content:@Composable LazyItemScope.()->Unit){
    if (show){
        item(content = content)
    }
}

fun <T> LazyListScope.empty(items:ReturnListData<T>) {
    if (items.data.isEmpty()){
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Empty")
            }
        }
    }
}

fun <T> LazyListScope.empty(items:List<T>) {
    if (items.isEmpty()){
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Empty")
            }
        }
    }
}

fun LazyListScope.empty(isEmpty:Boolean) {
    if (isEmpty){
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Empty")
            }
        }
    }
}

fun LazyListScope.empty() {
    item {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Empty")
        }
    }
}

//@ExperimentalMaterialApi
//fun LazyListScope.minePageTitleItem(title: String, n: Int, onClick: () -> Unit) {
//    item {
//        MineTitleItem(title, n, onClick)
//    }
//}