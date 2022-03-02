package com.wh.society.typeExt

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wh.common.typeExt.everyN
import com.wh.society.api.data.ReturnListData
import com.wh.society.ui.componment.SmallListTitle

fun LazyListScope.spacer(height: Dp = 60.dp) {
    item {
        Spacer(modifier = Modifier.padding(bottom = height))
    }
}

@ExperimentalMaterialApi
fun LazyListScope.smallListTitle(title: String, n: Int, onClick: () -> Unit) {
    item {
        SmallListTitle(title, n, onClick)
    }
}

@ExperimentalMaterialApi
fun LazyListScope.smallListTitle(title: String, n: Int, show: Boolean, onClick: () -> Unit) {
    itemOnCondition(show) {
        SmallListTitle(title = title, n = n, onClick = onClick)
    }
}

fun LazyListScope.itemOnCondition(show: Boolean, content: @Composable LazyItemScope.() -> Unit) {
    if (show) {
        item(content = content)
    }
}

inline fun <T> LazyListScope.itemsOnCondition(
    show: Boolean,
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    if (show) {
        items(
            count = items.size,
            key = if (key != null) { index: Int -> key(items[index]) } else null,
            contentType = { index: Int -> contentType(items[index]) }
        ) {
            itemContent(items[it])
        }
    }
}

fun <T> LazyListScope.everyNForRow(
    items: List<T>,
    n: Int = 3,
    content: @Composable LazyItemScope.(List<T>) -> Unit
) {
    items.everyN(n, onEach = { it ->
        item {
            content(it)
        }
    })
}

fun <T> LazyListScope.everyNForRow(
    items: List<T>,
    n: Int = 3,
    itemContent: @Composable LazyItemScope.(T, Modifier) -> Unit,
    placeholder: @Composable LazyItemScope.(Modifier) -> Unit = { m -> Spacer(modifier = m) }
) {
    items.everyN(n, onEach = { it ->
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                it.forEach {
                    itemContent(it, Modifier.weight(1F / n.toFloat()))
                }
                repeat(n - it.size) {
                    placeholder(Modifier.weight(1F / n.toFloat()))
                }
            }
        }
    })
}

fun <T> LazyListScope.empty(items: ReturnListData<T>) {
    if (items.data.isEmpty()) {
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

fun <T> LazyListScope.empty(items: List<T>) {
    if (items.isEmpty()) {
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

fun LazyListScope.empty(isEmpty: Boolean) {
    if (isEmpty) {
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