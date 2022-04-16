package com.wh.society.typeExt

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.common.typeExt.everyN
import com.wh.society.api.data.ReturnListData
import com.wh.society.componment.RequestHolder
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
    itemContent: @Composable LazyItemScope.(T, index: Int, Modifier) -> Unit,
    placeholder: @Composable LazyItemScope.(Modifier) -> Unit = { m -> Spacer(modifier = m) }
) {
    items.everyN(n, onEach = { groupId, it ->
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                it.forEachIndexed { i, it ->
                    itemContent(it, groupId * n + i, Modifier.weight(1F / n.toFloat()))
                }
                repeat(n - it.size) {
                    placeholder(Modifier.weight(1F / n.toFloat()))
                }
            }
        }
    })
}

fun <T> LazyListScope.empty(items: ReturnListData<T>) {
    empty(items.data.isEmpty())
}

fun <T> LazyListScope.empty(items: List<T>) {
    empty(items.isEmpty())
}

fun LazyListScope.empty(isEmpty: Boolean) {
    if (isEmpty) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "Empty", modifier = Modifier.align(alignment = Alignment.Bottom))
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

fun LazyListScope.textFiledV(label: String, initV: String = "", onUpdate: (String) -> Unit) {
    item {
        var v by remember {
            mutableStateOf(initV)
        }
        TextField(
            value = v,
            onValueChange = {
                v = it
                onUpdate(it)
            },
            label = { Text(text = label) },
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

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

val listItemModifierWithPadding =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)

val corner8 = RoundedCornerShape(8.dp)

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

fun LazyListScope.borderSwitcher(v: Boolean, label: String, onClick: () -> Unit) {
    item {
        val bc by animateColorAsState(if (v) Color.Transparent else Color.LightGray)
        val animateColor by animateColorAsState(if (v) Color.Green else Color.Transparent)
        Row(
            modifier = listItemModifierWithPadding
                .clip(corner8)
                .clickable {
                    onClick()
                }
                .border(width = 2.dp, bc, corner8)
                .height(60.dp)
                .background(animateColor, corner8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(label)
            AnimatedVisibility(v) {
                Icon(Icons.Default.Check, "")
            }
            AnimatedVisibility(!v) {
                Icon(Icons.Default.Close, "")
            }
        }
    }
}

fun <T> LazyListScope.imageNames(
    items: List<T>,
    names: ((item: T) -> String),
    keys:((item:T)->Any) = { item: T -> item.hashCode() },
    imageUrls: ((item: T) -> String),
    requestHolder: RequestHolder,
    onClick: (T) -> Unit
) {
    items(
        items = items,
        key = keys,
        itemContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick.invoke(it)
                    }
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = imageUrls.invoke(it),
                        imageLoader = requestHolder.coilImageLoader
                    ), "",
                    modifier = Modifier
                        .size(60.dp)
                        .shadow(5.dp, CircleShape)
                        .background(color = Color.White)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = names.invoke(it),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 16.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        })
}

fun LazyListScope.imageName(
    imageUrl: String,
    name: String,
    requestHolder: RequestHolder,
    onClick: () -> Unit
) {
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 10.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    imageLoader = requestHolder.coilImageLoader
                ), "",
                modifier = Modifier
                    .size(60.dp)
                    .shadow(5.dp, CircleShape)
                    .background(color = Color.White)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}