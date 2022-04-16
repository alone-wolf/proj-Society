package com.wh.society.ui.page.main.mine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.wh.society.api.data.society.Society
import com.wh.society.componment.RequestHolder
import com.wh.society.navigation.GlobalNavPage
import com.wh.society.typeExt.empty
import com.wh.society.typeExt.imageNames
import com.wh.society.ui.componment.GlobalScaffold
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MineSocietyList(requestHolder: RequestHolder) {

    val jointSociety = remember {
        mutableStateListOf<Society>()
    }


    LaunchedEffect(Unit) {
        requestHolder.apiViewModel.societyList(requestHolder.toast.toast){
            requestHolder.societyList.filter { item -> item.thisUserJoin }.let {
                jointSociety.clear()
                jointSociety.addAll(it)
            }
        }
    }


    GlobalScaffold(
        page = GlobalNavPage.MainMineSocietyListPage,
        requestHolder = requestHolder
    ) {
        LazyColumn(content = {

            imageNames(
                items = jointSociety,
                names = { item: Society -> item.name },
                imageUrls = { item: Society -> item.realIconUrl },
                requestHolder = requestHolder,
                onClick = { requestHolder.globalNav.goto(GlobalNavPage.DetailSociety, it) }
            )

            empty(jointSociety)

        })
    }
}